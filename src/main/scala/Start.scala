
package main.scala

import scala.collection.mutable.ListBuffer
import main.scala.Dir._

object Start {
  
  // VARIABLES
  var gameRunning = true
  
  // EVENTS
  val coffin = Event(
      promt = "Something is sleeping inside the coffin! Do you want to wake it up?",
      yesStr = "You pull the lid off with a loud crash.", 
      noStr = "You slowly move away. 'I think it's better to be quiet'", 
      func = gameOver,
      correct = false)
  
  // ROOMS
  val error = Room("error", "error", "error")
  
  val freedom = Room(
    name = "freedom", 
    desc = "hey, you won"
  )
  
  val hallway = Room(
      name = "hallway",
      entry = "You enter a long hallway with pantings along the wall. They all seem to look straight at you.",
      desc = "A long hallway with pantings along the wall. Rain is hitting the hallway windows.",
      doors = ListBuffer(
        Door(Dir.WEST, "bedroom"),
        Door(Dir.EAST, "dinning room"),
        Door(Dir.DOWN, "entrance", doorType = "stairs")
      ),
      items = ListBuffer(
          Item("painting", "It depicts a eerie family with fangs.", unlocksItem = Item("endim", "Look like a mathbook. You hate it.", true)),
          Item("window", "Looks like this house is placed on a hill outside the city. Too high to jump."),
          Item("rug", "A long red rug.", unlocksItem = Item("dust", "It's dust. What did you expect", unlocksItem= Item("big key", "How the fuck did that work.", true)))
          )
      )
      
  val entrance = Room(
      name = "entrance",
      entry = "Is that..? It's the exit! There is the main exit door!",
      desc = "It's a big entry room with banners hanging from the roof.",
      doors = ListBuffer(
          Door(Dir.UP, "hallway", doorType = "stairs"),
          Door(Dir.NORTH, "freedom", "big key")
        )
      )
      
  val dinning_room = Room(
      name = "dinning room",
      desc = "This room has a big long table in it, but only one chair on each end.",
      items = ListBuffer(
          Item("wine glass", "It contains a red fluid. Hmm."),
          Item("gun", "It's a clock, just laying next to a plate", event = Event("Shoot yourself?", "You shoot your foot. The bang was so loud the vampire woke up!", "'I'm sure it's not loaded', you think", gameOver, false))
        ),
      doors = ListBuffer(
          Door(Dir.WEST, "hallway")
        )
      )

  val bedroom = Room(
      name = "bedroom",
      entry="'My head hurts' you mutter, while slowly waking up. The dark room looks like a bedroom, except the bed is missing?\nMabye you should 'look' around. (type 'help' for all commands)",
      desc="It's a dark bedroom. This was where I woke up.", 
      items = ListBuffer(
        Item("coffin", "It looks like a big black coffin. The lid is slightly open.",
            event = coffin), 
        Item("lamp", "It's an older lamp. The lightbulb seems to be missing."), 
        Item("desk", "It's a dusty desk, with multiple drawers. There might be something 'use'ful inside.", 
            unlocksItem = Item("key", "It's a small golden key. You could 'use' this to open some door.", true))), 
      doors = ListBuffer(
        Door(Dir.EAST, "hallway", "key")))
        
  
  // Make list of rooms
  val rooms: ListBuffer[Room] = ListBuffer(hallway, bedroom, dinning_room, entrance, freedom, error)      
  
  var inventory: ListBuffer[Item] = ListBuffer.empty
  var currentRoom: Room = bedroom
  
  def main(args: Array[String]) = {
    println("Welcome to...")
    println(
      """
      
 __ __|  |                                               _)             )      
    |    __ \    _ \     \ \   /  _` |  __ `__ \   __ \   |   __|  _ \ /   __| 
    |    | | |   __/      \ \ /  (   |  |   |   |  |   |  |  |     __/   \__ \ 
   _|   _| |_| \___|       \_/  \)_,_| _|  _|  _|  .__/  _| _|   \___|   ____/ 
  __ `__ \    _` |  __ \    __|  |   _ \   __ \   _|                           
  |   |   |  (   |  |   | \__ \  |  (   |  |   |                               
 _|  _|  _| \__,_| _|  _| ____/ _| \___/  _|  _|                               
                                                                               

      """)
    
    if (usrYesOrNo("Do you want to start the game?")) start()
  }
  
	def usrYesOrNo(question: String): Boolean = {
    println(question + " (y/n)")
    print("You: ")
    scala.io.StdIn.readBoolean()
  }
  
