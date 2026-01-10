package `trait`

import java.awt.{Color, Font}
import java.io.File

trait Config {
  val RADIUS: Int = 50
  val CELL_DIMENSION: Int = 100
  val MARGIN: Int = 70
  val PADDING: Int = 10

//  val GREEN: Color = new Color(79, 152, 46)
  val BLACK: Color = new Color(13, 13, 13)
  val GREEN: Color = new Color(54, 95, 38)
//  val LINE_COLOR = new Color(40, 72, 29)
  val LINE_COLOR = Color.BLACK
  val BACKGROUND = new Color(67, 130, 60)

  val FONT_SCORE = new Font("Consolas", Font.BOLD, 30)
  val FONT_NAME = new Font("Arial", Font.BOLD, 22)

  // Load the font from a file
  private val audiowide = new File("src/font/Audiowide-Regular.ttf")
  private val lobster = new File("src/font/Lobster-Regular.ttf")
  val CUSTOM_FONT_AUDIOWIDE: Font = Font.createFont(Font.TRUETYPE_FONT, audiowide)
  val CUSTOM_FONT_LOBSTER: Font = Font.createFont(Font.TRUETYPE_FONT, lobster)

  val POSITION_BUTTON_PLAY: (Int, Int) = (200, 650)
  val POSITION_PION: (Int, Int) = (650, 550)
  val POSITION_TEXT_PLAY: (Int, Int) = (130, 670)
  val POSITION_TEXT_QUIT: (Int, Int) = (1030, 810)
  val POSITION_TEXT_RESTART_REPLAY: (Int, Int) = (1045, 710)
  val CLICK_REPLAY_RESTART_REPLAY: (Int, Int) = (1025, 665)
  val CLICK_QUIT: (Int, Int) = (1025, 765)
}
