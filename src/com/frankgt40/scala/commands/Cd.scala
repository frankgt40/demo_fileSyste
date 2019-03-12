package com.frankgt40.scala.commands

import com.frankgt40.scala.files.{DirEntry, Directory}
import com.frankgt40.scala.filesystem.State

import scala.annotation.tailrec

class Cd(dir: String) extends Command {
  override def apply(state: State): State = {
    /*
      cd /something/somethingElse (absolute path)
      cd a/b/c/  (relative to the current working directory
     */
    // 1. find root
    val root = state.root
    val wd = state.wd

    // 2. find the absolute path of the directory I want to cd to
    val absolutePath =
      if (dir.startsWith(Directory.SEPARATOR)) dir
      else if (wd.isRoot) wd.path + dir
      else wd.path + Directory.SEPARATOR + dir

    // 3. find the diretory to cd to , given the path
    val destinationDirectory = doFindEntry(root, absolutePath)

    // 4. change the state given the new directory
    if (destinationDirectory == null || !destinationDirectory.isDirectory)
      state.setMessage(s"$dir: no such diretory!")
    else
      State(root, destinationDirectory.asDirectory)
  }

  @tailrec
  private def collapseRelativeTokens(path: List[String], result: List[String]): List[String] = {
    if (path.isEmpty) result
    else if (".".equals(path.head)) collapseRelativeTokens(path.tail, result)
    else if ("..".equals(path.head)) {
      if (result.isEmpty) null
      else collapseRelativeTokens(path.tail, result.init)
    } else collapseRelativeTokens(path.tail, result :+ path.head)
  }

  def doFindEntry(root: Directory, path: String): DirEntry = {
    @tailrec
    def findEntryHelper(currentDirectory: Directory, path: List[String]): DirEntry =
      if (path.isEmpty || path.head.isEmpty) currentDirectory
      else if (path.tail.isEmpty) currentDirectory.findEntry(path.head)
      else {
        val nextDir = currentDirectory.findEntry(path.head)
        if (null == nextDir || !nextDir.isDirectory) null
        else findEntryHelper(nextDir.asDirectory, path.tail)
      }
    // 1. tokens
    val tokens: List[String] = path.substring(1).split(Directory.SEPARATOR).toList

    val newTokens = collapseRelativeTokens(tokens, List())
    // 1.5 eliminate/collapse relative tokens
    // 2. navigate to the correct entry
    if (null == newTokens) null
    else findEntryHelper(root, newTokens)
  }
}
