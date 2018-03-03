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
 * An HTTP response.
 */
@PublicAPI
expect class HttpResponse {

  /**
   * Returns the HTTP status code.
   */
  @PublicAPI
  fun code(): Int

  /**
   * Returns values of the header with the special name.
   */
  @PublicAPI
  fun header(name: String): String?

  /**
   * Returns the response as a string.
   */
  @PublicAPI
  fun string(): String
}
