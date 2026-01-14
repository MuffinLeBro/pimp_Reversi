package game

import classes.{Audio, Game}
import listener.BoardListener

import java.awt.event.{MouseAdapter, MouseEvent}

object Reversi extends App{
  var game: Game = new Game()
  game.loadGame()
}

