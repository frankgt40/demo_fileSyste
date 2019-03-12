package com.frankgt40.scala.commands
import com.frankgt40.scala.files.{DirEntry, Directory}
import com.frankgt40.scala.filesystem.State

class Mkdir(name: String) extends CreateEntry(name) {
  override def createSpecificEntry(state: State): DirEntry =
    Directory.empty(state.wd.path, name)
}
