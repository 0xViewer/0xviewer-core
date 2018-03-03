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

import com.ehviewer.core.wait
import com.ehviewer.core.wrapper.Http.createServer
import com.ehviewer.core.wrapper.body
import kotlinx.coroutines.experimental.Deferred
import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.await
import kotlinx.coroutines.experimental.launch
import kotlin.test.assertEquals

actual class HttpServer actual constructor() {

  data class RequestData(
      val method: String,
      val url: String,
      val headers: Map<String, String>?,
      val body: String?)

  private var port: Int = 0
  private var data: RequestData? = null
  private var done = false

  private val server = createServer { request, response -> launch { try {
    wait { this@HttpServer.data != null }
    val data = this@HttpServer.data!!

    assertEquals(data.method, request.method, "Request method")
    assertEquals(data.url, "http://127.0.0.1:$port${request.url}", "Request url")
    data.headers?.forEach { (name, value) ->
      assertEquals(value, request.headers[name.toLowerCase()], "Request header, name = $name")
    }
    val requestBody = request.body().await()
    if (requestBody.isNotEmpty() && data.body == null) {
      throw AssertionError("Contains body, but assert body is null")
    }
    if (data.body != null) {
      assertEquals(data.body, requestBody, "request body")
    }

    response.statusCode = (request.headers["Code".toLowerCase()] as String).toInt()
    data.headers?.forEach { (name, value) ->
      response.setHeader("Request-$name", value)
    }
    response.write("http://127.0.0.1:$port${request.url}")
  } finally {
    response.end()
    done = true
  } } }

  actual fun start(port: Int) {
    this.port = port
    server.listen(port)
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
    data = RequestData(method, url, headers, body)
    wait { done }
  }
}
