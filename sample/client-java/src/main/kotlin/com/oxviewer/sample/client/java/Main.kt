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

import com.google.gson.GsonBuilder
import com.oxviewer.core.PLUGIN_MAN
import com.oxviewer.core.UI
import com.oxviewer.core.plugin.PluginInfo
import kotlinx.coroutines.experimental.CoroutineDispatcher
import kotlinx.coroutines.experimental.Runnable
import kotlinx.coroutines.experimental.runBlocking
import java.io.File
import java.util.concurrent.LinkedBlockingQueue
import java.util.concurrent.TimeUnit
import kotlin.coroutines.experimental.CoroutineContext

val GSON = GsonBuilder()
    .registerTypeAdapter(JavaPluginState::class.java, JavaPluginStateJsonSerializer())
    .registerTypeAdapter(JavaPluginState::class.java, JavaPluginStateJsonDeserializer())
    .create()

private val TERMINAL = Runnable {}

private val ACTIONS = LinkedBlockingQueue<Runnable>()

private val MAIN_DISPATCHER = object : CoroutineDispatcher() {
  override fun dispatch(context: CoroutineContext, block: Runnable) {
    ACTIONS.add(block)
  }
}

private fun init() {
  UI = MAIN_DISPATCHER
  PLUGIN_MAN = JavaPluginMan()
}

private fun loop() {
  actions@ while (true) {
    val block = ACTIONS.poll(1, TimeUnit.MINUTES)
    when (block) {
      TERMINAL -> break@actions
      null -> continue@actions
      else -> block.run()
    }
  }
}

private suspend fun start() {

  PLUGIN_MAN.initialize().await()

  val file = File("../plugin-java/build/libs/plugin-java.jar")
  val info = PluginInfo(
      name = "sample_plugin",
      displayName = "Sample Plugin",
      uploader = "hippo",
      displayUploader = "Hippo",
      version = 1,
      displayVersion = "1.0",
      url = file.canonicalFile.toURI().toURL().toString()
  )

  PLUGIN_MAN.install(info).await()

  println(PLUGIN_MAN.getAllPluginStates())

  terminal()
}

fun main(args: Array<String>) = runBlocking {
  init()
  start()
  loop()
}

fun terminal() {
  ACTIONS.add(TERMINAL)
}
