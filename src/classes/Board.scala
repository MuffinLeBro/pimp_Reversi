package classes

import `trait`.Config
import animation.DiscAnimation
import hevs.graphics.FunGraphics
import utils.Shape

import java.awt
import java.awt.{Color, Point}
import scala.collection.mutable.ListBuffer
import scala.util.control.Breaks.{break, breakable}

class Board extends Config{
  private var _playBoard: Array[Array[Cell]] = _  // Main board
  private var _BOARD_WIDTH: Int = 0
  private var _BOARD_HEIGHT: Int = 0
  private val directions: Array[(Int, Int)] = Array(   // all directions to check for possible moves
    (-1,-1), (0,-1), (1,-1),
    (-1,0),         (1,0),
    (-1,1), ( 0,1), ( 1,1)
  )

  private val audio_many_flip: Audio = new Audio("/sounds/many_flip.wav")
  private val audio_good: Audio = new Audio("/sounds/good.wav")

  def playBoard: Array[Array[Cell]] = _playBoard
  def playBoard_=(value: Array[Array[Cell]]): Unit = {
    _playBoard = value
  }

  def BOARD_HEIGHT: Int = _BOARD_HEIGHT
  def BOARD_HEIGHT_=(value: Int): Unit = {
    _BOARD_HEIGHT = value
  }

  def BOARD_WIDTH: Int = _BOARD_WIDTH
  def BOARD_WIDTH_=(value: Int): Unit = {
    _BOARD_WIDTH = value
  }

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
    this.playBoard(playBoard.length / 2)(playBoard.length / 2 - 1) = new Cell(new Pion(BLACK)) // Pion haut à droite
    this.playBoard(playBoard.length / 2  - 1)(playBoard.length / 2) = new Cell(new Pion(BLACK)) // Pion bas à gauche
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
  def isValidMove(player: Player, i: Int, j: Int): Boolean = {
    var discovered_pawn: Boolean = false

    for((di, dj) <- this.directions){
      var index_i: Int = i + di
      var index_j: Int = j + dj

      if((index_i >= 0 && index_j >= 0) && (index_i < this.playBoard.length && index_j < this.playBoard(index_i).length)){
        if(this.playBoard(index_i)(index_j).pion.color != player.color && this.playBoard(index_i)(index_j).pion.color  != GREEN){

          breakable{
            while((index_i >= 0 && index_j >= 0) && (index_i < this.playBoard.length && index_j < this.playBoard(index_i).length)){
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

  def paintPion(display: FunGraphics): Unit = {
    for(i <- this.playBoard.indices){
      for(j <- this.playBoard(i).indices){
        Shape.drawDisc(display, this.playBoard(i)(j))
      }
    }
  }

  def paintPionAt(display: FunGraphics, color: Color, i: Int, j: Int): Unit = {
    display.setColor(color)
    Shape.drawDisc(display, this.playBoard(i)(j), color)
  }

  def countEmptyCell(): Int = {
    var res: Int = 0
    for(i <- this.playBoard.indices){
      res += this.playBoard(i).toList.count(_.pion.color == GREEN)
    }
    res
  }

  /**
   * Change all cell pawns to a new color after a move and return the number of flip
   * @param display, the FunGraphics
   * @param player, the current player
   * @param i, the index of line in the playboard
   * @param j, the index of column in the playboard
   */
  def applyMove(game: Game, display: FunGraphics, player: Player, i: Int, j: Int): Int = {
    val cells_to_modify: ListBuffer[(Int, Int)] = new ListBuffer[(Int, Int)]
    cells_to_modify.prepend((i, j))

    for((di, dj) <- this.directions){
      var index_i: Int = i + di
      var index_j: Int = j + dj

      if((index_i >= 0 && index_j >= 0) && (index_i < this.playBoard.length && index_j < this.playBoard(index_i).length)){
        if(this.playBoard(index_i)(index_j).pion.color != player.color && this.playBoard(index_i)(index_j).pion.color  != GREEN){

          breakable{
            var temp_cells: ListBuffer[(Int, Int)] = new ListBuffer[(Int, Int)]
            while((index_i >= 0 && index_j >= 0) && (index_i < this.playBoard.length && index_j < this.playBoard(index_i).length)){
              if(this.playBoard(index_i)(index_j).pion.color == player.color ){
                cells_to_modify ++= temp_cells
                break()
              }
              if(this.playBoard(index_i)(index_j).pion.color == GREEN ){
                temp_cells.clear()
                break()
              }
              temp_cells.prepend((index_i, index_j))
              index_i = index_i + di
              index_j = index_j + dj
            }
          }
        }
      }
    }

    for((i, j) <- cells_to_modify.toArray){
      this.playBoard(i)(j).pion.color = player.color
    }
    val anim = new DiscAnimation(game, display, this.playBoard, player.color, cells_to_modify.toArray)
    anim.start()
    cells_to_modify.size // number of flip
  }

  def showPlayableMoves(display: FunGraphics, player: Player): Unit = {
    for(i <- this.playBoard.indices; j <- this.playBoard(i).indices) {
      if(this.playBoard(i)(j).pion.color == GREEN){
        Shape.drawDisc(display, this.playBoard(i)(j), GREEN)
        if(isValidMove(player, i, j)) {
          Shape.drawDisc(display, this.playBoard(i)(j), YELLOW)
        }
      }
    }
  }


}
