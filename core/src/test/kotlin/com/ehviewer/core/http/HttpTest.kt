/*
 * Copyright 2018 Hippo Seven
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.ehviewer.core.http

import com.ehviewer.core.AsyncTest
import com.ehviewer.core.json.JsonObject
import kotlin.test.Ignore
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

expect fun newHttpClient(): HttpClient

class HttpTest: AsyncTest() {

  private val client: HttpClient = newHttpClient()
  private val server = HttpServer()

  override suspend fun onBefore() {
    server.start(6523)
  }

  private fun assertResponse(
      response: HttpResponse,
      code: Int,
      headers: Map<String, String>?,
      body: String
  ) {
    assertEquals(code, response.code(), "Response code")
    headers?.forEach { (name, value) ->
      assertEquals(value, response.header(name), "Response header, name: $name")
    }
    assertNull(response.header("invalid"), "Invalid response header")
    assertEquals(body, response.string(), "Response body")
  }

  private suspend fun test(
      url: String,
      headers: Map<String, String>? = null,
      form: Map<String, String>? = null,
      json: JsonObject? = null,
      requestBody: String? = null,
      responseCode: Int = 200
  ) {
    val request = client.newRequest()
    when {
      form != null -> request.post(form)
      json != null -> request.post(json)
      else -> request.get()
    }
    request.url(url)
    headers?.forEach { (name, value) -> request.header(name, value) }
    request.header("Code", responseCode.toString())

    val requestMethod = if (form != null || json != null) "POST" else "GET"
    val requestHeaders = mutableMapOf<String, String>()
    headers?.forEach { (name, value) -> requestHeaders[name] = value }
    if (form != null) {
      requestHeaders["Content-Type"] = "application/x-www-form-urlencoded"
    } else if (json != null) {
      requestHeaders["Content-Type"] = "application/json; charset=utf-8"
    }
    if (requestBody != null) {
      requestHeaders["Content-Length"] = requestBody.length.toString()
    }
    requestHeaders["Code"] = responseCode.toString()

    val deferred1 = server.assertRequest(requestMethod, url, requestHeaders, requestBody)
    val deferred2 = request.execute()

    deferred1.await()
    val response = deferred2.await()

    val responseHeaders = mutableMapOf<String, String>()
    headers?.forEach { (name, value) -> responseHeaders["Request-$name"] = value }
    assertResponse(response, responseCode, responseHeaders, url)
  }

  @Test
  fun testUrl1() = runTest {
    test("http://127.0.0.1:6523/")
  }

  @Test
  fun testUrl2() = runTest {
    test("http://127.0.0.1:6523/test1/test2")
  }

  @Test
  fun testHeader() = runTest {
    test("http://127.0.0.1:6523/", headers = mapOf("name1" to "value1", "name2" to "value2"))
  }

  @Test
  fun testPostForm() = runTest {
    test("http://127.0.0.1:6523/",
        form = mapOf("name1" to "value1", "name2" to "value2"),
        requestBody = "name1=value1&name2=value2")
  }

  @Ignore
  @Test
  fun testPostJson() = runTest {
    TODO("Implement JsonObject")
  }

  @Test
  fun testCode() = runTest {
    test("http://127.0.0.1:6523/test1/test2", responseCode = 542)
  }

  override suspend fun onAfter() {
    server.stop()
  }
}
