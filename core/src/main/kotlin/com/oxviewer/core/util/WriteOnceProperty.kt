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

package com.oxviewer.core.util

import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

class WriteOnceProperty<T: Any> : ReadWriteProperty<Any?, T> {

  private var value: T? = null

  override fun getValue(thisRef: Any?, property: KProperty<*>): T {
    return value ?: throw IllegalStateException("Property ${property.name} should be initialized before get.")
  }

  override fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
    if (this.value != null) {
      throw IllegalStateException("Property ${property.name} has been initialized.")
    }
    this.value = value
  }
}
