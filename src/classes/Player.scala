package classes

import java.awt.Color

class Player() {
  private var color : Color = _
  private var playing : Boolean = false  //wich player plays player 1 = true, player 2 = false
  var name: String = _
  private var score: Int = 0

  def this(name: String, c: Color) = {
    this()
    this.color = c
    this.name = name
    this.playing = false
  }

  def set_score(s: Int) = {
    this.score = s
  }

  def get_score(): Int = {
    this.score
  }
}
