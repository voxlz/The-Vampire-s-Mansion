
package main.scala

object Start {
  def main(args: Array[String]) = {
    println("Welcome to 'The Vampire's mansion'!")
    println("Do you want to start the game? (y/n)")
    
    if (usrYesOrNo) start()
  }
  
  def usrYesOrNo(): Boolean = {
    while (true) {
      print("You: ")
      var input = scala.io.StdIn.readLine()
    
      if (input == "y") {
        return true
      } else if (input == "n") {
        return false
      }
      else {
        println("Please write 'y' or 'n' to for yes or no.")
      }
    }
    true
  }
  
  def start() {
    val bedroom = 
        Room("It's a dark bedroom. This was where I woke up.", 
        Array("coffin", "lamp", "desk"), 
        Map(('e', "Hallway")))
    
    println("Game started")
    bedroom.enter
  }
}