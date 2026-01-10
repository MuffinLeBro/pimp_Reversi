package listener

import `trait`.Config
import classes.Game

import java.awt.event.{MouseAdapter, MouseEvent}

class LoadListener (game: Game) extends MouseAdapter with Config{
  override def mouseClicked(e: MouseEvent): Unit = {
    if(!game.isStarted){
      val event = e
      val posx = event.getX
      val posy = event.getY

      if(posx >= POSITION_BUTTON_PLAY._1 - 150 && posx <= (POSITION_BUTTON_PLAY._1 - 150 + 300) && posy >= POSITION_BUTTON_PLAY._2 - 50 && posy <= (POSITION_BUTTON_PLAY._2 - 50 + 100)){
        game.isStarted = true
        game.start()
        game.audio_intro.stop()
      }
      game.display.syncGameLogic(60)
    }
  }
}
