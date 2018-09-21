

package main.scala

import scala.collection.mutable.ListBuffer

case class Room
(
  name: String,
  entry: String = "", 
  desc: String, 
  items: ListBuffer[Item] = ListBuffer.empty, 
  var doors: ListBuffer[Door] = ListBuffer.empty
)
{
  var beenHereBefore = false
  
  def enter {
    
    if (name == "freedom") {
      println(Start.asciiMansion)
    }
    
    else if (beenHereBefore || entry == "") {
      println(s"$desc\n$listItems $printRooms")
    } else {
      println(s"$entry\n$listItems $printRooms")
      beenHereBefore = true
    }
  }
  
  def listItems: String = {

    var str = ""
    if (items.length != 0) {
       str = "There is a "

      for (i <- (0 until items.length).reverse) {
  
        val item = items(i).name.capitalize
        str = str + item
        
        if (i == 1) str += " and a "
        if (i > 1) str += ", a "
      }
      
      str = str + (" in this room.")
    } else {
      str = "This room seems to be empty."
    }
    str
  }
  
  def printRooms: String = {
    var str = ""
    
    for (door <- doors) {
      val t = door.doorType
      val dir = door.dir

      if (door.doorType == "door") {
         str += s"There is a $t to the $dir. "
      } else {
         str += s"There are $t leading $dir. "
      }
    }
    str
  }
}

