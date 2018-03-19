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

import com.ehviewer.core.PublicAPI

/**
 * A HTML element.
 */
@PublicAPI
abstract class Element {

  /**
   * Returns the name of the tag for this element in low case.
   */
  @PublicAPI
  abstract val tagName: String

  /**
   * Returns the `id` attribute of this element. An empty string if not present.
   */
  @PublicAPI
  abstract val id: String

  /**
   * Returns all of the element's class names. An empty list if no class name.
   */
  @PublicAPI
  abstract val classNames: List<String>

  /**
   * Returns the element's inner HTML.
   */
  @PublicAPI
  abstract val innerHtml: String

  /**
   * Returns the element's outer HTML.
   */
  @PublicAPI
  abstract val outerHtml: String

  /**
   * Returns the element's text.
   */
  @PublicAPI
  abstract val text: String

  /**
   * Returns the element's parent.
   */
  @PublicAPI
  abstract val parent: Element?

  /**
   * Returns an attribute's value by its key. The key is case insensitive.
   * An empty list if not present.
   */
  @PublicAPI
  abstract fun attr(key: String): String

  /**
   * Returns `true` if this element has an attribute with the key.
   */
  @PublicAPI
  abstract fun hasAttr(key: String): Boolean

  /**
   * Find child elements that match the [CSS selector](https://developer.mozilla.org/en-US/docs/Web/CSS/CSS_Selectors).
   */
  @PublicAPI
  abstract fun select(cssSelector: String): List<Element>
}
