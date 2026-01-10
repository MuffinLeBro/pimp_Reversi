package listener

import `trait`.Config
import classes.{Game, Player}
import hevs.graphics.FunGraphics

import java.awt.event.{MouseAdapter, MouseEvent, MouseListener}

class BoardListener(game: Game) extends MouseAdapter with Config{

  override def mouseClicked(e: MouseEvent): Unit = {

    if(game.isStarted){
      if(!game.click_enable) return
      else{
        val event = e
        // Get the mouse position from the event
        val posx = event.getX
        val posy = event.getY
        if(posx >= MARGIN && posx <= game.board.BOARD_WIDTH && posy >= MARGIN && posy <= game.board.BOARD_HEIGHT){
          for(i <- game.board.playBoard.indices){
            for(j <- game.board.playBoard(i).indices){
              if(game.board.playBoard(i)(j).pion.color == GREEN && (Math.abs(posx - game.board.playBoard(i)(j).center.getX) <= CELL_DIMENSION / 2) && (Math.abs(posy - game.board.playBoard(i)(j).center.getY) <= CELL_DIMENSION / 2)){
                if(game.board.isValidMove(game.current_player, i, j)){
                  game.board.applyMove(game.display, game.current_player, i, j)
                  game.number_of_switch = 0

                  game.disableClick()
                  game.updateScore()
                  game.switch_player()
                  game.startTurn()

                  // Manage the sound of good move
                  game.audio_good.play()
                  Thread.sleep(1000)
                  if(game.audio_good.audioClip.isRunning) game.audio_good.stop()
                  game.updateTurn(game.players.indexOf(game.current_player))
                }
                else{
                  game.startTurn()
                }
              }
            }
          }
        }
        else{
          if(posx >= CLICK_REPLAY_RESTART_REPLAY._1 && posx <= (CLICK_REPLAY_RESTART_REPLAY._1 + 150) && posy >= CLICK_REPLAY_RESTART_REPLAY._2 && posy <= (CLICK_REPLAY_RESTART_REPLAY._2 + 70)) game.restart()
          if(posx >= CLICK_QUIT._1 && posx <= (CLICK_QUIT._1 + 150) && posy >= CLICK_QUIT._2 && posy <= (CLICK_QUIT._2 + 70)) System.exit(0)
        }
      }
    }
    else{
      val event = e
      val posx = event.getX
      val posy = event.getY

      if(posx >= POSITION_BUTTON_PLAY._1 - 150 && posx <= (POSITION_BUTTON_PLAY._1 - 150 + 300) && posy >= POSITION_BUTTON_PLAY._2 - 50 && posy <= (POSITION_BUTTON_PLAY._2 - 50 + 100)){
        game.isStarted = true
        game.start()
        game.audio_intro.stop()
      }
    }
    game.display.syncGameLogic(60)
  }
}
