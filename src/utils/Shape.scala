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
    for (x <- (cell.get_center().x - RADIUS - PADDING) to  (cell.get_center().x + RADIUS - PADDING)) {
      for (y <- (cell.get_center().y - RADIUS - PADDING) to  (cell.get_center().y + RADIUS - PADDING)) {
        val distance = (cell.get_center().x - x) * (cell.get_center().x - x) + (cell.get_center().y - y) * (cell.get_center().y - y)
        if (distance <= (RADIUS - PADDING) * (RADIUS - PADDING)) display.setPixel(x, y, cell.get_pion().get_color())
      }
    }
  }
}
