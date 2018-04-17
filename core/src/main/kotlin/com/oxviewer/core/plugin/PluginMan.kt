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

import com.oxviewer.core.UI
import com.oxviewer.core.util.toUnit
import kotlinx.coroutines.experimental.Deferred
import kotlinx.coroutines.experimental.launch

/**
 * The Plugin manager.
 */
abstract class PluginMan {

  private val listeners = mutableListOf<Listener>()

  abstract fun initialize(): Deferred<Unit>

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
  abstract fun install(info: PluginInfo): Deferred<PluginState>

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
   * Returns all installed plugins. The order of the list is undefined.
   */
  abstract fun getAllPluginStates(): List<PluginState>

  /**
   * Returns the plugin with the same name and uploader.
   */
  abstract fun getPluginState(name: String, uploader: String): PluginState?

  /**
   * Registers a listener to listen all events.
   *
   * @see unregisterListener
   */
  fun registerListener(listener: Listener) {
    listeners.add(listener)
  }

  /**
   * Unregisters the listener.
   *
   * @see registerListener
   */
  fun unregisterListener(listener: Listener) {
    listeners.remove(listener)
  }

  private inline fun notifyListener(crossinline action: Listener.() -> Unit): Unit = launch(UI) {
    listeners.toList().forEach { if (it in listeners) it.action() }
  }.toUnit()

  protected fun notifyInstallStart(info: PluginInfo): Unit =
      notifyListener { onInstallStart(info) }

  protected fun notifyInstallProgress(info: PluginInfo, progress: Float): Unit =
      notifyListener { onInstallProgress(info, progress) }

  protected fun notifyInstallSuccess(info: PluginInfo, state: PluginState): Unit =
      notifyListener { onInstallSuccess(info, state) }

  protected fun notifyInstallFailure(info: PluginInfo, error: Throwable): Unit =
      notifyListener { onInstallFailure(info, error) }

  protected fun notifyUninstallStart(plugin: Plugin): Unit =
      notifyListener { onUninstallStart(plugin) }

  protected fun notifyUninstallSuccess(info: PluginInfo): Unit =
      notifyListener { onUninstallSuccess(info) }

  protected fun notifyUninstallFailure(info: PluginInfo, error: Throwable): Unit =
      notifyListener { onUninstallFailure(info, error) }

  open class Listener {

    fun onInstallStart(info: PluginInfo) {}

    fun onInstallProgress(info: PluginInfo, progress: Float) {}

    fun onInstallSuccess(info: PluginInfo, state: PluginState) {}

    fun onInstallFailure(info: PluginInfo, error: Throwable) {}

    fun onUninstallStart(plugin: Plugin) {}

    fun onUninstallSuccess(info: PluginInfo) {}

    fun onUninstallFailure(info: PluginInfo, error: Throwable) {}
  }
}
