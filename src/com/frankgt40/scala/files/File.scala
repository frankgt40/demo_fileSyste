package com.frankgt40.scala.files

import com.frankgt40.scala.filesystem.FilesystemException

class File(override  val parentPath: String, override val name: String, val contents: String) extends DirEntry (parentPath, name) {
  def appendContents(newContents: String): File = setContents(contents + "\n" + newContents)

  def setContents(newContents: String): File = new File(parentPath, name, newContents)
  def asDirectory: Directory =
    throw new FilesystemException("A file cannot be converted to a directory!")

  def asFile: File = this

  def getType: String = "File"

  override def isDirectory: Boolean = false

  override def isFile: Boolean = true
}

object File {
  def empty(parentPath: String, name: String): File =
    new File(parentPath, name, "")
}
