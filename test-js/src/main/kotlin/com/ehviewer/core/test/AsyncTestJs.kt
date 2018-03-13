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

import kotlinx.coroutines.experimental.promise
import kotlin.test.AfterTest
import kotlin.test.BeforeTest

actual open class AsyncTest {

  private companion object {
    private var asyncTestRunning = false
  }

  private var beforeDone = false
  private var testDone = false

  @BeforeTest
  actual fun before(): dynamic = promise {
    wait { !asyncTestRunning }
    asyncTestRunning = true

    try {
      onBefore()
    } finally {
      beforeDone = true
    }
  }

  actual open suspend fun onBefore() {}

  actual fun <T> runTest(block: suspend () -> T): dynamic = promise {
    wait { beforeDone }
    try {
      block()
    } finally {
      testDone = true
    }
  }

  @AfterTest
  actual fun after(): dynamic = promise {
    wait { testDone }
    try {
      onAfter()
    } finally {
      asyncTestRunning = false
    }
  }

  actual open suspend fun onAfter() {}
}
