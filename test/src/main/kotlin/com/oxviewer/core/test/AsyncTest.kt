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

package com.oxviewer.core.test

import kotlin.test.AfterTest
import kotlin.test.BeforeTest

/**
 * A test class for async functions.
 *
 * Please follow all rules below.
 * 1. Use [onBefore] instead of [BeforeTest].
 * 2. Use [onAfter] instead of [AfterTest].
 * 3. Add tests like this.
 * ```
 * @Test
 * fun myTest() = runTest {
 *   ...
 * }
 * ```
 */
expect open class AsyncTest() {

  @BeforeTest
  fun before()

  open suspend fun onBefore()

  fun <T> runTest(block: suspend () -> T)

  @AfterTest
  fun after()

  open suspend fun onAfter()
}
