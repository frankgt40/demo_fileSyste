package com.frankgt40.scala.commands

import com.frankgt40.scala.filesystem.State

class Cat(filename: String) extends Command {
  override def apply(state: State): State = {
    val wd = state.wd

    val dirEntry = wd.findEntry(filename)
    if (null == dirEntry || !dirEntry.isFile) state.setMessage(s"$filename: no such file")
    else state.setMessage(dirEntry.asFile.contents)
  }
}
