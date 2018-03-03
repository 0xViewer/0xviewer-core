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

import kotlin.test.Test
import kotlin.test.assertEquals

class DomTest {

  @Test
  fun testSelectTag() {
    var document = Document("<div>Hello <div>world</div></div>", null)
    assertEquals(2, document.select("div").size)

    document = Document("<div>Hello <a>world</a></div>", null)
    assertEquals(1, document.select("div").size)

    document = Document("<div>Hello <a>world</a></div>", null)
    assertEquals(1, document.select("a").size)

    document = Document("<div>Hello <a>world</a></div>", null)
    assertEquals(0, document.select("p").size)
  }

  @Test
  fun testSelectClass() {
    val document = Document("<div class=\"column\">Hello <div class=\"column wrapper\">world</div></div>", null)
    assertEquals(2, document.select(".column").size)
    assertEquals(1, document.select(".wrapper").size)
  }

  @Test
  fun testSelectId() {
    val document = Document("<div id=\"greet\">Hello <div id=\"name\">world</div></div>", null)
    assertEquals(1, document.select("#name").size)
    assertEquals(0, document.select("#greeting").size)
  }

  @Test
  fun testSelectAttribute() {
    val document = Document("<div attr1=\"value1\">Hello <div attr1=\"value100\">world</div></div>", null)
    assertEquals(2, document.select("[attr1]").size)
    assertEquals(1, document.select("[attr1=value1]").size)
    assertEquals(0, document.select("[attr2]").size)
  }

  @Test
  fun testSelectAdjacentSibling() {
    val document = Document("<div>Hello</div><p>!</p><div>world</div>", null)
    assertEquals(1, document.select("div + p + div").size)
    assertEquals(1, document.select("div + p").size)
    assertEquals(0, document.select("div + div").size)
  }

  @Test
  fun testSelectGeneralSibling() {
    val document = Document("<div>Hello<a>?</a></div><p>!</p><div>world</div>", null)
    assertEquals(1, document.select("div ~ div").size)
    assertEquals(1, document.select("div ~ p").size)
    assertEquals(0, document.select("div ~ a").size)
  }

  @Test
  fun testSelectChildCombinator() {
    val document = Document("<div>Hello<a>?</a></div><div>world</div>", null)
    assertEquals(1, document.select("div > a").size)
    assertEquals(0, document.select("div > div").size)
  }

  @Test
  fun testSelectDescendantCombinator() {
    val document = Document("<div>Hello <div>world <a>!</a></div></div><p>!</p>", null)
    assertEquals(1, document.select("div a").size)
    assertEquals(1, document.select("div div").size)
    assertEquals(0, document.select("div p").size)
  }
}
