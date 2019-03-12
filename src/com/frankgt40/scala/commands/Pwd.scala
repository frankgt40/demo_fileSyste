package com.frankgt40.scala.commands

import com.frankgt40.scala.filesystem.State

class Pwd extends Command {
  override def apply(state: State): State = state.setMessage(state.wd.path)
}
