package listener

import `trait`.Config
import classes.{Game, Player}
import hevs.graphics.FunGraphics

import java.awt.event.{MouseAdapter, MouseEvent, MouseListener}

class BoardListener(game: Game) extends MouseAdapter with Config{
  override def mouseClicked(e: MouseEvent): Unit = {

      if(game.current_player.can_play(game.board)){ // if the current player can play
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

                  // Manage the sound of good move
                  game.audio_good.play()
                  Thread.sleep(1000)
                  if(game.audio_good.audioClip.isRunning) game.audio_good.stop()

                  game.number_of_switch = 0
                  game.updateScore()
                  game.switch_player()
                }
                else{
                  // Manage the sound of bad move
                  game.audio_mistake.play()
                  Thread.sleep(1000)
                  if(game.audio_mistake.audioClip.isRunning) game.audio_mistake.stop()
                }
              }
            }
          }
        }
      }
      else{ // if not, we switch to the next player
        game.switch_player()
        game.number_of_switch += 1
      }
      game.display.syncGameLogic(60)
  }
}
