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
import kotlinx.coroutines.experimental.Deferred

/**
 * An HTTP request.
 */
@PublicAPI
expect class HttpRequest {

  /**
   * HTTP GET method. It's the default method.
   */
  @PublicAPI
  fun get(): HttpRequest

  /**
   * HTTP POST method. Submits a form.
   */
  @PublicAPI
  fun post(form: Map<String, String>): HttpRequest

  /**
   * HTTP POST method. Sends a json.
   */
  @PublicAPI
  fun post(json: JsonObject): HttpRequest

  /**
   * Set the url.
   */
  @PublicAPI
  fun url(url: String): HttpRequest

  /**
   * Adds a header `name: value`, remove all other headers with the same name.
   * In some cases, this method doesn't work. For example, the header breaks the HTTP specification.
   */
  @PublicAPI
  fun header(name: String, value: String): HttpRequest

  /**
   * Execute this HTTP request. This method is non-blocking.
   * The callback is invoked after the request is completely done.
   */
  @PublicAPI
  fun execute(): Deferred<HttpResponse>
}
