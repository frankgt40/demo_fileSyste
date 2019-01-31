package com.frankgt40.scala.filesystem

import java.util.Scanner

import com.frankgt40.scala.commands.Command
import com.frankgt40.scala.files.Directory

object Filesystem extends App {

  val root = Directory.ROOT
  var state = State(root, root) // whenever need a stateful program, we can only use var
  val scanner = new Scanner(System.in)

  while (true) {
    state.show
    val input = scanner.nextLine()
    state = Command.from(input).apply(state)
  }
}
