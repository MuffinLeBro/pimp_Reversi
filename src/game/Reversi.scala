package game

import classes.Game

object Reversi extends App{

  var game: Game = new Game()
  game.askPlayerName()
  game.init_game()
}
