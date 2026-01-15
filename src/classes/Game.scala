package classes

import `trait`.Config
import hevs.graphics.FunGraphics
import hevs.graphics.utils.GraphicsBitmap
import listener.{BoardListener, LoadListener}
import utils.{Dialogs, Shape}

import java.awt.{Color, DisplayMode, Font, FontMetrics, Label}
import scala.util.control.Breaks.{break, breakable}

class Game extends Config{

  private var _click_enable: Boolean = false
  private var _isStarted: Boolean = false
  private var _isOnBeginning: Boolean = true

  private val GRAPHICS_WIDTH: Int = 1300
  private val GRAPHICS_HEIGHT: Int = 950

  private var _display: FunGraphics = new FunGraphics(GRAPHICS_WIDTH, GRAPHICS_HEIGHT)
  private var _players: Array[Player] = new Array[Player](2)
  var current_player: Player = _
  private var number_of_switch: Int = 0
  var board: Board = new Board(8, 8)
  // the audio files
  private val audio_tic_toc: Audio = new Audio("/sounds/tictoc.wav")
  private val audio_winner: Audio = new Audio("/sounds/winner.wav")
  val audio_mistake: Audio = new Audio("/sounds/mistake.wav")
  val audio_intro: Audio = new Audio("/sounds/intro.wav")
  val audio_restart: Audio = new Audio("/sounds/restart.wav")
  // images
  private val img_crown = new GraphicsBitmap("/img/crown.png")
  private val img_pion = new GraphicsBitmap("/img/pion.png")
  private val img_bg_button = new GraphicsBitmap("/img/Button_PLAY.png")
  private val img_grille = new GraphicsBitmap("/img/grille.png")
  private val img_skip = new GraphicsBitmap("/img/skip.png")
  private val img_draw = new GraphicsBitmap("/img/draw.png")

  def players: Array[Player] = _players // getter players
  def players_=(value: Array[Player]): Unit = {
    _players = value
  }

  def isOnBeginning: Boolean = _isOnBeginning
  def isOnBeginning_=(value: Boolean): Unit = {
    _isOnBeginning = value
  }

  def click_enable: Boolean = _click_enable
  def click_enable_=(value: Boolean): Unit = {
    _click_enable = value
  }

  def display: FunGraphics = _display
  def display_=(value: FunGraphics): Unit = {
    _display = value
  }

  def isStarted: Boolean = _isStarted
  def isStarted_=(value: Boolean): Unit = {
    _isStarted = value
  }

  private def askPlayerName(): Unit = {
    for(i <- players.indices){
      val name: String = Dialogs.getString(s"PLAYER ${i + 1}" )
      this.players(i) = if (i == 0) new Player(name, BLACK) else new Player(name, Color.WHITE)
    }
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
    this.board.paintPion(display) //paint all pawns

    this.display.setColor(Color.BLACK)
    this.display.drawFillRect(45, 45, 850, 25) //top border
    this.display.drawFillRect(45, 870, 850, 25) //botton border
    this.display.drawFillRect(45, 45, 25, 850) //left border
    this.display.drawFillRect(870, 45, 25, 850) //right border

    this.display.setColor(Color.WHITE)
    this.display.drawLine(45, 45, 70, 70) // line top left
    this.display.drawLine(870, 70, 895, 45) // line top right
    this.display.drawLine(870, 870, 895, 895) // line bottom right
    this.display.drawLine(45, 895, 70, 870) // line bottom left

    // BUTTON RESTART THE GAME
    this.display.drawTransformedPicture(1100, 700, 0, 0.5, this.img_bg_button)
    this.display.drawString(POSITION_TEXT_RESTART_REPLAY._1, POSITION_TEXT_RESTART_REPLAY._2, "Restart", CUSTOM_FONT_AUDIOWIDE.deriveFont(Font.BOLD, 25f), null)
    // BUTTON QUIT THE GAME
    this.display.drawTransformedPicture(1100, 800, 0, 0.5, this.img_bg_button)
    this.display.drawString(POSITION_TEXT_QUIT._1, POSITION_TEXT_QUIT._2, "Quit game", CUSTOM_FONT_AUDIOWIDE.deriveFont(Font.BOLD, 25f), null)
  }

