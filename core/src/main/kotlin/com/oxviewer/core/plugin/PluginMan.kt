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

package com.oxviewer.core.plugin

import kotlinx.coroutines.experimental.Deferred

/**
 * The Plugin manager.
 */
abstract class PluginMan {

  /**
   * Installs a plugin. This method is non-blocking.
   * The return value is a non-lazy `Deferred`.
   *
   * @see uninstall
   * @see Listener.onInstallStart
   * @see Listener.onInstallProgress
   * @see Listener.onInstallSuccess
   * @see Listener.onInstallFailure
   */
  abstract fun install(info: PluginInfo): Deferred<Plugin>

  /**
   * Uninstalls a plugin. This method is non-blocking.
   * The return value is a non-lazy `Deferred`.
   *
   * @see install
   * @see Listener.onUninstallStart
   * @see Listener.onUninstallSuccess
   * @see Listener.onUninstallFailure
   */
  abstract fun uninstall(plugin: Plugin): Deferred<Unit>

  /**
   * Registers a listener to listen all events.
   *
   * @see unregisterListener
   */
  abstract fun registerListener(listener: Listener)

  /**
   * Unregisters the listener.
   *
   * @see registerListener
   */
  abstract fun unregisterListener(listener: Listener)

  /**
   * Returns all installed plugins. The order of the list is undefined.
   */
  abstract fun getAllPlugins(): List<Plugin>

  /**
   * Returns the plugin with the same name and uploader.
   */
  abstract fun getPlugin(name: String, uploader: String): Plugin

  open class Listener {

    fun onInstallStart(info: PluginInfo) {}

    fun onInstallProgress(info: PluginInfo, float: Float) {}

    fun onInstallSuccess(info: PluginInfo, plugin: Plugin) {}

    fun onInstallFailure(info: PluginInfo, throwable: Throwable) {}

    fun onUninstallStart(plugin: Plugin) {}

    fun onUninstallSuccess(info: PluginInfo) {}

    fun onUninstallFailure(info: PluginInfo) {}
  }
}
