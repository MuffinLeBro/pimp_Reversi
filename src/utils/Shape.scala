package utils

import `trait`.Config
import classes.Cell
import hevs.graphics.FunGraphics

import java.awt.Color

/**
 * Class using the FunGraphics class with a function
 * to draw different shapes.
 */
object Shape extends Config{
  /**
   *
   * @param display, the FunGraphics
   * @param cell which contains the circle (Pion)
   */
  def drawDisc(display: FunGraphics, cell: Cell) : Unit = {
    for (x <- (cell.center.x - RADIUS - PADDING) to  (cell.center.x + RADIUS - PADDING)) {
      for (y <- (cell.center.y - RADIUS - PADDING) to  (cell.center.y + RADIUS - PADDING)) {
        val distance = (cell.center.x - x) * (cell.center.x - x) + (cell.center.y - y) * (cell.center.y - y)
        if (distance <= (RADIUS - PADDING) * (RADIUS - PADDING)) display.setPixel(x, y, cell.pion.color)
      }
    }
  }
}
