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

package com.oxviewer.core

import com.oxviewer.core.dom.DocumentFactory
import com.oxviewer.core.json.JsonFactory
import com.oxviewer.core.plugin.PluginMan
import com.oxviewer.core.util.WriteOnceProperty
import kotlin.coroutines.experimental.CoroutineContext

var UI by WriteOnceProperty<CoroutineContext>()

var JSON_FACTORY by WriteOnceProperty<JsonFactory>()

var DOCUMENT_FACTORY by WriteOnceProperty<DocumentFactory>()

var PLUGIN_MAN by WriteOnceProperty<PluginMan>()
