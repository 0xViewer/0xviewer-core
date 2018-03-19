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
import org.w3c.dom.asList

class ElementBrowser(private val element: org.w3c.dom.Element): Element() {

  override val tagName: String = element.tagName.toLowerCase()

  override val id: String
    get() = element.id

  override val classNames: List<String> = element.classList.asList()

  override val innerHtml: String
    get() = element.innerHTML

  override val outerHtml: String
    get() = element.outerHTML

  override val text: String
    get() = element.textContent ?: ""

  override val parent: Element? = element.parentElement?.let { ElementBrowser(it) }

  override fun attr(key: String): String = element.getAttribute(key) ?: ""

  override fun hasAttr(key: String): Boolean = element.hasAttribute(key)

  override fun select(cssSelector: String): List<Element> {
    val nodes = element.querySelectorAll(cssSelector)
    val result = ArrayList<Element>(nodes.length)
    nodes.forEach {
      if (it != null && it.nodeType == Node.ELEMENT_NODE) {
        result.add(ElementBrowser(it.asDynamic())) // Avoid cast
      }
    }
    return result
  }

  override fun equals(other: Any?): Boolean = other is ElementBrowser && other.element == element

  override fun hashCode(): Int = element.hashCode()
}
