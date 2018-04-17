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

package com.oxviewer.sample.client.java

import java.io.File
import java.io.IOException
import java.nio.file.Files

/**
 * Discards the value.
 */
fun Any?.toUnit() = Unit

/**
 * Ensure the file is a directory.
 * Throws [IOException] if can't make the guarantee.
 */
@Throws(IOException::class)
fun ensureDir(directory: File) {
  val path = directory.toPath()

  if (Files.exists(path) && !Files.isDirectory(path)) {
    throw IOException("The file exists, but it's not a directory: ${directory.canonicalPath}")
  }

  if (Files.isDirectory(path)) {
    return
  }

  Files.createDirectories(path)
  if (!Files.isDirectory(path)) {
    throw IOException("Can't create directory: ${directory.canonicalPath}")
  }
}

/**
 * Ensure the file is a regular file, or could be a regular file.
 * Throws [IOException] if can't make the guarantee.
 */
@Throws(IOException::class)
fun ensureFile(file: File) {
  val path = file.toPath()

  if (!Files.exists(path)) {
    return
  }

  if (Files.isRegularFile(path)) {
    return
  }

  throw IOException("The file exists, but it's not a regular file: ${file.canonicalPath}")
}

/**
 * Throws the throwable if it's fatal.
 */
fun throwIfFatal(t: Throwable) {
  // values here derived from https://github.com/ReactiveX/RxJava/issues/748#issuecomment-32471495
  when (t) {
    is VirtualMachineError -> throw t
    is ThreadDeath -> throw t
    is LinkageError -> throw t
  }
}




