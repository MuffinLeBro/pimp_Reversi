package listener

import `trait`.Config
import classes.Game

import java.awt.event.{MouseEvent, MouseMotionAdapter}

class BoardMotionListener (game: Game) extends MouseMotionAdapter with Config{
  override def mouseMoved(e: MouseEvent): Unit = {
    val event = e

    // Get the mouse position from the event
    val posx = event.getX
    val posy = event.getY

    // Draws a circle where the mouse was during click
//    game.display.drawFilledCircle(posx, posy, 5)
  }
}
