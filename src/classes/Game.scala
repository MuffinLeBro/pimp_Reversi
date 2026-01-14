package classes

import `trait`.Config
import hevs.graphics.FunGraphics
import hevs.graphics.utils.GraphicsBitmap
import listener.{BoardListener, BoardMotionListener}
import utils.{Dialogs, Shape}

import java.awt.{Color, Font}
import scala.util.control.Breaks.{break, breakable}

class Game extends Config{

  private var _click_enable: Boolean = false
  private val GRAPHICS_WIDTH: Int = 1300
  private val GRAPHICS_HEIGHT: Int = 950
  val display: FunGraphics = new FunGraphics(GRAPHICS_WIDTH, GRAPHICS_HEIGHT)
  private var _players: Array[Player] = new Array[Player](2)
  var current_player: Player = _
  var number_of_switch: Int = 0
  var board: Board = new Board(8, 8)
  // the audio files
   private val audio_tic_toc: Audio = new Audio("/sounds/tictoc.wav")
   private val audio_winner: Audio = new Audio("/sounds/winner.wav")
   val audio_mistake: Audio = new Audio("/sounds/mistake.wav")
   val audio_good: Audio = new Audio("/sounds/good.wav")
  // images
  private val img = new GraphicsBitmap("/img/index.png")

  def players: Array[Player] = _players // getter players
  def players_=(value: Array[Player]): Unit = {
    _players = value
  }

  def click_enable: Boolean = _click_enable
  def click_enable_=(value: Boolean): Unit = {
    _click_enable = value
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
    this.board.paintFirstPion(display) // paint the 4 first pawns
  }

  private def displayText(): Unit = {
    for(i <- players.indices){
      this.count_point(this.players(i))
    }
    display.setColor(Color.BLACK)
    display.drawString(this.board.BOARD_WIDTH + 100, MARGIN + 60, this.players(0).name.toUpperCase , FONT_NAME, null) // name player 1
    display.drawString(this.board.BOARD_WIDTH + 230, MARGIN + 60, this.players(0).score.toString , FONT_SCORE, null) // score player 1
    display.setColor(Color.BLACK)
    display.drawFilledCircle(this.board.BOARD_WIDTH + 60, MARGIN + 40, 25)
    display.drawLine(this.board.BOARD_WIDTH + 100, MARGIN + 70, 1200, MARGIN + 70)

    display.setColor(Color.WHITE)
    display.drawString(this.board.BOARD_WIDTH + 100, MARGIN + 160, this.players(1).name.toUpperCase, FONT_NAME, null) // name player 2
    display.drawString(this.board.BOARD_WIDTH + 230, MARGIN + 160, this.players(1).score.toString, FONT_SCORE, null) // score player 2
    display.drawFilledCircle(this.board.BOARD_WIDTH + 60, MARGIN + 140, 25)
    display.drawLine(this.board.BOARD_WIDTH + 100, MARGIN + 170, 1200, MARGIN + 170)
  }

  def init_game(): Unit = {
    this.displayBoard()
    this.displayText()
    this.display.addMouseListener(new BoardListener(this))
    this.display.addMouseMotionListener(new BoardMotionListener(this))
    this.audio_tic_toc.play()
  }

  def isOver: Boolean = {
    this.number_of_switch == 2 || this.board.countEmptyCell() == 0
  }

   private def count_point(player: Player): Unit = {
    var count: Int = 0
    for(i <- this.board.playBoard.indices){
      for(j <- this.board.playBoard(i).indices){
        if(this.board.playBoard(i)(j).pion.color == player.color) count += 1
      }
    }
    player.score = count
  }

  def switch_player(): Unit = {

    breakable{
      for(i <- this.players.indices){
        if(this.players(i).name != this.current_player.name ){
          this.current_player = this.players(i)
          this.updateTurn(this.players.indexOf(this.current_player))
          break()
        }
      }
    }
  }

  def updateScore(): Unit = {
    for(i <- this.players.indices){
      this.count_point(this.players(i))
    }
    display.setColor(BACKGROUND)
    display.drawFillRect(this.board.BOARD_WIDTH + 230, MARGIN + 40, 50, 30)
    display.drawFillRect(this.board.BOARD_WIDTH + 230, MARGIN + 140, 50, 30)
    display.setColor(Color.WHITE)
    display.drawString(this.board.BOARD_WIDTH + 230, MARGIN + 60, this.players(0).score.toString , FONT_SCORE, null)
    display.drawString(this.board.BOARD_WIDTH + 230, MARGIN + 160, this.players(1).score.toString, FONT_SCORE, null)
  }

  def updateTurn(index: Int): Unit = {
    // to display
    var y = if(index == 0) MARGIN + 40 else MARGIN + 140
    display.setColor(BACKGROUND)
    display.drawFillRect(this.board.BOARD_WIDTH + 50, y - 5, 45, 50)
    if(!this.isOver){
      display.setColor(Color.ORANGE)
      display.drawFilledCircle(this.board.BOARD_WIDTH + 58, y - 4, 35)
    }
    display.setColor(if(index == 0) Color.BLACK else Color.WHITE)
    display.drawFilledCircle(this.board.BOARD_WIDTH + 63, y, 25)

    // to hide
    var y_to_hide = if(index == 0) MARGIN + 140 else MARGIN + 40
    display.setColor(BACKGROUND)
    display.drawFillRect(this.board.BOARD_WIDTH + 50, y_to_hide - 5, 45, 50)
    display.setColor(if(index == 0) Color.WHITE else Color.BLACK)
    display.drawFilledCircle(this.board.BOARD_WIDTH + 63, y_to_hide, 25)
  }

  def start(): Unit = {
    this.askPlayerName()
    this.init_game()
    this.current_player = this.players(0)
    this.startTurn()
  }

  private def end(): Unit = {
    this.audio_tic_toc.stop()
    this.audio_winner.play()
  }

  def startTurn(): Unit = {
    if (this.isOver) {  // if the game is over
      this.end()
      return
    }
    if(!this.current_player.can_play(this.board)){ // if the current player can play
      this.number_of_switch += 1
      this.switch_player()
      startTurn()
    }
    else{
      board.showPlayableMoves(display,current_player)
      this.enableClick()
    }
  }

  private def enableClick(): Unit = {
    this.click_enable = true
  }

  def disableClick(): Unit = {
    this.click_enable = false
  }
}
