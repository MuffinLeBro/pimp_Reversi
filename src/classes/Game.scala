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
  var current_player: Player = player1

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
    this.count_point(this.player1)
    this.count_point(this.player2)
    val font_name = new Font("Arial", Font.PLAIN, 20)
    val font_score = new Font("Consolas", Font.PLAIN, 30)

    display.drawString(this.board.BOARD_WIDTH + 100, MARGIN + 60, this.player1.name.toUpperCase , font_name, null) // name
    display.drawString(this.board.BOARD_WIDTH + 230, MARGIN + 60, this.player1.score.toString , font_score, null) // score
    display.setColor(Color.BLACK)
    display.drawFilledCircle(this.board.BOARD_WIDTH + 60, MARGIN + 40, 25)
    display.drawLine(this.board.BOARD_WIDTH + 100, MARGIN + 70, 1200, MARGIN + 70)

    display.drawString(this.board.BOARD_WIDTH + 100, MARGIN + 160, this.player2.name.toUpperCase, font_name, null) // name
    display.drawString(this.board.BOARD_WIDTH + 230, MARGIN + 160, this.player2.score.toString, font_score, null) // score
    display.setColor(Color.WHITE)
    display.drawFilledCircle(this.board.BOARD_WIDTH + 60, MARGIN + 140, 25)
    display.setColor(Color.BLACK)
    display.drawLine(this.board.BOARD_WIDTH + 100, MARGIN + 170, 1200, MARGIN + 170)
  }

  def init_game(): Unit = {
    this.displayBoard()
    this.paintPion()
    this.displayText()
  }

  def isOver: Boolean = {
    false
  }

  def count_possible_shots(): Int = {
    var count: Int = 0

    count
  }

  def count_point(player: Player): Unit = {
    var count: Int = 0
    for(i <- this.board.playBoard.indices){
      for(j <- this.board.playBoard(i).indices){
        if(this.board.playBoard(i)(j).pion.color == player.color) count += 1
      }
    }
    player.score = count
  }
}
