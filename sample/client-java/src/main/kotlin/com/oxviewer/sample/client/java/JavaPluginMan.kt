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

import com.google.gson.reflect.TypeToken
import com.oxviewer.core.plugin.Plugin
import com.oxviewer.core.plugin.PluginInfo
import com.oxviewer.core.plugin.PluginMan
import com.oxviewer.core.util.WriteOnceProperty
import kotlinx.coroutines.experimental.Deferred
import kotlinx.coroutines.experimental.async
import java.io.File
import java.io.IOException
import java.net.URL
import java.nio.file.Files
import java.util.LinkedList

// TODO Ensure thread safety

private const val PATH_HOME = "release/0xViewer"

private const val NAME_PLUGINS_LIST = "plugins.json"

private const val NAME_PLUGINS_DIR = "plugins"

private val TYPE_LIST_PLUGIN_STATE = object : TypeToken<LinkedList<JavaPluginState>>() {}.type

class JavaPluginMan : PluginMan() {

  private val states = LinkedList<JavaPluginState>()

  private var homeDir by WriteOnceProperty<File>()
  private var pluginDir by WriteOnceProperty<File>()

  private var pluginFile by WriteOnceProperty<File>()

  /**
   * Ensure the file is a directory, or throw [IOException].
   *
   * @param name the name of the directory in error message
   * @param dir the file which must be a directory
   */
  private fun ensureDir(name: String, dir: File) {
    try {
      ensureDir(dir)
    } catch (e: Throwable) {
      throwIfFatal(e)
      throw IOException("Can't ensure $name directory: ${dir.path}")
    }
  }

  /**
   * Ensure the file is a regular file, or could be a regular file. Or throw [IOException].
   *
   * @param name the name of the directory in error message
   * @param file the file which must be a regular file, or could be a regular file
   */
  private fun ensureFile(name: String, file: File) {
    try {
      ensureFile(file)
    } catch (e: Throwable) {
      throwIfFatal(e)
      throw IOException("Can't ensure $name directory: ${file.path}")
    }
  }

  private fun PluginInfo.filename(): String = "$name-$uploader-$version.jar"

  override fun initialize(): Deferred<Unit> = async { run {
    // Ensure directories
    homeDir = File(PATH_HOME).canonicalFile
    ensureDir("home", homeDir)
    pluginDir = File(homeDir, NAME_PLUGINS_DIR)
    ensureDir("plugins", pluginDir)

    // Ensure files
    pluginFile = File(homeDir, NAME_PLUGINS_LIST)

    // Load all plugins
    val readState: List<JavaPluginState> = try {
      GSON.fromJson(pluginFile.reader(), TYPE_LIST_PLUGIN_STATE) ?: emptyList()
    } catch (e: Throwable) {
      throwIfFatal(e)
      emptyList()
    }
    readState.filter { it.enabled }.forEach { load(it) }
  }.toUnit() }

  private fun syncPluginFile() {
    pluginFile.writer().use {
      GSON.toJson(states, it)
    }
  }

  private fun addState(state: JavaPluginState) {
    states.add(state)
    syncPluginFile()
  }

  private fun removeState(state: JavaPluginState) {
    states.remove(state)
    syncPluginFile()
  }

  private fun load(state: JavaPluginState) {
    val file = File(pluginFile, state.info.filename())
    state.load(file)
  }

  private fun unload(state: JavaPluginState) {
    state.unload()
    // TODO tell UI to remove all views about this plugin
  }

  override fun install(info: PluginInfo): Deferred<JavaPluginState> = async { synchronized(JavaPluginMan@this) {
    notifyInstallStart(info)

    try {
      // 1. Find the installed plugin with the same package
      val oldState = getPluginState(name = info.name, uploader = info.uploader)

      // 2. Copy plugin to temp file in plugin dir
      val file = File(pluginDir, info.filename())
      val tempFile = if (file.exists()) File(pluginDir, info.filename() + ".temp") else file
      val originFile = File(URL(info.url).path)
      originFile.copyTo(tempFile)

      // 3. Uninstall the old plugin
      if (oldState != null) {
        unload(oldState)
        removeState(oldState)
      }

      // 4. Handle file
      if (tempFile != file) {
        file.delete()
        Files.move(tempFile.toPath(), file.toPath())
      }

      // 5. Add new plugin to list
      val newState = JavaPluginState(info = info, enabled = true)
      addState(newState)
      newState.load(file)

      notifyInstallSuccess(info, newState)
      return@synchronized newState
    } catch (e: Throwable) {
      throwIfFatal(e)
      notifyInstallFailure(info, e)
      throw e
    }
  } }

  override fun uninstall(plugin: Plugin): Deferred<Unit> = async { run {
    // TODO
  }.toUnit() }

  override fun getAllPluginStates(): List<JavaPluginState> = states.toList()

  override fun getPluginState(name: String, uploader: String): JavaPluginState? =
      states.find { it.info.name == name && it.info.uploader == uploader }
}
