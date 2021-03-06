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

package com.oxviewer.core.dom

/**
 * A HTML Document.
 */
abstract class Document {

  /**
   * Returns the root element of this document.
   * The tag name and attributes of the element are uncertain.
   */
  abstract val rootElement: Element

  /**
   * Find elements that match the [CSS selector](https://developer.mozilla.org/en-US/docs/Web/CSS/CSS_Selectors).
   */
  abstract fun select(cssSelector: String): List<Element>
}
