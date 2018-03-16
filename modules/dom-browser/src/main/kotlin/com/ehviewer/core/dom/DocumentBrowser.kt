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

package com.ehviewer.core.dom

import org.w3c.dom.Node
import org.w3c.dom.NodeList
import org.w3c.dom.get

class DocumentBrowser(private val document: org.w3c.dom.Document) : Document() {

  override val rootElement: Element = ElementBrowser(document.documentElement!!.lastElementChild!!.firstElementChild!!)

  private inline fun NodeList.forEach(action: (Node) -> Unit) {
    for (i in 0 until length) {
      get(i)?.also { action(it) }
    }
  }

  override fun select(cssSelector: String): List<Element> {
    val nodes = document.querySelectorAll(cssSelector)
    val result = ArrayList<Element>(nodes.length)
    nodes.forEach {
      if (it.nodeType == Node.ELEMENT_NODE) {
        result.add(ElementBrowser(it.asDynamic())) // Avoid cast
      }
    }
    return result
  }
}
