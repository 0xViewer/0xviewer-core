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

expect fun newDocumentFactory(): DocumentFactory

class DomTest {

  private val factory = newDocumentFactory()

  @Test
  fun testSelectTag() {
    var document = factory.newDocument("<div>Hello <div>world</div></div>")
    assertEquals(2, document.select("div").size)

    document = factory.newDocument("<div>Hello <a>world</a></div>")
    assertEquals(1, document.select("div").size)

    document = factory.newDocument("<div>Hello <a>world</a></div>")
    assertEquals(1, document.select("a").size)

    document = factory.newDocument("<div>Hello <a>world</a></div>")
    assertEquals(0, document.select("p").size)
  }

  @Test
  fun testSelectClass() {
    val document = factory.newDocument("<div class=\"column\">Hello <div class=\"column wrapper\">world</div></div>")
    assertEquals(2, document.select(".column").size)
    assertEquals(1, document.select(".wrapper").size)
  }

  @Test
  fun testSelectId() {
    val document = factory.newDocument("<div id=\"greet\">Hello <div id=\"name\">world</div></div>")
    assertEquals(1, document.select("#name").size)
    assertEquals(0, document.select("#greeting").size)
  }

  @Test
  fun testSelectAttribute() {
    val document = factory.newDocument("<div attr1=\"value1\">Hello <div attr1=\"value100\">world</div></div>")
    assertEquals(2, document.select("[attr1]").size)
    assertEquals(1, document.select("[attr1=value1]").size)
    assertEquals(0, document.select("[attr2]").size)
  }

  @Test
  fun testSelectAdjacentSibling() {
    val document = factory.newDocument("<div>Hello</div><p>!</p><div>world</div>")
    assertEquals(1, document.select("div + p + div").size)
    assertEquals(1, document.select("div + p").size)
    assertEquals(0, document.select("div + div").size)
  }

  @Test
  fun testSelectGeneralSibling() {
    val document = factory.newDocument("<div>Hello<a>?</a></div><p>!</p><div>world</div>")
    assertEquals(1, document.select("div ~ div").size)
    assertEquals(1, document.select("div ~ p").size)
    assertEquals(0, document.select("div ~ a").size)
  }

  @Test
  fun testSelectChildCombinator() {
    val document = factory.newDocument("<div>Hello<a>?</a></div><div>world</div>")
    assertEquals(1, document.select("div > a").size)
    assertEquals(0, document.select("div > div").size)
  }

  @Test
  fun testSelectDescendantCombinator() {
    val document = factory.newDocument("<div>Hello <div>world <a>!</a></div></div><p>!</p>")
    assertEquals(1, document.select("div a").size)
    assertEquals(1, document.select("div div").size)
    assertEquals(0, document.select("div p").size)
  }

  @Test
  fun testElementTagName() {
    val elements = factory.newDocument("<div>div</div><DIV>DIV</DIV>").select("div")
    assertEquals("div", elements[0].tagName)
    assertEquals("div", elements[1].tagName)
  }

  @Test
  fun testElementId() {
    val elements = factory.newDocument("" +
        "<div id=\"div\">div</div>" +
        "<div id=\" div \">div</div>" +
        "<div id=\"DIV\">div</div>" +
        "<div id=\"\">div</div>" +
        "<div>div</div>"
    ).select("div")
    assertEquals("div", elements[0].id)
    assertEquals(" div ", elements[1].id)
    assertEquals("DIV", elements[2].id)
    assertEquals("", elements[3].id)
    assertEquals("", elements[4].id)
  }

  @Test
  fun testElementClassName() {
    val elements = factory.newDocument("" +
        "<div class=\"div\">div</div>" +
        "<div class=\"div p\">div</div>" +
        "<div class=\" div p \">div</div>" +
        "<div class=\"div\tp\">div</div>" +
        "<div class=\"DIV\">div</div>" +
        "<div class=\"\">div</div>" +
        "<div>div</div>"
    ).select("div")
    assertEquals(listOf("div"), elements[0].classNames)
    assertEquals(listOf("div", "p"), elements[1].classNames)
    assertEquals(listOf("div", "p"), elements[2].classNames)
    assertEquals(listOf("div", "p"), elements[3].classNames)
    assertEquals(listOf("DIV"), elements[4].classNames)
    assertEquals(emptyList(), elements[5].classNames)
    assertEquals(emptyList(), elements[6].classNames)
  }

  @Test
  fun testElementAttr() {
    val elements = factory.newDocument("" +
        "<div key=\"value\">div</div>" +
        "<div key=\" VALUE\">div</div>" +
        "<div KEY=\"value\">div</div>" +
        "<div key=\"\">div</div>" +
        "<div>div</div>"
    ).select("div")
    assertEquals("value", elements[0].attr("key"))
    assertEquals("value", elements[0].attr("KEY"))
    assertEquals(" VALUE", elements[1].attr("key"))
    assertEquals("value", elements[2].attr("key"))
    assertEquals("", elements[3].attr("key"))
    assertEquals(false, elements[4].hasAttr("key"))
    assertEquals("", elements[4].attr("key"))
  }

  @Test
  fun testElementInnerHtml() {
    val elements = factory.newDocument("" +
        "<div>Hello<p>My</p>World</div>" +
        "<div></div>"
    ).select("div")
    // Some libraries might add white spaces to separate elements
    assertEquals("Hello<p>My</p>World", elements[0].innerHtml.filter { it.isWhitespace().not() })
    assertEquals("", elements[1].innerHtml)
  }

  @Test
  fun testElementOuterHtml() {
    val elements = factory.newDocument("" +
        "<div>Hello<p>My</p>World</div>"
    ).select("div")
    // Some libraries might add white spaces to separate elements
    assertEquals("<div>Hello<p>My</p>World</div>", elements[0].outerHtml.filter { it.isWhitespace().not() })
  }

  @Test
  fun testElementText() {
    val elements = factory.newDocument("" +
        "<div>Hello  <p>  My  </p>  <br/>World</div>" +
        "<div/>"
    ).select("div")
    assertEquals("Hello    My    World", elements[0].text)
    assertEquals("", elements[1].text)
  }
}