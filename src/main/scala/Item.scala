

package main.scala

import scala.collection.mutable.ListBuffer

case class Item(name: String, obtainable: Boolean = false, unlocksItem: Item = null, desc: String = "Nothing of interest here.", event: Tuple3(String, String, Item) = Tuple3.nill) {
  
  // Event expects 
  
  def Interact() = {    
    if (obtainable) {
      if (Start.usrYesOrNo(s"Do you want to pick up $name?")) {
        Start.inventory.insert(0, this)
        println(s"$name added to your inventory.")
      } else {
        println(s"You put back $name")
      }
    }
    
    else if (unlocksItem != null)  {
      Start.currentRoom.items.insert(0, unlocksItem)
      val itemName = unlocksItem.name
      println(s"While searching the $name, you found a $itemName!")
    }
    
    else {
      println(s"You fimble with the $name but find nothing of interest") 
    }
  }
}