  def userPromt() = {
    print("You: ")
    var input: String = scala.io.StdIn.readLine()
    input = input.toLowerCase()
    
    if (input.contains("help")) {
      println("Availabe commands: ")
      println("north, west, east, south, up, down, interact/use, look, inventory, room")
    }
    
    else if (input.contains("interact") || input.contains("use")) { 
      var itemFound = false
      for (item <- currentRoom.items) {
        if (input.contains(item.name)) {
          item.Interact()
          itemFound = true
        }
      }
      if (!itemFound) {
        println("Eh, no. I can't do that.")
      }
    }
    
    else if (input.contains("look")) {
      var itemFound = false
      for (item <- currentRoom.items) {
        if (input.contains(item.name)) {
          println(item.desc)
          itemFound = true
        }
      }
      if (!itemFound) {
        println("There is no such thing in this room.")
      }
    }
    
    else if (input.contains("inventory")) {
      if(inventory.length == 0) {
        println("My pockets are empty")
      } else {
        print("My pockets contain: ")

        for (item <- inventory) {
          print(item.name + ". ")
        }
        
        print("\n")
      }
    }
    
    else if (input.contains("room")) {
      currentRoom.enter
    }
    
    else if (input.contains("east")) {
      move(Dir.EAST)
    }
    
    else if (input.contains("west")) {
      move(Dir.WEST)
    }
        
    else if (input.contains("south")) {
      move(Dir.SOUTH)
    }    
    
    else if (input.contains("north")) {
      move(Dir.NORTH)
    }
    
    else if (input.contains("down")) {
      move(Dir.DOWN)
    }
    
    else if (input.contains("up")) {
      move(Dir.UP)
    }
    
    else {
      println("I don't know how to do that")
    }
  }
  
  def start() {
    println("\n----------------------Game Start--------------------\n")
    bedroom.enter
    while (gameRunning) {
      userPromt
    }
  }
  
  
  def findRoom(input: String): Room = {
    rooms.find(room => room.name == input) getOrElse(error)
  }
  
  def move(direction: Dir.Value) = {
    var door = currentRoom.doors.find(door => door.dir == direction) getOrElse(null)
      
      if (door != null) {        
        if (door.key == "") {
          
          currentRoom = findRoom(door.leads)
          currentRoom.enter
        }
        else if (inventory.exists(item => item.name == door.key)) {
          inventory.remove(inventory.indexWhere(item => item.name == door.key))
          println("You used the key to unlock the door.")
          currentRoom = findRoom(door.leads)
          door.key = ""
          currentRoom.enter
        }
        else {
          println("Door is locked. Might need a key or some other item.")
        }
      } else {
        println(s"There is no door to the $direction")
      }
  }
  
  def gameOver(): Unit = {
    gameRunning = false
    println("The vampire attacked you before you could react. You died.")
    println("\n----------------------Game Over -------------------------\n")
  }
  
  ///god damn
  def asciiMansion = {
    
    println("The large door squeaks open. You escape into the night, with one final look behind you.\n-------------------YOU WON------------------\n")
    gameRunning = false
    
    """               *         .              *            _.---._      
                             ___   .            ___.'       '.   *
        .              _____[LLL]______________[LLL]_____     \
                      /     [LLL]              [LLL]     \     |
                     /____________________________________\    |    .
                      )==================================(    /
     .      *         '|I .-. I .-. I .--. I .-. I .-. I|'  .'
                  *    |I |+| I |+| I |. | I |+| I |+| I|-'`       *
                       |I_|+|_I_|+|_I_|__|_I_|+|_I_|+|_I|      .
              .       /_I_____I_____I______I_____I_____I_\
                       )================================(   *
       *         _     |I .-. I .-. I .--. I .-. I .-. I|          *
                |u|  __|I |+| I |+| I |<>| I |+| I |+| I|    _         .
           __   |u|_|uu|I |+| I |+| I |~ | I |+| I |+| I| _ |U|     _
       .  |uu|__|u|u|u,|I_|+|_I_|+|_I_|__|_I_|+|_I_|+|_I||n|| |____|u|
          |uu|uu|_,.-' /I_____I_____I______I_____I_____I\`'-. |uu u|u|__
          |uu.-'`      #############(______)#############    `'-. u|u|uu|
         _.'`              ~"^"~   (________)   ~"^"^~           `'-.|uu|
      ,''          .'    _                             _ `'-.        `'-.
  ~"^"~    _,'~"^"~    _( )_                         _( )_   `'-.        ~"^"~
      _  .'            |___|                         |___|      ~"^"~     _
    _( )_              |_|_|          () ()          |_|_|              _( )_
    |___|/\/\/\/\/\/\/\|___|/\/\/\/\/\|| ||/\/\/\/\/\|___|/\/\/\/\/\/\/\|___|
    |_|_|\/\/\/\/\/\/\/|_|_|\/\/\/\/\/|| ||\/\/\/\/\/|_|_|\/\/\/\/\/\/\/|_|_|
    |___|/\/\/\/\/\/\/\|___|/\/\/\/\/\|| ||/\/\/\/\/\|___|/\/\/\/\/\/\/\|___|
    |_|_|\/\/\/\/\/\/\/|_|_|\/\/\/\/\/[===]\/\/\/\/\/|_|_|\/\/\/\/\/\/\/|_|_|
    |___|/\/\/\/\/\/\/\|___|/\/\/\/\/\|| ||/\/\/\/\/\|___|/\/\/\/\/\/\/\|___|
    |_|_|\/\/\/\/\/\/\/|_|_|\/\/\/\/\/|| ||\/\/\/\/\/|_|_|\/\/\/\/\/\/\/|_|_|
    |___|/\/\/\/\/\/\/\|___|/\/\/\/\/\|| ||/\/\/\/\/\|___|/\/\/\/\/\/\/\|___|
~""~|_|_|\/\/\/\/\/\/\/|_|_|\/\/\/\/\/|| ||\/\/\/\/\/|_|_|\/\/\/\/\/\/\/|_l_|~""~
   [_____]            [_____]                       [_____]            [_____]
   ---------------------------------------------------------------------------"""
  }
}