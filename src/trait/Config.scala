package `trait`

import java.awt.{Color, Font}
import java.io.File

trait Config {
  // Dimensions and measures
  val RADIUS: Int = 50
  val CELL_DIMENSION: Int = 100
  val MARGIN: Int = 70
  val PADDING: Int = 10
  val HELPRADUIS : Int = 20

  // Color
  val BLACK: Color = new Color(13, 13, 13)
  val GREEN: Color = new Color(54, 95, 38)
  val YELLOW : Color = new Color(255,215,0)
  val LINE_COLOR: Color = Color.BLACK
  val BACKGROUND = new Color(67, 130, 60)
  val MESSAGE_SKIP_WIN = new Color(242, 223, 179)

  // Load the font from a file
  private val audiowide: File = new File("src/font/Audiowide-Regular.ttf")
  private val lobster: File = new File("src/font/Lobster-Regular.ttf")
  val CUSTOM_FONT_AUDIOWIDE: Font = Font.createFont(Font.TRUETYPE_FONT, audiowide)
  val CUSTOM_FONT_LOBSTER: Font = Font.createFont(Font.TRUETYPE_FONT, lobster)

  // All text positions
  val POSITION_BUTTON_PLAY: (Int, Int) = (200, 650)
  val POSITION_PION: (Int, Int) = (650, 550)
  val POSITION_TEXT_PLAY: (Int, Int) = (130, 670)
  val POSITION_TEXT_QUIT: (Int, Int) = (1030, 810)
  val POSITION_TEXT_RESTART_REPLAY: (Int, Int) = (1045, 710)
  val CLICK_REPLAY_RESTART_REPLAY: (Int, Int) = (1025, 665)
  val CLICK_QUIT: (Int, Int) = (1025, 765)
}
