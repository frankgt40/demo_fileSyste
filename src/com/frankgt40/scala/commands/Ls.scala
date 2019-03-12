package com.frankgt40.scala.commands

import com.frankgt40.scala.files.DirEntry
import com.frankgt40.scala.filesystem.State

import scala.annotation.tailrec

class Ls extends Command {
  override def apply(state: State): State = {
    val contents = state.wd.contents
    val niceOutput = createNiceOutput(contents)
    state.setMessage(niceOutput)
  }
  def createNiceOutput(entries: List[DirEntry]): String = {
    @tailrec
    def createNiceOutputHeler(contents: List[DirEntry], acc: String): String = {
      if (contents.isEmpty) acc
      else createNiceOutputHeler(contents.tail, acc + contents.head.name + "[" + contents.head.getType + "]\t")
    }
    createNiceOutputHeler(entries, "") + "\n"
  }
}
