package game

import classes.Game

object Reversi extends App{

  var game: Game = new Game()
  game.askPlayerName()
  game.init_game()
  game.current_player = game.players(0) // set the first player who plays

  do{
     if(game.current_player.can_play(game.board)){
        println("CAN PLAY")
     }
    else{
      game.switch_player()
    }
  }while(!game.isOver)
}

