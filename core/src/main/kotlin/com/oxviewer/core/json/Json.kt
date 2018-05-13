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
 * The father of [JsonObject] and [JsonArray].
 */
sealed class Json

/**
 * A modifiable set of name/value mappings.
 * [toString] returns a json-serialized text.
 * [equals] and [hashCode] are not supported.
 */
abstract class JsonObject : Json() {

  /**
   * Returns the value mapped by name if it exists and is a `boolean`, or throws otherwise.
   */
  abstract fun getBoolean(name: String): Boolean

  /**
   * Returns the value mapped by name if it exists and is a `int`, or throws otherwise.
   */
  abstract fun getInt(name: String): Int

  /**
   * Returns the value mapped by name if it exists and is a `float`, or throws otherwise.
   */
  abstract fun getFloat(name: String): Float

  /**
   * Returns the value mapped by name if it exists and is a `string`, or throws otherwise.
   */
  abstract fun getString(name: String): String

  /**
   * Returns the value mapped by name if it exists and is a `JsonObject`, or throws otherwise.
   */
  abstract fun getJsonObject(name: String): JsonObject

  /**
   * Returns the value mapped by name if it exists and is a `JsonArray`, or throws otherwise.
   */
  abstract fun getJsonArray(name: String): JsonArray

  /**
   * Maps name to value, clobbering any existing name/value mapping with the same name.
   */
  abstract fun put(name: String, value: Boolean): JsonObject

  /**
   * Maps name to value, clobbering any existing name/value mapping with the same name.
   */
  abstract fun put(name: String, value: Int): JsonObject

  /**
   * Maps name to value, clobbering any existing name/value mapping with the same name.
   */
  abstract fun put(name: String, value: Float): JsonObject

  /**
   * Maps name to value, clobbering any existing name/value mapping with the same name.
   */
  abstract fun put(name: String, value: String): JsonObject

  /**
   * Maps name to value, clobbering any existing name/value mapping with the same name.
   */
  abstract fun put(name: String, value: JsonObject): JsonObject

  /**
   * Maps name to value, clobbering any existing name/value mapping with the same name.
   */
  abstract fun put(name: String, value: JsonArray): JsonObject
}

/**
 * A dense indexed sequence of values.
 * [toString] returns a json-serialized text.
 * [equals] and [hashCode] are not supported.
 */
abstract class JsonArray : Json() {

  /**
   * Returns the number of values in this array.
   */
  abstract fun size(): Int

  /**
   * Returns the value at index if it exists and is a `boolean`, or throws otherwise.
   */
  abstract fun getBoolean(index: Int): Boolean

  /**
   * Returns the value at index if it exists and is a `int`, or throws otherwise.
   */
  abstract fun getInt(index: Int): Int

  /**
   * Returns the value at index if it exists and is a `float`, or throws otherwise.
   */
  abstract fun getFloat(index: Int): Float

  /**
   * Returns the value at index if it exists and is a `string`, or throws otherwise.
   */
  abstract fun getString(index: Int): String

  /**
   * Returns the value at index if it exists and is a `JsonObject`, or throws otherwise.
   */
  abstract fun getJsonObject(index: Int): JsonObject

  /**
   * Returns the value at index if it exists and is a `JsonArray`, or throws otherwise.
   */
  abstract fun getJsonArray(index: Int): JsonArray

  /**
   * Appends value to the end of this array.
   */
  abstract fun add(value: Boolean): JsonArray

  /**
   * Appends value to the end of this array.
   */
  abstract fun add(value: Int): JsonArray

  /**
   * Appends value to the end of this array.
   */
  abstract fun add(value: Float): JsonArray

  /**
   * Appends value to the end of this array.
   */
  abstract fun add(value: String): JsonArray

  /**
   * Appends value to the end of this array.
   */
  abstract fun add(value: JsonObject): JsonArray

  /**
   * Appends value to the end of this array.
   */
  abstract fun add(value: JsonArray): JsonArray
}
