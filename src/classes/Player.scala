package classes

import java.awt.Color

class Player() {
  private var _color: Color = _
  private var _name: String = _
  private var _score: Int = 0

  def this(name: String, c: Color) = {
    this()
    this.color = c
    this.name = name
  }

  def name: String = _name // getter name
  def name_=(value: String): Unit = { // setter name
    _name = value
  }

  def color: Color = _color // getter color
  def color_=(value: Color): Unit = { // setter color
    _color = value
  }

  def score: Int = _score // getter score
  def score_=(value: Int): Unit = { // setter score
    _score = value
  }

  def can_play(board: Board): Boolean = {
    board.hasValidMove(this)
  }

}
