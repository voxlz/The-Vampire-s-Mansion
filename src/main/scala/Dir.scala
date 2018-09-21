package main.scala

object Dir extends Enumeration {
    type Dir = Value
    val WEST, EAST, NORTH, SOUTH, UP, DOWN = Value
}