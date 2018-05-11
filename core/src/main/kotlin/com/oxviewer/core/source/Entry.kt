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

import com.oxviewer.core.PublicAPI

/**
 * The abstract of entries got from [sections][Section].
 * Actually `Entry` should be a data class.
 */
sealed class Entry {

  /**
   * The key to identify an entry uniquely.
   */
  @PublicAPI
  abstract val id: String

  /**
   * Last update time. `0` if can't get it.
   */
  @PublicAPI
  abstract val timestamp: Long

  /**
   * It's title. `null` if can't get it.
   */
  @PublicAPI
  abstract val title: String?

  /**
   * Several sentences about this content. `null` if can't get it.
   */
  @PublicAPI
  abstract val description: String?

  /**
   * The url of its thumbnail. `null` if can't get it.
   */
  @PublicAPI
  abstract val thumbnail: String?

  /**
   * The one who uploaded this content.`null` if can't get it.
   */
  @PublicAPI
  abstract val uploader: String?

  /**
   * The language of this content.`null` if can't get it.
   *
   * It must be a three-letter identifiers of ISO 639-3, or `null`.
   */
  @PublicAPI
  abstract val language: String?

  /**
   * The rating of this content, [0, 10]. `null` if can't get it.
   */
  @PublicAPI
  abstract val rating: Float?

  /**
   * The tags of this content. `null` if can't get them.
   *
   * Tags is composed by several tag groups. A group is
   * composed by one namespace and several tag strings.
   * If namespace is not supported, use [NO_NAMESPACE].
   */
  @PublicAPI
  abstract val tags: Map<String, List<String>>?

  companion object {
    const val NO_NAMESPACE: String = "__NO_NAMESPACE__"
  }
}

/**
 * A image entry.
 */
@PublicAPI
abstract class Image : Entry()

/**
 * A gallery entry. A gallery is a set of images.
 */
@PublicAPI
abstract class Gallery : Entry() {

  /**
   * The number of pages. `null` if can't get it.
   */
  abstract val pageNum: Int?
}

/**
 * A comic entry. A comic is a set of galleries.
 */
@PublicAPI
abstract class Comic : Entry() {

  /**
   * The number of chapters. `null` if can't get it.
   */
  abstract val chapterNum: Int?
}

/**
 * A sector entry. Instead of actual content, sector provides a section.
 */
@PublicAPI
abstract class Sector @PublicAPI constructor() : Entry()
