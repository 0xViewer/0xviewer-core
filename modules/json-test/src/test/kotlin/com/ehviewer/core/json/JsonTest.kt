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

package com.ehviewer.core.json

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFails

expect fun newJsonFactory(): JsonFactory

class JsonTest {

  private val factory = newJsonFactory()

  @Test
  fun testParseJsonObject() {
    val json = factory.parseJson("{\n" +
        "  \"array\": [\n" +
        "    1,\n" +
        "    2,\n" +
        "    3\n" +
        "  ],\n" +
        "  \"boolean\": true,\n" +
        "  \"null\": null,\n" +
        "  \"number\": 123,\n" +
        "  \"object\": {\n" +
        "    \"a\": \"b\",\n" +
        "    \"c\": \"d\",\n" +
        "    \"e\": \"f\"\n" +
        "  },\n" +
        "  \"string\": \"Hello World\"\n" +
        "}")

    assertEquals(true, json is JsonObject)
    val jo = json as JsonObject

    val ja = jo.getJsonArray("array")
    assertEquals(3, ja.size())
    assertEquals(1, ja.getInt(0))
    assertEquals(2, ja.getInt(1))
    assertEquals(3, ja.getInt(2))

    assertEquals(true, jo.getBoolean("boolean"))

    assertFails { jo.getBoolean("null") }
    assertFails { jo.getInt("null") }
    assertFails { jo.getFloat("null") }
    assertFails { jo.getString("null") }
    assertFails { jo.getJsonObject("null") }
    assertFails { jo.getJsonArray("null") }

    assertEquals(123, jo.getInt("number"))

    val jo2 = jo.getJsonObject("object")
    assertEquals("b", jo2.getString("a"))
    assertEquals("d", jo2.getString("c"))
    assertEquals("f", jo2.getString("e"))

    assertEquals("Hello World", jo.getString("string"))
  }

  @Test
  fun testParseJsonArray() {
    val json = factory.parseJson("[1, \"2\"]")

    assertEquals(true, json is JsonArray)
    val ja = json as JsonArray

    assertEquals(2, ja.size())
    assertEquals(1, ja.getInt(0))
    assertEquals("2", ja.getString(1))
  }

  @Test
  fun testParseError() {
    assertFails { factory.parseJson("123") }
    assertFails { factory.parseJson("123.43") }
    assertFails { factory.parseJson("\"nihao\"") }
    assertFails { factory.parseJson("false") }
    assertFails { factory.parseJson("null") }
  }

  private fun <T> testJsonObjectPut(
      getter: JsonObject.(String) -> T,
      setter: JsonObject.(String, T) -> JsonObject,
      value1: T,
      value2: T
  ) {
    val jo = factory.newJsonObject()
    assertFails { jo.getter("name") }
    jo.setter("name", value1).setter("name", value2)
    assertEquals(value2, jo.getter("name"))
  }

  @Test
  fun testJsonObjectPutBoolean() = testJsonObjectPut(JsonObject::getBoolean, JsonObject::put, false, true)

  @Test
  fun testJsonObjectPutInt() = testJsonObjectPut(JsonObject::getInt, JsonObject::put, 12, 21)

  @Test
  fun testJsonObjectPutFloat() = testJsonObjectPut(JsonObject::getFloat, JsonObject::put, 12.0f, 21.0f)

  @Test
  fun testJsonObjectPutString() = testJsonObjectPut(JsonObject::getString, JsonObject::put, "12", "21")

  @Test
  fun testJsonObjectPutObject() {
    val jo = factory.newJsonObject()
    assertFails { jo.getJsonObject("name") }
    jo.put("name", factory.newJsonObject().put("name", "value"))
    val jo2 = jo.getJsonObject("name")
    assertEquals("value", jo2.getString("name"))
  }

  @Test
  fun testJsonObjectPutArray() {
    val jo = factory.newJsonObject()
    assertFails { jo.getJsonArray("name") }
    jo.put("name", factory.newJsonArray().add("value"))
    val ja2 = jo.getJsonArray("name")
    assertEquals(1, ja2.size())
    assertEquals("value", ja2.getString(0))
  }

  @Test
  fun testJsonObjectMixPut() {
    val jo = factory.newJsonObject()
    assertFails { jo.getBoolean("boolean") }
    assertFails { jo.getInt("boolean") }

    jo.put("boolean", false)
    assertEquals(false, jo.getBoolean("boolean"))
    assertFails { jo.getInt("boolean") }

    jo.put("boolean", 100)
    assertFails { jo.getBoolean("boolean") }
    assertEquals(100, jo.getInt("boolean"))
  }

  private fun newTestJsonObject(): JsonObject = factory.newJsonObject().apply {
    put("boolean", false)
    put("int", 100)
    put("float", 34.32f)
    put("string", "nihao")
    put("object", factory.newJsonObject().put("name", "value"))
    put("array", factory.newJsonArray().add("value"))
  }

  private fun assertTestJsonObject(jo: JsonObject) {
    assertEquals(false, jo.getBoolean("boolean"))
    assertEquals(100, jo.getInt("int"))
    assertEquals(34.32f, jo.getFloat("float"))
    assertEquals("nihao", jo.getString("string"))

    val jo2 = jo.getJsonObject("object")
    assertEquals("value", jo2.getString("name"))

    val ja2 = jo.getJsonArray("array")
    assertEquals("value", ja2.getString(0))
  }

  @Test
  fun testJsonObjectAddGet() = assertTestJsonObject(newTestJsonObject())

  @Test
  fun testJsonObjectToString() = assertTestJsonObject(factory.parseJson(newTestJsonObject().toString()) as JsonObject)

  private fun newTestJsonArray(): JsonArray = factory.newJsonArray().apply {
    add(false)
    add(100)
    add(34.32f)
    add("string")
    add(factory.newJsonObject().put("name", "value"))
    add(factory.newJsonArray().add("value"))
  }

  private fun assertTestJsonArray(ja: JsonArray) {
    assertEquals(6, ja.size())
    assertEquals(false, ja.getBoolean(0))
    assertEquals(100, ja.getInt(1))
    assertEquals(34.32f, ja.getFloat(2))
    assertEquals("string", ja.getString(3))

    val jo2 = ja.getJsonObject(4)
    assertEquals("value", jo2.getString("name"))

    val ja2 = ja.getJsonArray(5)
    assertEquals("value", ja2.getString(0))
  }

  @Test
  fun testJsonArrayAddGet() = assertTestJsonArray(newTestJsonArray())

  @Test
  fun testJsonArrayToString() = assertTestJsonArray(factory.parseJson(newTestJsonArray().toString()) as JsonArray)
}
