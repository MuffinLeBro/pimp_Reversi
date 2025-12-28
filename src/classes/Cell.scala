package classes

import java.awt.{Color, Point}

class Cell() {
  private var pion: Pion = new Pion()
  private var center: Point = _

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

  def set_pion(p: Pion): Unit = {
    this.pion = p
  }

  def get_pion(): Pion = {
    this.pion
  }

  def set_center(c: Point): Unit = {
    this.center = c
  }

  def get_center(): Point = {
    this.center
  }

}
