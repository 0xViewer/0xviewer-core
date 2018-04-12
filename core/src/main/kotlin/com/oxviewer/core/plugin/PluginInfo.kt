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

package com.oxviewer.core.plugin

import com.oxviewer.core.PublicAPI

// TODO Localized display information

/**
 * The metadata of a plugin.
 * The primary key of a plugin is the pair of the name and uploader.
 */
data class PluginInfo(
    @PublicAPI val name: String,
    @PublicAPI val displayName: String,

    @PublicAPI val uploader: String,
    @PublicAPI val displayUploader: String,

    @PublicAPI val version: Int,
    @PublicAPI val displayVersion: String,

    @PublicAPI val url: String
) {

  /**
   * Packages with the same name and uploader are the same packages.
   */
  fun isSamePackage(info: PluginInfo): Boolean = name == info.name && uploader == info.uploader
}