  def displayText(): Unit = {
    for(i <- players.indices){
      this.count_point(this.players(i))
    }
    display.setColor(BLACK)
    display.drawString(this.board.BOARD_WIDTH + 100, MARGIN + 60, this.truncateWithEllipsis(this.players(0).name.toUpperCase, 10) , CUSTOM_FONT_AUDIOWIDE.deriveFont(20f), null) // name player 1
    display.drawString(this.board.BOARD_WIDTH + 280, MARGIN + 60, this.players(0).score.toString , CUSTOM_FONT_AUDIOWIDE.deriveFont(20f), null) // score player 1
    display.setColor(BLACK)
    display.drawFilledCircle(this.board.BOARD_WIDTH + 60, MARGIN + 40, 25)
    display.drawLine(this.board.BOARD_WIDTH + 100, MARGIN + 70, 1200, MARGIN + 70)

    display.setColor(Color.WHITE)
    display.drawString(this.board.BOARD_WIDTH + 100, MARGIN + 160, this.truncateWithEllipsis(this.players(1).name.toUpperCase, 10), CUSTOM_FONT_AUDIOWIDE.deriveFont(20f), null) // name player 2
    display.drawString(this.board.BOARD_WIDTH + 280, MARGIN + 160, this.players(1).score.toString, CUSTOM_FONT_AUDIOWIDE.deriveFont(20f), null) // score player 2
    display.drawFilledCircle(this.board.BOARD_WIDTH + 60, MARGIN + 140, 25)
    display.drawLine(this.board.BOARD_WIDTH + 100, MARGIN + 170, 1200, MARGIN + 170)
  }

  def loadGame(): Unit = {
    this.displayScreenloader()
//    this.audio_intro.play()
    this.display.addMouseListener(new LoadListener(this))
  }

  private def displayScreenloader(): Unit = {
    this.display.setColor(BACKGROUND)
    this.display.drawFillRect(0, 0, GRAPHICS_WIDTH, GRAPHICS_HEIGHT)
    this.display.drawTransformedPicture(POSITION_BUTTON_PLAY._1, POSITION_BUTTON_PLAY._2, 0, 0.8, this.img_bg_button)

    this.display.drawTransformedPicture(POSITION_PION._1 + 200, POSITION_PION._2 - 200, 1.7, 1, this.img_grille)
    this.display.drawTransformedPicture(POSITION_PION._1 - 100, POSITION_PION._2, -1.7, 1, this.img_grille)
    this.display.drawTransformedPicture(POSITION_PION._1 - 100, POSITION_PION._2, -1.5, 1, this.img_pion)
    this.display.drawTransformedPicture(POSITION_PION._1 + 200, POSITION_PION._2 - 200, 1.7, 0.9, this.img_pion)

    this.display.setColor(Color.WHITE)
    this.display.drawString(POSITION_TEXT_PLAY._1, POSITION_TEXT_PLAY._2, "PLAY", CUSTOM_FONT_AUDIOWIDE.deriveFont(Font.BOLD, 35f), null)
    this.display.drawString(100, 300, "Reversi", CUSTOM_FONT_LOBSTER.deriveFont(Font.BOLD, 140f), null)
    this.display.drawString(800, 750, "Classic", CUSTOM_FONT_LOBSTER.deriveFont(Font.BOLD, 140f), null)
  }

  private def init_game(): Unit = {
    this.displayBoard()
    this.displayText()
    this.display.addMouseListener(new BoardListener(this))
    this.audio_tic_toc.play()
  }

