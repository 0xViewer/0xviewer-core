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

import com.oxviewer.core.jsObject

class JsonFactoryJs : JsonFactory() {

  override fun newJsonObject() = JsonObjectJs(jsObject())

  override fun newJsonArray() = JsonArrayJs(arrayOf<Any?>())

  override fun parseJson(text: String): Json {
    val value = JSON.parse<Any?>(text)
    return when {
      value == null -> throw NullPointerException("Parse json text into a null: $text")
      value is Array<*> -> JsonArrayJs(value)
      jsTypeOf(value) == "object" -> JsonObjectJs(value.asDynamic())
      else -> throw IllegalArgumentException("Unsupported json type: ${jsTypeOf(value)}")
    }
  }
}
