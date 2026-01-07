package game

import classes.{Audio, Game}
import listener.BoardListener

import java.awt.event.{MouseAdapter, MouseEvent}

object Reversi extends App{

  var game: Game = new Game()

  game.askPlayerName()
  game.init_game()
  game.current_player = game.players(0)

  do{ }while(!game.isOver)

  game.audio_tic_toc.stop()
  game.audio_winner.play()

}

