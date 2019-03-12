package com.frankgt40.scala.commands

import com.frankgt40.scala.filesystem.State

trait Command {//extends (State => State) {
  def apply(state: State): State
}

object Command {
  val MKDIR: String = "mkdir"
  val LS: String = "ls"
  val PWD: String = "pwd"
  val TOUCH: String = "touch"
  val CD: String = "cd"
  val RM: String = "rm"
  val ECHO: String = "echo"
  val CAT = "cat"
  def emptyCommand: Command = new Command {
    override def apply(state: State): State = state
  }
  def incompleteCommand(name: String): Command = new Command {
    override def apply(state: State): State =
      state.setMessage(name + ": incomplete command!")
  }
  def from(input: String): Command = {
    val tokens: Array[String] = input.split(" ")
    if (input.isEmpty || tokens.isEmpty) emptyCommand
    else tokens(0) match {
      case MKDIR =>
        if (tokens.length < 2) incompleteCommand("mkdir")
        else new Mkdir(tokens(1))
      case LS =>
        new Ls
      case PWD =>
        new Pwd
      case TOUCH =>
        if (tokens.length < 2) incompleteCommand("touch")
        else new Touch(tokens(1))
      case CD =>
        if (tokens.length < 2) incompleteCommand("cd")
        else new Cd(tokens(1))
      case RM =>
        if (tokens.length < 2) incompleteCommand("rm")
        else new Rm(tokens(1))
      case ECHO =>
        if (tokens.length < 2) incompleteCommand("echo")
        else new Echo(tokens.tail)
      case CAT =>
        if (tokens.length < 2) incompleteCommand("echo")
        else new Cat(tokens(1))
      case _ =>
        new UnknownCommand
    }
  }
}