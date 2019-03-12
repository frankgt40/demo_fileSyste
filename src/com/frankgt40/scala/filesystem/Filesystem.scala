package com.frankgt40.scala.filesystem

import java.util.Scanner

import com.frankgt40.scala.commands.Command
import com.frankgt40.scala.files.Directory

object Filesystem extends App {

  val root = Directory.ROOT

  // [1, 2, 3, 4]
  /*
  0 (op) 1 => 1
  1 (op) 2 => 3
  3 (op) 3 => 6
  6 (op) 4 => last value, 10
  List(1,2,3,4).foldLeft(0) ((x,y) => x + y)
   */
  val initialState = State(root, root)
  initialState.show
  io.Source.stdin.getLines().foldLeft(initialState)((currentState, newLine) => {
    val newState = Command.from(newLine).apply(currentState)
    newState.show
    newState
  })
//  var state = State(root, root) // whenever need a stateful program, we can only use var
//  val scanner = new Scanner(System.in)
//
//  while (true) {
//    state.show
//    val input = scanner.nextLine()
//    state = Command.from(input).apply(state)
//  }
}
