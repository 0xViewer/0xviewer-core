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

package com.ehviewer.core.http

import kotlinx.coroutines.experimental.suspendCancellableCoroutine
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import java.io.IOException

/**
 * Suspend extension that allows suspend [Call] inside coroutine.
 *
 * @return Result of request or throw exception
 */
// https://github.com/gildor/kotlin-coroutines-okhttp/blob/25bea7dc776c1697779f9a27d6ebc9d9717eb36d/src/main/kotlin/ru/gildor/coroutines/okhttp/CallAwait.kt
suspend fun Call.await(): Response {
  return suspendCancellableCoroutine { continuation ->
    enqueue(object : Callback {
      override fun onResponse(call: Call, response: Response) {
        continuation.resume(response)
      }

      override fun onFailure(call: Call, e: IOException) {
        // Don't bother with resuming the continuation if it is already cancelled.
        if (continuation.isCancelled) return
        continuation.resumeWithException(e)
      }
    })

    continuation.invokeOnCompletion {
      if (continuation.isCancelled)
        try {
          cancel()
        } catch (ex: Throwable) {
          //Ignore cancel exception
        }
    }
  }
}
