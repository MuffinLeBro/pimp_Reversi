package utils

import `trait`.Config
import classes.{Cell, Game}
import hevs.graphics.FunGraphics

import java.awt.Color
import javax.swing.Timer

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
    display.setColor(cell.pion.color)
    display.drawFilledOval(cell.center.x - RADIUS + PADDING , cell.center.y - RADIUS + PADDING, (RADIUS-PADDING) * 2, (RADIUS-PADDING) * 2)
  }


  /**
   *
   * @param display, the FunGraphics
   * @param cell which contains the circle (Pion)
   * @param color of the circle
   */
  def drawDisc(
                display: FunGraphics,
                cell: Cell,
                color: Color) : Unit = {
    display.frontBuffer.synchronized{
      display.setColor(color)
      display.drawFilledOval(cell.center.x - RADIUS + PADDING, cell.center.y - RADIUS + PADDING, (RADIUS - PADDING) * 2, (RADIUS - PADDING) * 2)
    }
    // FPS sync
    display.syncGameLogic(60)
  }

  /**
   *
   * @param display, the FunGraphics
   * @param cell which contains the circle (Pion)
   * @param color of the circle
   */
  def drawDisc(
                display: FunGraphics,
                cell: Cell, radius: Int,
                color: Color) : Unit = {

    display.frontBuffer.synchronized {
      display.setColor(color)
      val x = cell.center.x - radius
      val y = cell.center.y - radius
      val d = radius * 2
      display.drawFilledOval(x, y, d, d)
    }
  }

  def drawFlipDisc(
                    game: Game,
                    display: FunGraphics,
                    cell: Cell,
                    halfWidth: Int,
                    radius: Int,
                    color: Color
                  ): Unit = {
      display.frontBuffer.synchronized {
        display.setColor(color)

        val centerX = cell.center.x
        val centerY = cell.center.y

        val x = centerX - halfWidth
        val y = centerY - radius

        val width = halfWidth * 2
        val height = radius * 2

        display.drawFilledOval(x, y, width, height)
      }
  }

}
