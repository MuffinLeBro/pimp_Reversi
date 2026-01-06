package `trait`

import java.awt.{Color, Font}

trait Config {
  val RADIUS: Int = 50
  val CELL_DIMENSION: Int = 100
  val MARGIN: Int = 70
  val PADDING: Int = 10

  val GREEN: Color = new Color(54, 95, 38)
  val LINE_COLOR = new Color(40, 72, 29)
  val BACKGROUND = new Color(76, 75, 75)

  val FONT_SCORE = new Font("Consolas", Font.BOLD, 30)
  val FONT_NAME = new Font("Arial", Font.BOLD, 22)
}
