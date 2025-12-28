package classes

import java.awt.{Color, Point}

class Pion() {
  private var color: Color = Color.GREEN

  def this(c: Color) = {
    this()
    this.color = c
  }

  def set_color(c: Color): Unit = {
    this.color = c
  }

  def get_color(): Color = {
    this.color
  }
}

