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

class JsonObjectMin(val jo: com.eclipsesource.json.JsonObject) : JsonObject() {

  override fun getBoolean(name: String): Boolean =
      jo.get(name)?.asBoolean() ?: throw IllegalStateException("Invalid name: $name")

  override fun getInt(name: String): Int =
      jo.get(name)?.asInt() ?: throw IllegalStateException("Invalid name: $name")

  override fun getFloat(name: String): Float =
      jo.get(name)?.asFloat() ?: throw IllegalStateException("Invalid name: $name")

  override fun getString(name: String): String =
      jo.get(name)?.asString() ?: throw IllegalStateException("Invalid name: $name")

  override fun getJsonObject(name: String): JsonObject =
      jo.get(name)?.asObject()?.let { JsonObjectMin(it) } ?: throw IllegalStateException("Invalid name: $name")

  override fun getJsonArray(name: String): JsonArray =
      jo.get(name)?.asArray()?.let { JsonArrayMin(it) } ?: throw IllegalStateException("Invalid name: $name")

  override fun put(name: String, value: Boolean): JsonObject {
    jo.set(name, value)
    return this
  }

  override fun put(name: String, value: Int): JsonObject {
    jo.set(name, value)
    return this
  }

  override fun put(name: String, value: Float): JsonObject {
    jo.set(name, value)
    return this
  }

  override fun put(name: String, value: String): JsonObject {
    jo.set(name, value)
    return this
  }

  override fun put(name: String, value: JsonObject): JsonObject {
    jo.set(name, (value as JsonObjectMin).jo)
    return this
  }

  override fun put(name: String, value: JsonArray): JsonObject {
    jo.set(name, (value as JsonArrayMin).ja)
    return this
  }

  override fun toString(): String = jo.toString()
}

class JsonArrayMin(val ja: com.eclipsesource.json.JsonArray) : JsonArray() {

  override fun size(): Int = ja.size()

  override fun getBoolean(index: Int): Boolean = ja.get(index).asBoolean()

  override fun getInt(index: Int): Int = ja.get(index).asInt()

  override fun getFloat(index: Int): Float = ja.get(index).asFloat()

  override fun getString(index: Int): String = ja.get(index).asString()

  override fun getJsonObject(index: Int): JsonObject = ja.get(index).asObject().let { JsonObjectMin(it) }

  override fun getJsonArray(index: Int): JsonArray = ja.get(index).asArray().let { JsonArrayMin(it) }

  override fun add(value: Boolean): JsonArray {
    ja.add(value)
    return this
  }

  override fun add(value: Int): JsonArray {
    ja.add(value)
    return this
  }

  override fun add(value: Float): JsonArray {
    ja.add(value)
    return this
  }

  override fun add(value: String): JsonArray {
    ja.add(value)
    return this
  }

  override fun add(value: JsonObject): JsonArray {
    ja.add((value as JsonObjectMin).jo)
    return this
  }

  override fun add(value: JsonArray): JsonArray {
    ja.add((value as JsonArrayMin).ja)
    return this
  }

  override fun toString(): String = ja.toString()
}
