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

package com.oxviewer.sample.plugin

import com.oxviewer.core.source.Parameters
import com.oxviewer.core.source.Pattern
import com.oxviewer.core.source.Result
import com.oxviewer.core.source.Section
import com.oxviewer.core.source.Text

class SampleSection : Section() {

  override val name: String = "Sample Section"

  override fun setupPattern(pattern: Pattern.Builder) {
    pattern.add(Text(key = "key", default = "default"))
  }

  override suspend fun search(page: Int, parameters: Parameters): Result = Result(0, emptyList())
}
