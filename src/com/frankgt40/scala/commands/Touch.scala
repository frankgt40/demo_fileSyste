package com.frankgt40.scala.commands

import com.frankgt40.scala.files.{DirEntry, File}
import com.frankgt40.scala.filesystem.State

class Touch(name: String) extends CreateEntry(name) {
  override def createSpecificEntry(state: State): DirEntry = File.empty(state.wd.path, name)
}
