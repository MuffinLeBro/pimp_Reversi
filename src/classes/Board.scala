package classes

import `trait`.Config

import java.awt.{Color, Point}
import scala.util.control.Breaks.{break, breakable}

class Board extends Config{
  var playBoard : Array[Array[Cell]] = _ // Main board
  var BOARD_WIDTH: Int = 0
  var BOARD_HEIGHT: Int = 0

  def this(line: Int, col: Int) = {
    this()
    var row: Int = 0
    var column: Int = 0
    this.playBoard = Array.ofDim(line, col)
    this.BOARD_WIDTH = line * CELL_DIMENSION + MARGIN  // if the col is 8, the width will be 850
    this.BOARD_HEIGHT = col *  CELL_DIMENSION + MARGIN  //   if the line is 8, the width will be 850

    for(i <- playBoard.indices){
      for(j <- playBoard(i).indices){
          this.playBoard(i)(j) = new Cell(new Pion())
      }
    }

    this.playBoard(playBoard.length / 2  - 1)(playBoard.length / 2 - 1) = new Cell(new Pion(Color.WHITE)) // Pion haut à gauche
    this.playBoard(playBoard.length / 2)(playBoard.length / 2 - 1) = new Cell(new Pion(Color.BLACK)) // Pion haut à droite
    this.playBoard(playBoard.length / 2  - 1)(playBoard.length / 2) = new Cell(new Pion(Color.BLACK)) // Pion bas à gauche
    this.playBoard(playBoard.length / 2)(playBoard.length / 2) = new Cell(new Pion(Color.WHITE)) // Pion bas à droite

    for(y <- MARGIN + RADIUS until BOARD_HEIGHT by RADIUS * 2){
      column = 0
      for(x <- MARGIN + RADIUS until BOARD_WIDTH by RADIUS * 2){
        this.playBoard(row)(column).center = new Point(x, y)  // set the center of each Pion
        column += 1
      }
      row += 1
    }
  }

  /**
   * Check if the current player has a valid move
   * otherwise, we will pass on his turn
   * @param player, the player who plays
   * @return a boolean if the current player can play
   */
  def hasValidMove(player: Player): Boolean = {
    for(i <- this.playBoard.indices){
      for(j <- this.playBoard(i).indices){
        if(this.playBoard(i)(j).pion.color == GREEN){  // if the cell is not yet coloured
          if(this.isValidMove(player, i, j)) return true
        }
      }
    }
    false
  }

  /**
   *
   * @param player, who plays his turn
   * @param i, the index of line in the playboard
   * @param j,  the index of column in the playboard
   * @return a boolean if the player has AT LEAST one move on his turn
   */
  private def isValidMove(player: Player, i: Int, j: Int): Boolean = {
    var discovered_pawn: Boolean = false
    // all directions to check for possible moves
    val directions: Array[(Int, Int)] = Array(
    (-1,-1), (0,-1), (1,-1),
    (-1,0),         (1,0),
    (-1,1), ( 0,1), ( 1,1)
    )

    for((di, dj) <- directions){
      var index_i: Int = i + di
      var index_j: Int = j + dj

      if((index_i >= 0 && index_j >= 0) && (index_i < this.playBoard.length && index_j < this.playBoard(index_i).length)){
        if(this.playBoard(index_i)(index_j).pion.color != player.color && this.playBoard(index_i)(index_j).pion.color  != GREEN){

          breakable{
            while(index_i < this.playBoard.length && index_j < this.playBoard(index_i).length){
              if(this.playBoard(index_i)(index_j).pion.color == player.color ) discovered_pawn = true
              if(this.playBoard(index_i)(index_j).pion.color == GREEN) break()
              if(discovered_pawn) return discovered_pawn
              index_i = index_i + di
              index_j = index_j + dj
            }
          }
        }
      }
    }
    false
  }

}
