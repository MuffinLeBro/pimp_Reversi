package classes

import `trait`.Config

import java.awt.Color

class Pion extends Config {
  private var _color: Color = GREEN

  def this(c: Color) = {
    this()
    this.color = c
  }

  def color: Color = _color
  def color_= (value: Color): Unit = {
    _color = value
  }
}

