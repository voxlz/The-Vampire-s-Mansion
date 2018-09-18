

package main.scala

import scala.collection.mutable.ListBuffer

case class Room(desc: String, items: ListBuffer[Item] = ListBuffer.empty, rooms: Map[Char, String] = Map.empty) {
  
  def enter {
    println(s"$desc $printObjs $printRooms")
  }
  
  def printObjs: String = {

    var str = ""
    if (items.length != 0) {
       str = "There is a "

      for (i <- (0 until items.length).reverse) {
  
        val item = items(i).name.capitalize.toString()
        str = str + item
        
        if (i == 1) str = str + " and a "
        if (i > 1) str = str + ", a "
      }
      
      str = str + (" in this room.")
    } else {
      str = "This room seems to be empty."
    }
    str
  }
  
  def printRooms: String = {
    var str = ""
    
    if (rooms.count(p => true) > 1) {
      str = "There are doors to the "
    } else {
      str = "There is a door to the "
    }
    
    for (i <- rooms.keys) {
      i match {
          case 'n'  => str = str + ("north")
          case 's'  => str = str + ("south")
          case 'w'  => str = str + ("west")
          case 'e'  => str = str + ("east")
          case 'u'  => str = str + ("up")
          case 'd'  => str = str + ("down")
          // catch the default with a variable so you can print it
          case whoa  => println("Unexpected case: " + whoa.toString)
      }
    }
    
    str + "."
  }
}

