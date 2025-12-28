package classes

import `trait`.Config
import hevs.graphics.FunGraphics
import hevs.utils.TextTools
import utils.{Dialogs, Shape}

import java.awt.{Color, Font}
import javax.swing.{JFrame, SwingConstants}

class Game extends Config{

  val GRAPHICS_WIDTH: Int = 1300
  val GRAPHICS_HEIGHT: Int = 1000
  val display: FunGraphics = new FunGraphics(GRAPHICS_WIDTH, GRAPHICS_HEIGHT)
  var player1: Player = new Player()
  var player2: Player = new Player()
  var current_player: Player = new Player()

  var board: Board = new Board(8, 8)


  def askPlayerName(): Unit = {
    val name1: String = Dialogs.getString("PLAYER 1")
    val name2: String = Dialogs.getString("PLAYER 2")

    this.player1 = new Player(name1, Color.BLACK)
    this.player2 = new Player(name2, Color.WHITE)

  }

  def displayBoard(): Unit = {
    display.setColor(BACKGROUND)
    display.drawFillRect(0, 0, GRAPHICS_WIDTH, GRAPHICS_HEIGHT)
    display.setColor(GREEN)
    display.drawFillRect(MARGIN, MARGIN, 8 * CELL_DIMENSION, 8 * CELL_DIMENSION)
    display.setColor(LINE_COLOR)

    for(y <- MARGIN  to this.board.BOARD_HEIGHT  by 100){
      display.drawLine(MARGIN, y, this.board.BOARD_WIDTH, y)
      for(x <- MARGIN  to this.board.BOARD_WIDTH  by 100){
        display.drawLine(x, y, x, this.board.BOARD_HEIGHT)
      }
    }
  }

  def paintPion(): Unit = {
    for(i <- this.board.playBoard.indices){
      for(j <- this.board.playBoard(i).indices){
        Shape.drawDisc(display, this.board.playBoard(i)(j))
      }
    }
  }

  def displayText(): Unit = {
    display.setColor(Color.BLACK)
    display.drawString(this.board.BOARD_WIDTH + 100, MARGIN + 20, TextTools.capitalize(this.player1.name), "SansSerif", Font.PLAIN, 20)
//    display.drawString(this.board.BOARD_WIDTH + 100, MARGIN + 25, "Score", "SansSerif", Font.ITALIC, 20)
    display.drawLine(this.board.BOARD_WIDTH + 100, MARGIN + 60, 1200, MARGIN + 60)

    display.drawString(this.board.BOARD_WIDTH + 100, MARGIN + 120,TextTools.capitalize(this.player2.name), "SansSerif", Font.PLAIN, 20)
    display.drawLine(this.board.BOARD_WIDTH + 100, MARGIN + 160, 1200, MARGIN + 160)
  }

  def init_game(): Unit = {
    this.displayBoard()
    this.paintPion()
    this.displayText()
  }

  def isOver(): Boolean = {
    false
  }

  def count_possible_shots(): Int = {
    var count: Int = 0

    count
  }
}
