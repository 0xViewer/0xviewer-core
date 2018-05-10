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
 */
@PublicAPI
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
   * It's title. `null` if no title.
   */
  @PublicAPI
  abstract val title: String?

  /**
   * Several sentences about this content. `null` if no description.
   */
  @PublicAPI
  abstract val description: String?

  /**
   * The url of its thumbnail. `null` if no thumbnail.
   */
  @PublicAPI
  abstract val thumbnail: String?

  /**
   * The one uploaded this content.`null` if no uploader.
   */
  @PublicAPI
  abstract val uploader: String?

  /**
   * The rating of this content, [0, 10]. `null` if no rating.
   */
  @PublicAPI
  abstract val rating: Float?

  // TODO how to define tags? Is namespace necessary?
}


/**
 * A image entry.
 */
@PublicAPI
abstract class Image : Entry() {

  /**
   * The url of the image.
   */
  @PublicAPI
  abstract val url: String
}

/**
 * A gallery entry. A gallery is a set of images.
 */
@PublicAPI
abstract class Gallery : Entry() {
  // TODO get images incrementally
}

/**
 * A comic entry. A comic is a set of galleries.
 */
@PublicAPI
abstract class Comic : Entry() {
  // TODO get chapters incrementally
}

/**
 * A sector entry. Instead of actual content, sector provides a section.
 */
@PublicAPI
abstract class Sector @PublicAPI constructor() : Entry() {

  abstract val section: Section
}
