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

import kotlinx.coroutines.experimental.Deferred
import kotlinx.coroutines.experimental.async
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import kotlin.test.assertEquals

actual class HttpServer {

  private val server = MockWebServer()

  actual fun start(port: Int) {
    server.start(port)
  }

  actual fun stop() {
    server.close()
  }

  actual fun assertRequest(
      method: String,
      url: String,
      headers: Map<String, String>?,
      body: String?
  ): Deferred<Unit> = async {
    val request = server.takeRequest()

    assertEquals(method, request.method, "Request method")
    assertEquals(url, request.requestUrl.toString(), "Request url")
    headers?.forEach { (name, value) ->
      assertEquals(value, request.headers.get(name), "Request header, name = $name")
    }
    if (!request.body.exhausted() && body == null) {
      throw AssertionError("Contains body, but assert body is null")
    }
    if (body != null) {
      assertEquals(body, request.body.readUtf8(), "Request body")
    }

    val response = MockResponse()
    response.setResponseCode(request.getHeader("Code")?.toInt() ?: 0)
    headers?.forEach { (name, value) ->
      response.addHeader("Request-$name", value)
    }
    response.setBody(url)

    server.enqueue(response)
  }
}
