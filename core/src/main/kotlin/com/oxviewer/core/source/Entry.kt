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

package com.oxviewer.core.source

/**
 * The abstract of entries got from [sections][Section].
 * Actually `Entry` should be a data class.
 */
sealed class Entry {

  /**
   * The key to identify an entry uniquely.
   */
  abstract val id: String

  /**
   * Last update time. `0` if can't get it.
   */
  open val version: Long = 0

  /**
   * It's title. `null` if can't get it.
   */
  open val title: String? = null

  /**
   * Several sentences about this content. `null` if can't get it.
   */
  open val description: String? = null

  /**
   * The url of its thumbnail. `null` if can't get it.
   */
  open val thumbnail: String? = null

  /**
   * The one who uploaded this content. `null` if can't get it.
   */
  open val uploader: String? = null

  /**
   * The language of this content. `null` if can't get it.
   *
   * It must be a three-letter identifiers of [ISO 639-3](https://en.wikipedia.org/wiki/List_of_ISO_639-3_codes), or `null`.
   */
  open val language: String? = null

  /**
   * The rating of this content, [0, 10]. `null` if can't get it.
   */
  open val rating: Float? = null

  /**
   * The tags of this content. `null` if can't get them.
   *
   * Tags is composed by several tag groups. A group is
   * composed by one namespace and several tag strings.
   * If namespace is not supported, use [NO_NAMESPACE].
   */
  open val tags: Map<String, List<String>>? = null

  companion object {
    const val NO_NAMESPACE: String = "__NO_NAMESPACE__"
  }
}

/**
 * A image entry.
 */
abstract class Image : Entry()

/**
 * A gallery entry. A gallery is a set of images.
 */
abstract class Gallery : Entry() {

  /**
   * The number of pages. `null` if can't get it.
   */
  open val pageNum: Int? = null
}

/**
 * A comic entry. A comic is a set of galleries.
 */
abstract class Comic : Entry() {

  /**
   * The number of chapters. `null` if can't get it.
   */
  open val chapterNum: Int? = null
}

/**
 * A sector entry. Instead of actual content, sector provides a section.
 */
abstract class Sector : Entry()
