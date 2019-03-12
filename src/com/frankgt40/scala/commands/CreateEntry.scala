package com.frankgt40.scala.commands

import com.frankgt40.scala.files.{DirEntry, Directory}
import com.frankgt40.scala.filesystem.State

abstract class CreateEntry(name: String) extends Command {

  def checkIllegal(name: String): Boolean = name.contains(".")


  def doCreateEntry(state: State, name: String): State = {

    def updateStructure(currentDirectory: Directory, path: List[String], newEntry: DirEntry) : Directory = {
      if (path.isEmpty) currentDirectory.addEntry(newEntry)
      else {
        val oldEntry = currentDirectory.findEntry(path.head).asDirectory
        currentDirectory.replaceEntry(oldEntry.name, updateStructure(oldEntry, path.tail, newEntry))
      }
    }
    val wd = state.wd
    val fullPath = wd.path

    // 1. all the directories in the full path
    val allDirsInPath = wd.getAllFoldersInPath

    // 2. create new directory entry in the wd
//    val newDir = Directory.empty(wd.path, name)
    // TODO implement this
    val newEntry: DirEntry = createSpecificEntry(state)

    // 3. update the whole directory structure starting from the root
    // (the directory structure is IMMUTABLE)
    val newRoot = updateStructure(state.root, allDirsInPath, newEntry)

    // 4. find new working directory INSTANCE given wd's full path, in the NEW directory structure
    val newWd = newRoot.findDescendant(allDirsInPath)

    State(newRoot, newWd)
  }

  override def apply(state: State): State = {
    val wd = state.wd
    if (wd.hasEntry(name)) {
      state.setMessage(s"Entry $name already exists!")
    } else if (name.contains(Directory.SEPARATOR)) {
      // mkdir -p something/somethingElse
      state.setMessage(s"$name must not contain separator!")
    } else if (checkIllegal(name)) {
      state.setMessage(s"$name: illegal entry name!")
    } else {
      doCreateEntry(state, name)
    }
  }

  def createSpecificEntry(state: State): DirEntry
}
