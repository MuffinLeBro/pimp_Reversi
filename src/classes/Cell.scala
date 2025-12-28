package classes

import java.awt.{Color, Point}

class Cell() {
  private var _pion: Pion = new Pion()
  private var _center: Point = _

  def this(pion: Pion, center: Point) = {
    this()
    this.pion = pion
    this.center = center
  }

  def this(pion: Pion) = {
    this()
    this.pion = pion
    this.center = new Point()
  }

  def pion: Pion = _pion
  def pion_=(value: Pion): Unit = {
    _pion = value
  }

  def center: Point = _center
  def center_=(value: Point): Unit = {
    _center = value
  }

}
