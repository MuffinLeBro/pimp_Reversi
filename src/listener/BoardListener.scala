package listener

import `trait`.Config
import classes.Player
import hevs.graphics.FunGraphics

import java.awt.event.{MouseAdapter, MouseEvent}

class BoardListener(display: FunGraphics) extends Config{

  def applyMove(player: Player, i: Int, j: Int): Unit = {
    // This will handle the mouse
    display.addMouseListener(new MouseAdapter() {
      override def mouseClicked(e: MouseEvent): Unit = {
        val event = e

        // Get the mouse position from the event
        val posx = event.getX
        val posy = event.getY

        println(s"Mouse position $posx - $posy")

        // Draws a circle where the mouse was during click
        display.drawFilledCircle(posx, posy, 5)
      }
    })
  }
}
