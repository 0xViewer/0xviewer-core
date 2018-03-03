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
import com.ehviewer.core.json.JsonObject
import kotlinx.coroutines.experimental.CoroutineStart
import kotlinx.coroutines.experimental.Deferred
import kotlinx.coroutines.experimental.async
import okhttp3.FormBody
import okhttp3.MediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody

private val JSON = MediaType.parse("application/json; charset=utf-8")

actual class HttpRequest(private val client: OkHttpClient) {

  private val builder = Request.Builder()

  @PublicAPI
  actual fun get(): HttpRequest {
    builder.get()
    return this
  }

  @PublicAPI
  actual fun post(form: Map<String, String>): HttpRequest {
    val bodyBuilder = FormBody.Builder()
    form.forEach { name, value -> bodyBuilder.add(name, value) }
    builder.post(bodyBuilder.build())
    return this
  }

  @PublicAPI
  actual fun post(json: JsonObject): HttpRequest {
    builder.post(RequestBody.create(JSON, json.toString()))
    return this
  }

  @PublicAPI
  actual fun url(url: String): HttpRequest {
    builder.url(url)
    return this
  }

  @PublicAPI
  actual fun header(name: String, value: String): HttpRequest {
    builder.header(name, value)
    return this
  }

  @PublicAPI
  actual fun execute(): Deferred<HttpResponse> =
      async(start = CoroutineStart.UNDISPATCHED) { HttpResponse(client.newCall(builder.build()).await()) }
}
