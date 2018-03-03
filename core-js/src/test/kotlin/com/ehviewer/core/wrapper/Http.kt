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

package com.ehviewer.core.wrapper

import kotlin.js.Promise

@JsModule("http")
external object Http {
  fun createServer(callback: (request: Request, response: Response) -> Unit): Server
}

external interface Request {
  val method: String
  val url: String
  val headers: dynamic
  fun on(tag: String, callback: (dynamic) -> Unit): Request
}

external interface Response {
  var statusCode: Int
  fun setHeader(name: String, value: String)
  fun write(body: String)
  fun end()
}

external interface Server {
  fun close()
  fun listen(port: Int): Boolean
}

fun Request.body(): Promise<String> = Promise { resolve, reject ->
  val body = js("[]")
  on("data") { chunk ->
    body.push(chunk)
  }.on("end") {
    val str: String = js("Buffer").concat(body).toString()
    resolve(str)
  }.on("error") {
    reject(it)
  }
}
