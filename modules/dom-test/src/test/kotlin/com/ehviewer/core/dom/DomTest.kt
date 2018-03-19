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

  private val documentSelector: (String, String) -> List<Element> =
      { html, query -> factory.newDocument(html).select(query)}

  private val elementSelector: (String, String) -> List<Element> =
      { html, query -> factory.newDocument(html).rootElement.select(query)}

  private fun testSelectTag(selector: (String, String) -> List<Element>) {
    assertEquals(2, selector(
        "<div>Hello <div>world</div></div>",
        "div"
    ).size)
    assertEquals(1, selector(
        "<div>Hello <a>world</a></div>",
        "div"
    ).size)
    assertEquals(1, selector(
        "<div>Hello <a>world</a></div>",
        "a"
    ).size)
    assertEquals(0, selector(
        "<div>Hello <a>world</a></div>",
        "p"
    ).size)
    assertEquals(1, selector(
        "<DIV>Hello <a>world</a></DIV>",
        "div"
    ).size)
    assertEquals(1, selector(
        "<div>Hello <a>world</a></div>",
        "DIV"
    ).size)
  }

  @Test
  fun testDocumentSelectTag() = testSelectTag(documentSelector)

  @Test
  fun testElementSelectTag() = testSelectTag(elementSelector)

  private fun testSelectClass(selector: (String, String) -> List<Element>) {
    assertEquals(2, selector(
        "<div class=\"column\">Hello <div class=\"column wrapper\">world</div></div>",
        ".column"
    ).size)
    assertEquals(1, selector(
        "<div class=\"column\">Hello <div class=\"column wrapper\">world</div></div>",
        ".wrapper"
    ).size)
    assertEquals(1, selector(
        "<div class=\"column\">Hello <div class=\"column wrapper\">world</div></div>",
        ".column.wrapper"
    ).size)
    assertEquals(1, selector(
        "<div class=\"column\">Hello <div class=\"column wrapper\">world</div></div>",
        ".wrapper.column"
    ).size)
  }

  @Test
  fun testDocumentSelectClass() = testSelectClass(documentSelector)

  @Test
  fun testElementSelectClass() = testSelectClass(elementSelector)

  private fun testSelectId(selector: (String, String) -> List<Element>) {
    assertEquals(1, selector(
        "<div id=\"greet\">Hello <div id=\"name\">world</div></div>",
        "#name"
    ).size)
    assertEquals(0, selector(
        "<div id=\"greet\">Hello <div id=\"name\">world</div></div>",
        "#greeting"
    ).size)
  }

  @Test
  fun testDocumentSelectId() = testSelectId(documentSelector)

  @Test
  fun testElementSelectId() = testSelectId(elementSelector)

  private fun testSelectAttribute(selector: (String, String) -> List<Element>) {
    assertEquals(2, selector(
        "<div attr1=\"value1\">Hello <div attr1=\"value100\">world</div></div>",
        "[attr1]"
    ).size)
    assertEquals(1, selector(
        "<div attr1=\"value1\">Hello <div attr1=\"value100\">world</div></div>",
        "[attr1=value1]"
    ).size)
    assertEquals(0, selector(
        "<div attr1=\"value1\">Hello <div attr1=\"value100\">world</div></div>",
        "[attr2]"
    ).size)
  }

  @Test
  fun testDocumentSelectAttribute() = testSelectAttribute(documentSelector)

  @Test
  fun testElementSelectAttribute() = testSelectAttribute(elementSelector)

  private fun testSelectAdjacentSibling(selector: (String, String) -> List<Element>) {
    assertEquals(1, selector(
        "<div>Hello</div><p>!</p><div>world</div>",
        "div + p + div"
    ).size)
    assertEquals(1, selector(
        "<div>Hello</div><p>!</p><div>world</div>",
        "div + p"
    ).size)
    assertEquals(0, selector(
        "<div>Hello</div><p>!</p><div>world</div>",
        "div + div"
    ).size)
  }

  @Test
  fun testDocumentSelectAdjacentSibling() = testSelectAdjacentSibling(documentSelector)

  @Test
  fun testElementSelectAdjacentSibling() = testSelectAdjacentSibling(elementSelector)

  private fun testSelectGeneralSibling(selector: (String, String) -> List<Element>) {
    assertEquals(1, selector(
        "<div>Hello<a>?</a></div><p>!</p><div>world</div>",
        "div ~ div"
    ).size)
    assertEquals(1, selector(
        "<div>Hello<a>?</a></div><p>!</p><div>world</div>",
        "div ~ p"
    ).size)
    assertEquals(0, selector(
        "<div>Hello<a>?</a></div><p>!</p><div>world</div>",
        "div ~ a"
    ).size)
  }

  @Test
  fun testDocumentSelectGeneralSibling() = testSelectGeneralSibling(documentSelector)

  @Test
  fun testElementSelectGeneralSibling() = testSelectGeneralSibling(elementSelector)

  private fun testSelectChildCombinator(selector: (String, String) -> List<Element>) {
    assertEquals(1, selector(
        "<div>Hello<a>?</a></div><div>world</div>",
        "div > a"
    ).size)
    assertEquals(0, selector(
        "<div>Hello<a>?</a></div><div>world</div>",
        "div > div"
    ).size)
  }

  @Test
  fun testDocumentSelectChildCombinator() = testSelectChildCombinator(documentSelector)

  @Test
  fun testElementSelectChildCombinator() = testSelectChildCombinator(elementSelector)

  private fun testSelectDescendantCombinator(selector: (String, String) -> List<Element>) {
    assertEquals(1, selector(
        "<div>Hello <div>world <a>!</a></div></div><p>!</p>",
        "div a"
    ).size)
    assertEquals(1, selector(
        "<div>Hello <div>world <a>!</a></div></div><p>!</p>",
        "div div"
    ).size)
    assertEquals(1, selector(
        "<div>Hello <div>world <a>!</a></div></div><p>!</p>",
        "div div"
    ).size)
  }

  @Test
  fun testDocumentSelectDescendantCombinator() = testSelectDescendantCombinator(documentSelector)

  @Test
  fun testElementSelectDescendantCombinator() = testSelectDescendantCombinator(elementSelector)

  private fun testSelectMix(selector: (String, String) -> List<Element>) {
    assertEquals(1, selector(
        "<div class=\"cl1 cl2 cl3\" key=\"value\">Hello <div>world <a>!</a></div></div><p>!</p>",
        "div.cl3.cl1[key=value] > div > a"
    ).size)
  }

  @Test
  fun testDocumentSelectMix() = testSelectMix(documentSelector)

  @Test
  fun testElementSelectMix() = testSelectMix(elementSelector)

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

  @Test
  fun testElementParent() {
    val element = factory.newDocument("" +
        "<div>Hello<p>My</p>World</div>"
    ).rootElement
    assertEquals(null, element.parent)
    assertEquals("div", element.select("p")[0].parent?.tagName)
  }

  @Test
  fun testElementChildren() {
    val document = factory.newDocument("" +
        "<div>Hello<p>My</p><a>Little</a>World</div>"
    )
    val element = document.select("div")[0]
    val children = element.children
    assertEquals(2, children.size)
    assertEquals("p", children[0].tagName)
    assertEquals("a", children[1].tagName)
  }

  @Test
  fun testElementNextSibling() {
    val document = factory.newDocument("" +
        "<div>Hello<p>My</p><a>Little</a>World</div>"
    )
    assertEquals("a", document.select("p")[0].nextSibling?.tagName)
    assertEquals(null, document.select("a")[0].nextSibling?.tagName)
  }

  @Test
  fun testElementPreviousSibling() {
    val document = factory.newDocument("" +
        "<div>Hello<p>My</p><a>Little</a>World</div>"
    )
    assertEquals(null, document.select("p")[0].previousSibling?.tagName)
    assertEquals("p", document.select("a")[0].previousSibling?.tagName)
  }

  @Test
  fun testElementEqualsHashCode() {
    val document = factory.newDocument("" +
        "<div>Hello World</div>"
    )
    assertEquals(true, document.select("div")[0] == document.select("div")[0])
    assertEquals(true, document.select("div")[0].hashCode() == document.select("div")[0].hashCode())
    assertEquals(false, document.select("div")[0] === document.select("div")[0])
  }
}
