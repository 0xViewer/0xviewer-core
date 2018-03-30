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

package com.ehviewer.core.source

import com.ehviewer.core.PublicAPI

/**
 * A section is a part of a [source][Source].
 * It provides a search action to get several [entries][Entry]
 * upon [search parameters][Parameters].
 */
@PublicAPI
abstract class Section @PublicAPI constructor() {

  /**
   * The display name of this section.
   */
  @PublicAPI
  abstract val name: String

  /**
   * Setups the [query pattern][Pattern] of this section.
   * The [query parameters][Parameters] generated from this query pattern
   * are passed to [search] to execute search action.
   *
   * It's called when creating the UI of this section or after
   * [invalidatePattern] being called.
   *
   * @see invalidatePattern
   * @see search
   */
  @PublicAPI
  abstract fun setupPattern(pattern: Pattern.Builder)

  /**
   * Declare that the [query pattern][Pattern] has changed.
   * [setupPattern] will be called soon.
   *
   * @see setupPattern
   * @see search
   */
  @PublicAPI
  fun invalidatePattern() {
    TODO()
  }

  /**
   * Paging searches with the [query parameters][Parameters].
   * The query parameters are generated from this [query pattern][Parameters]
   * which is configured in [setupPattern].
   *
   * @see setupPattern
   * @see invalidatePattern
   */
  @PublicAPI
  abstract suspend fun search(page: Int, parameters: Parameters): Result
}

/**
 * Paging search result.
 *
 * @property pages total pages
 * @property data result entries
 */
data class Result(val pages: Int, val data: List<Entry>)
