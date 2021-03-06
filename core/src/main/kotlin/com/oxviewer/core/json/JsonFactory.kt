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

package com.oxviewer.core.json

/**
 * Creates [JsonObject] or [JsonArray] instances, and parse json text.
 */
abstract class JsonFactory {

  /**
   * Creates a new [JsonObject].
   */
  abstract fun newJsonObject(): JsonObject

  /**
   * Creates a new [JsonArray].
   */
  abstract fun newJsonArray(): JsonArray

  /**
   * Parses a json text to a [JsonObject] or a [JsonArray].
   *
   * @throws Throwable if can't parse the text to a [JsonObject] or a [JsonArray]
   */
  abstract fun parseJson(text: String): Json
}
