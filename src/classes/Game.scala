package classes

import `trait`.Config
import hevs.graphics.FunGraphics
import utils.{Dialogs, Shape}

import java.awt.{Color, Font}

class Game extends Config{

  private val GRAPHICS_WIDTH: Int = 1300
  private val GRAPHICS_HEIGHT: Int = 1000
  val display: FunGraphics = new FunGraphics(GRAPHICS_WIDTH, GRAPHICS_HEIGHT)
  private var _players: Array[Player] = new Array[Player](2)
  var current_player: Player = _
  var board: Board = new Board(8, 8)


  def players: Array[Player] = _players // getter players
  def players_=(value: Array[Player]): Unit = {
    _players = value
  }


  def askPlayerName(): Unit = {
    for(i <- players.indices){
      val name: String = Dialogs.getString(s"PLAYER ${i + 1}" )
      this.players(i) = if (i == 0) new Player(name, Color.BLACK) else new Player(name, Color.WHITE)
    }
  }

  private def displayBoard(): Unit = {
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

  private def paintPion(): Unit = {
    for(i <- this.board.playBoard.indices){
      for(j <- this.board.playBoard(i).indices){
        Shape.drawDisc(display, this.board.playBoard(i)(j))
      }
    }
  }

  private def displayText(): Unit = {
    display.setColor(Color.BLACK)
    for(i <- players.indices){
      this.count_point(this.players(i))
    }
    val font_name = new Font("Arial", Font.PLAIN, 20)
    val font_score = new Font("Consolas", Font.PLAIN, 30)

    display.drawString(this.board.BOARD_WIDTH + 100, MARGIN + 60, this.players(0).name.toUpperCase , font_name, null) // name player 1
    display.drawString(this.board.BOARD_WIDTH + 230, MARGIN + 60, this.players(0).score.toString , font_score, null) // score player 1
    display.setColor(Color.BLACK)
    display.drawFilledCircle(this.board.BOARD_WIDTH + 60, MARGIN + 40, 25)
    display.drawLine(this.board.BOARD_WIDTH + 100, MARGIN + 70, 1200, MARGIN + 70)

    display.drawString(this.board.BOARD_WIDTH + 100, MARGIN + 160, this.players(1).name.toUpperCase, font_name, null) // name player 2
    display.drawString(this.board.BOARD_WIDTH + 230, MARGIN + 160, this.players(1).score.toString, font_score, null) // score player 2
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

  def switch_player(): Unit = {
    for(i <- this.players.indices){
      if(this.current_player.color != this.players(i).color) this.current_player = this.players(i)
    }
  }
}
