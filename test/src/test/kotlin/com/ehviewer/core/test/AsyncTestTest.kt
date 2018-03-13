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

package com.ehviewer.core.test

import kotlinx.coroutines.experimental.delay
import kotlin.test.Test
import kotlin.test.assertEquals

class AsyncTestTest : AsyncTest() {

  private var beforeDone = false
  private var testDone = false
  private var afterDone = false

  override suspend fun onBefore() {
    assertEquals(false, beforeDone)
    assertEquals(false, testDone)
    assertEquals(false, afterDone)
    delay(100)
    assertEquals(false, beforeDone)
    assertEquals(false, testDone)
    assertEquals(false, afterDone)
    beforeDone = true
  }

  @Test
  fun test1() = runTest {
    assertEquals(true, beforeDone)
    assertEquals(false, testDone)
    assertEquals(false, afterDone)
    delay(100)
    assertEquals(true, beforeDone)
    assertEquals(false, testDone)
    assertEquals(false, afterDone)
    testDone = true
  }

  @Test
  fun test2() = runTest {
    assertEquals(true, beforeDone)
    assertEquals(false, testDone)
    assertEquals(false, afterDone)
    delay(100)
    assertEquals(true, beforeDone)
    assertEquals(false, testDone)
    assertEquals(false, afterDone)
    testDone = true
  }

  @Test
  fun test3() = runTest {
    assertEquals(true, beforeDone)
    assertEquals(false, testDone)
    assertEquals(false, afterDone)
    delay(100)
    assertEquals(true, beforeDone)
    assertEquals(false, testDone)
    assertEquals(false, afterDone)
    testDone = true
  }

  override suspend fun onAfter() {
    assertEquals(true, beforeDone)
    assertEquals(true, testDone)
    assertEquals(false, afterDone)
    delay(100)
    assertEquals(true, beforeDone)
    assertEquals(true, testDone)
    assertEquals(false, afterDone)
    afterDone = true
  }
}
