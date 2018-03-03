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

/**
 * An HTTP client.
 */
@PublicAPI
expect class HttpClient {

  /**
   * Create a HTTP request.
   */
  @PublicAPI
  fun newRequest(): HttpRequest

  /**
   * Add a cookie to this HTTP client.
   */
  @PublicAPI
  fun addCookie(name: String, value: String, domain: String, path: String)

  /**
   * Remove the special cookie from the HTTP client.
   */
  @PublicAPI
  fun removeCookie(name: String, domain: String, path: String)
}
