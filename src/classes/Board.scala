package classes

import `trait`.Config

import java.awt.{Color, Point}

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

}
