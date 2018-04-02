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

import com.oxviewer.core.isArray
import com.oxviewer.core.isObject
import kotlin.js.Json

class JsonObjectJs(val json: Json) : JsonObject() {

  override fun getBoolean(name: String): Boolean =
      json[name] as? Boolean ?: throw IllegalStateException("Invalid boolean value, name: $name")

  override fun getInt(name: String): Int =
      json[name] as? Int ?: throw IllegalStateException("Invalid int value, name: $name")

  override fun getFloat(name: String): Float =
      json[name] as? Float ?: throw IllegalStateException("Invalid float value, name: $name")

  override fun getString(name: String): String =
      json[name] as? String ?: throw IllegalStateException("Invalid string value, name: $name")

  override fun getJsonObject(name: String): JsonObject =
      json[name]?.takeIf { isObject(it) }?.let { JsonObjectJs(it.asDynamic()) } ?: throw IllegalStateException("Invalid JsonObject value, name: $name")

  override fun getJsonArray(name: String): JsonArray =
      json[name]?.takeIf { isArray(it) }?.let { JsonArrayJs(it.asDynamic()) } ?: throw IllegalStateException("Invalid JsonArray value, name: $name")

  override fun put(name: String, value: Boolean): JsonObject {
    json[name] = value
    return this
  }

  override fun put(name: String, value: Int): JsonObject {
    json[name] = value
    return this
  }

  override fun put(name: String, value: Float): JsonObject {
    json[name] = value
    return this
  }

  override fun put(name: String, value: String): JsonObject {
    json[name] = value
    return this
  }

  override fun put(name: String, value: JsonObject): JsonObject {
    json[name] = (value as JsonObjectJs).json
    return this
  }

  override fun put(name: String, value: JsonArray): JsonObject {
    json[name] = (value as JsonArrayJs).array
    return this
  }

  override fun toString(): String = JSON.stringify(json)
}

class JsonArrayJs(val array: Array<*>) : JsonArray() {

  override fun size(): Int = array.size

  override fun getBoolean(index: Int): Boolean =
      array[index] as? Boolean ?: throw IllegalStateException("Invalid boolean value, index: $index")

  override fun getInt(index: Int): Int =
      array[index] as? Int ?: throw IllegalStateException("Invalid int value, index: $index")

  override fun getFloat(index: Int): Float =
      array[index] as? Float ?: throw IllegalStateException("Invalid float value, index: $index")

  override fun getString(index: Int): String =
      array[index] as? String ?: throw IllegalStateException("Invalid string value, index: $index")

  override fun getJsonObject(index: Int): JsonObject =
      array[index]?.takeIf { isObject(it) }?.let { JsonObjectJs(it.asDynamic()) } ?: throw IllegalStateException("Invalid JsonObject value, index: $index")

  override fun getJsonArray(index: Int): JsonArray =
      array[index]?.takeIf { isArray(it) }?.let { JsonArrayJs(it.asDynamic()) } ?: throw IllegalStateException("Invalid JsonArray value, index: $index")

  @Suppress("UNUSED_PARAMETER")
  private fun push(array: Array<*>, value: dynamic): JsonArray {
    js("array.push(value);")
    return this
  }

  override fun add(value: Boolean): JsonArray = push(array, value)

  override fun add(value: Int): JsonArray = push(array, value)

  override fun add(value: Float): JsonArray = push(array, value)

  override fun add(value: String): JsonArray = push(array, value)

  override fun add(value: JsonObject): JsonArray = push(array, (value as JsonObjectJs).json)

  override fun add(value: JsonArray): JsonArray = push(array, (value as JsonArrayJs).array)

  override fun toString(): String = JSON.stringify(array)
}
