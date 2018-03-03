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

import com.ehviewer.core.PublicAPI
import com.ehviewer.core.jsObject
import com.ehviewer.core.json.JsonObject
import com.ehviewer.core.wrapper.QueryString
import com.ehviewer.core.wrapper.axios
import com.ehviewer.core.wrapper.newRequest
import com.ehviewer.core.wrapper.stringifyMap
import kotlinx.coroutines.experimental.Deferred
import kotlinx.coroutines.experimental.asDeferred

@PublicAPI
actual class HttpRequest {

  private var method: String = "GET"
  private var data: String? = null
  private var contentType: String? = null
  private var url: String? = null
  private var headers: MutableMap<String, String>? = null

  @PublicAPI
  actual fun get(): HttpRequest {
    method = "GET"
    data = null
    contentType = null
    return this
  }

  @PublicAPI
  actual fun post(form: Map<String, String>): HttpRequest {
    method = "POST"
    data = QueryString.stringifyMap(form)
    contentType = "application/x-www-form-urlencoded"
    return this
  }

  @PublicAPI
  actual fun post(json: JsonObject): HttpRequest {
    method = "POST"
    data = json.toString()
    contentType = "application/json; charset=utf-8"
    return this
  }

  @PublicAPI
  actual fun url(url: String): HttpRequest {
    this.url = url
    return this
  }

  @PublicAPI
  actual fun header(name: String, value: String): HttpRequest {
    var headers = this.headers
    if (headers == null) {
      headers = mutableMapOf()
      this.headers = headers
    }
    headers[name] = value
    return this
  }

  @PublicAPI
  actual fun execute(): Deferred<HttpResponse> {
    val url = this.url
    val data = this.data
    if (url == null) throw IllegalStateException("url == null")

    val request = newRequest()
    request.method = method
    request.data = data
    request.url = url
    request.headers = jsObject {
      headers?.forEach { (name, value) ->
        it[name] = value
      }
      if (contentType != null) {
        it["Content-Type"] = contentType
      }
      if (data != null) {
        it["Content-Length"] = data.length
      }
    }

    return axios(request).then { HttpResponse(it) }.asDeferred()
  }
}
