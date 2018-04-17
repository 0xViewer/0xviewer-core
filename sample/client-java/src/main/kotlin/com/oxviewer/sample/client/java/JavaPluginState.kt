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

package com.oxviewer.sample.client.java

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.google.gson.JsonSerializationContext
import com.google.gson.JsonSerializer
import com.oxviewer.core.plugin.Plugin
import com.oxviewer.core.plugin.PluginInfo
import com.oxviewer.core.plugin.PluginState
import java.io.BufferedReader
import java.io.File
import java.io.InputStreamReader
import java.lang.reflect.Type
import java.net.URLClassLoader

private const val PLUGIN_FILE = "META-INF/0xViewer/plugin"
private const val PLUGIN_FILE_BUFFER_SIZE = 128 // Only the class name, 128 is enough

class JavaPluginState(info: PluginInfo, enabled: Boolean) : PluginState(info, enabled) {

  var error: Throwable? = null
    private set

  fun load(file: File) {
    if (plugin != null) {
      throw IllegalStateException("This plugin is already loaded")
    }

    try {
      val classLoader = URLClassLoader(arrayOf(file.toURI().toURL()), this.javaClass.classLoader)
      val stream = classLoader.getResourceAsStream(PLUGIN_FILE)
      val reader = BufferedReader(InputStreamReader(stream), PLUGIN_FILE_BUFFER_SIZE)
      val className = reader.readLine()
      plugin = classLoader.loadClass(className).newInstance() as Plugin
    } catch (e: Throwable) {
      throwIfFatal(e)
      error = e
    }
  }

  fun unload() {
    this.plugin = null
  }
}

class JavaPluginStateJsonSerializer : JsonSerializer<JavaPluginState> {
  override fun serialize(
      src: JavaPluginState,
      typeOfSrc: Type?,
      context: JsonSerializationContext?
  ): JsonElement = JsonObject().also {
    it.add("info", GSON.toJsonTree(src.info))
    it.addProperty("enabled", src.enabled)
  }
}

class JavaPluginStateJsonDeserializer : JsonDeserializer<JavaPluginState> {
  override fun deserialize(
      json: JsonElement,
      typeOfT: Type?,
      context: JsonDeserializationContext?
  ): JavaPluginState {
    val obj = json.asJsonObject
    val info = GSON.fromJson(obj.get("info"), PluginInfo::class.java)
    val enabled = obj.get("enabled").asBoolean
    return JavaPluginState(info, enabled)
  }
}
