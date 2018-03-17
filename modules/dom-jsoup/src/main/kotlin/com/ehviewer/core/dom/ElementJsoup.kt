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

class ElementJsoup(private val element: org.jsoup.nodes.Element): Element() {

  override val tagName: String = element.tagName().toLowerCase()

  override val id: String
    get() = element.id()

  override val classNames: List<String> = element.attr("class").split("\\s+".toRegex()).filter { it.isNotEmpty() }

  override val innerHtml: String
    get() = element.html()

  override val outerHtml: String
    get() = element.outerHtml()

  override val text: String
    get() = element.wholeText()

  override fun attr(key: String): String = element.attr(key)

  override fun hasAttr(key: String): Boolean = element.hasAttr(key)

  override fun select(cssSelector: String): List<Element> =
      element.select(cssSelector).map { ElementJsoup(it) }
}