  private def isOver: Boolean = {
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
    display.drawFillRect(this.board.BOARD_WIDTH + 280, MARGIN + 40, 50, 30)
    display.drawFillRect(this.board.BOARD_WIDTH + 280, MARGIN + 140, 50, 30)
    display.setColor(this.players(0).color)
    display.drawString(this.board.BOARD_WIDTH + 280, MARGIN + 60, this.players(0).score.toString, CUSTOM_FONT_AUDIOWIDE.deriveFont(20f), null)
    display.setColor(this.players(1).color)
    display.drawString(this.board.BOARD_WIDTH + 280, MARGIN + 160, this.players(1).score.toString, CUSTOM_FONT_AUDIOWIDE.deriveFont(20f), null)
  }

  private def updateTurn(index: Int): Unit = {
      // to display
      var y = if (index == 0) MARGIN + 40 else MARGIN + 140
      display.setColor(BACKGROUND)
      display.drawFillRect(this.board.BOARD_WIDTH + 50, y - 5, 45, 50)
      if (!this.isOver) {
        display.setColor(Color.ORANGE)
        display.drawFilledCircle(this.board.BOARD_WIDTH + 58, y - 4, 35)
      }
      display.setColor(if (index == 0) BLACK else Color.WHITE)
      display.drawFilledCircle(this.board.BOARD_WIDTH + 63, y, 25)

      // to hide
      var y_to_hide = if (index == 0) MARGIN + 140 else MARGIN + 40
      display.setColor(BACKGROUND)
      display.drawFillRect(this.board.BOARD_WIDTH + 50, y_to_hide - 5, 45, 50)
      display.setColor(if (index == 0) Color.WHITE else BLACK)
      display.drawFilledCircle(this.board.BOARD_WIDTH + 63, y_to_hide, 25)
  }

  def start(): Unit = {
    this.askPlayerName()
    this.init_game()
    this.current_player = this.players(0)
    this.updateTurn(0)
    this.board.showPlayableMoves(this.display,this.current_player)
    this.startTurn()
  }

  def restart(): Unit = {
    if(this.audio_winner.audioClip.isRunning) this.audio_winner.stop()
    this.isOnBeginning = true
    this.board = new Board(8, 8)
    this.number_of_switch = 0
    this.display.clear()
    this.displayBoard()
    this.resetScore()
    this.displayText()
    this.current_player = this.players(0)
    this.updateTurn(0)
    this.board.showPlayableMoves(this.display,this.current_player)
    this.audio_tic_toc.play()
    this.startTurn()
  }

  private def resetScore(): Unit = {
    for (player <-this.players) {
      player.score = 0
    }
  }

  private def end(): Unit = {
    if(this.audio_tic_toc.audioClip.isRunning) this.audio_tic_toc.stop()
    this.audio_winner.play()
    if(!this.isDraw) { // if there is a winner
      this.addCrownToWinner(this.players.indexOf(this.getWinner))
      this.showMessageWinner(this.current_player.name)
    }
    else{
      this.showMessageDraw()
    }

    // BUTTON REPLAY THE GAME
    display.setColor(BACKGROUND)
    display.drawFillRect(995, 660, 200, 80)
    display.setColor(Color.WHITE)
    this.display.drawTransformedPicture(1100, 700, 0, 0.5, this.img_bg_button)
    this.display.drawString(POSITION_TEXT_RESTART_REPLAY._1, POSITION_TEXT_RESTART_REPLAY._2, "Replay", CUSTOM_FONT_AUDIOWIDE.deriveFont(Font.BOLD, 25f), null)
  }

  def startTurn(): Unit = {
    this.isOnBeginning = false
    if (this.isOver) { // if the game is over
      this.end()
      this.enableClick()
      this.showMessageEndGame()
      return
    }
    if(!this.current_player.can_play(this.board)){ // if the current player can play
      var name: String = this.current_player.name
      this.number_of_switch += 1
      this.switch_player()
      if(this.current_player.can_play(this.board)) {
        showMessageSkipTurn(name)
        this.board.showPlayableMoves(this.display,this.current_player)
        startTurn()
      }
      else{
        this.number_of_switch += 1
        startTurn()
      }
    }
    else{
      this.number_of_switch = 0
      this.enableClick()
    }
  }

  private def enableClick(): Unit = {
    this.click_enable = true
  }

