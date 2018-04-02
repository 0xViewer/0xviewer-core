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

import kotlinx.coroutines.experimental.runBlocking
import kotlin.test.AfterTest
import kotlin.test.BeforeTest

actual open class AsyncTest {

  @BeforeTest
  actual fun before() {
    runBlocking { onBefore() }
  }

  actual open suspend fun onBefore() {}

  actual fun <T> runTest(block: suspend () -> T)  {
    runBlocking { block() }
  }

  @AfterTest
  actual fun after() {
    runBlocking { onAfter() }
  }

  actual open suspend fun onAfter() {}
}
