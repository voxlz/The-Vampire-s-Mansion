package main.scala

case class Event(promt: String, yesStr: String, noStr: String, func: () => Unit, correct: Boolean) {
  
  def start {
    if (Start.usrYesOrNo(promt)) {
      println(yesStr)
      if (correct != true) func()
    } else {
      println(noStr)
      if (correct != false) func()
    }
  }
}