  def disableClick(): Unit = {
    this.click_enable = false
  }

  private def addCrownToWinner(index: Int): Unit = {
    var y = if(index == 0) MARGIN + 40 else MARGIN + 140
    display.drawTransformedPicture(this.board.BOARD_WIDTH + 350, y, 0, 0.1, this.img_crown)
  }

  private def isDraw: Boolean = {
   this.players(0).score == this._players(1).score
  }

  private def showMessageSkipTurn(playerName: String): Unit = {
    val approxCharWidth = 20 // average character width
    val textWidth = if(playerName.length < 20) playerName.length * approxCharWidth else 20 * 20
    val center = (this.GRAPHICS_WIDTH - this.board.BOARD_WIDTH) / 2
    val textX = center + this.board.BOARD_WIDTH - (textWidth / 2)

    display.drawTransformedPicture(1090, 420, 0, 1, this.img_skip)
    display.setColor(Color.ORANGE)
    this.display.drawString(1010, 350, " No move!!", CUSTOM_FONT_AUDIOWIDE.deriveFont(Font.BOLD, 30f), null)
    display.setColor(Color.WHITE)
    display.setColor(MESSAGE_SKIP_WIN)
    this.display.drawString(
      if(playerName.length < 8) textX
      else if(playerName.length >= 8 && playerName.length <= 9) textX + 5
      else if(playerName.length > 9 && playerName.length <= 15) textX + 5
      else textX + 30,
      500, s"${truncateWithEllipsis(playerName, 17)}", CUSTOM_FONT_AUDIOWIDE.deriveFont(Font.BOLD, 32f), null)
  }

  private def showMessageWinner(playerName: String): Unit = {
    val approxCharWidth = 20 // average character width
    val textWidth = if(playerName.length < 20) playerName.length * approxCharWidth else 20 * 20
    val center = (this.GRAPHICS_WIDTH - this.board.BOARD_WIDTH) / 2
    val textX = center + this.board.BOARD_WIDTH - (textWidth / 2)

    this.display.drawTransformedPicture(1090, 450, 0, 0.3, this.img_crown)
    display.setColor(MESSAGE_SKIP_WIN)
    this.display.drawString(
      if(playerName.length < 8) textX
      else if(playerName.length >= 8 && playerName.length <= 9) textX + 5
      else if(playerName.length > 9 && playerName.length <= 15) textX + 5
      else textX + 30,
      550, s"${truncateWithEllipsis(s"${this.getWinner.name}", 17)}", CUSTOM_FONT_AUDIOWIDE.deriveFont(Font.BOLD, 32f), null)

    display.setColor(Color.ORANGE)
    this.display.drawString(1025, 350, "Victory", CUSTOM_FONT_LOBSTER.deriveFont(Font.BOLD, 40f), null)
  }

  private def showMessageDraw(): Unit = {
    this.display.drawTransformedPicture(1090, 450, 0, 0.8, this.img_draw)
    display.setColor(MESSAGE_SKIP_WIN)
    this.display.drawString(1020, 550, "No winner", CUSTOM_FONT_AUDIOWIDE.deriveFont(Font.BOLD, 32f), null)

    display.setColor(Color.ORANGE)
    this.display.drawString(1060, 350, "Draw", CUSTOM_FONT_LOBSTER.deriveFont(Font.BOLD, 40f), null)
  }

  private def truncateWithEllipsis(input: String, maxLength: Int): String = {
    if (input.length <= maxLength) input
    else input.take(maxLength - 3) + "..."
  }

  private def getWinner: Player = {
    var max_score = this.players.map(_.score).max
    this.players.filter(_.score == max_score)(0)
  }

  private def showMessageEndGame(): Unit = {
    this.display.frontBuffer.synchronized {
      display.setColor(Color.ORANGE)
      this.display.drawString(350, 470, "End game !!", CUSTOM_FONT_AUDIOWIDE.deriveFont(Font.BOLD, 40f), null)
    }
    this.display.syncGameLogic(60)
  }
}
