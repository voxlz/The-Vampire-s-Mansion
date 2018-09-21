

package main.scala

import scala.collection.mutable.ListBuffer

case class Item(
    name: String, 
    desc: String = "Nothing of interest here.", 
    obtainable: Boolean = false, 
    unlocksItem: Item = null,
    event: Event = null) {
  
  var neverPickedUp = true
  var alreadyUnlocked = false
  
  def Interact() = {    
    if (obtainable && neverPickedUp) {
      if (Start.usrYesOrNo(s"Do you want to pick up $name?")) {
        Start.inventory.insert(0, this)
        println(s"$name added to your inventory.")
        Start.currentRoom.items.remove(Start.currentRoom.items.indexWhere(item => item.name == name))
        neverPickedUp = false
      } else {
        println(s"You put back $name")
      }
    }
    
    else if (unlocksItem != null && !alreadyUnlocked)  {
      Start.currentRoom.items.insert(0, unlocksItem)
      val itemName = unlocksItem.name
      println(s"While searching the $name, you found a $itemName!")
      alreadyUnlocked = true
    }
    
    else if (event != null) {
      event.start
    }
    
    else {
      println(s"You fimble with the $name but find nothing of interest") 
    }
  }
}