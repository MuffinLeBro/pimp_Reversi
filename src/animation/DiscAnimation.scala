package animation

import `trait`.Config
import classes.{Audio, Cell, Game}
import hevs.graphics.FunGraphics
import utils.Shape

import java.awt.Color
import javax.swing.Timer
import scala.collection.mutable.ListBuffer

class DiscAnimation(game: Game, display: FunGraphics, playboard: Array[Array[Cell]], color: Color, cellsToModify: Array[(Int, Int)]) extends Config{
  private val timer = new Timer(8, _ => update2())
  private val durationMs = 300.0
  private val startTime = System.currentTimeMillis()
  private var audio: Audio = if(cellsToModify.length - 1 > 6) new Audio("/sounds/many_flip.wav") else new Audio("/sounds/good.wav")

  def start(): Unit = {
    this.audio.play()
    timer.start()
    if(this.audio.audioClip.isRunning) this.audio.stop()
  }

  /**
   * Animation from the center to the border
   */
  private def update(): Unit = {
    val progress = audio.audioClip.getMicrosecondPosition.toDouble / audio.audioClip.getMicrosecondLength
    val p = progress min 1.0
    val smooth = easeInOut(p)
    val scale =
      if (smooth < 0.5) 1.0 - smooth * 2
      else (smooth - 0.5) * 2

    val CELL_RADIUS = CELL_DIMENSION / 2
    val PION_RADIUS = CELL_RADIUS - PADDING
    val animatedRadius = (PION_RADIUS * scale).toInt

    for ((i, j) <- cellsToModify) {
      val cell = playboard(i)(j)
      val drawColor =
        if (progress < 0.5) oppositeColor(color)
        else color

      Shape.drawDisc(display, playboard(i)(j), animatedRadius, drawColor)
    }
    if (p >= 1.0) timer.stop()
  }

  /**
   * Animation FLIP
   */
  private def update2(): Unit = {
    val elapsed = System.currentTimeMillis() - startTime
    val progress = (elapsed / durationMs) min 1.0

    val CELL_RADIUS = CELL_DIMENSION / 2
    val PION_RADIUS = CELL_RADIUS - PADDING

    // animation flip (0 → 1 → 0)
    val scale =
      if (progress < 0.5) 1.0 - progress * 2
      else (progress - 0.5) * 2

    val animatedWidth = (PION_RADIUS * scale).toInt max 1

    for ((i, j) <- cellsToModify) {
      val cell = playboard(i)(j)

      val drawColor =
        if (progress < 0.5) oppositeColor(color)
        else color

      Shape.drawFlipDisc(game, display, cell, animatedWidth, PION_RADIUS, drawColor)
    }
    display.syncGameLogic(60)
    if (progress >= 1.0) timer.stop()
  }

  private def oppositeColor(c: Color): Color = {
    if (c == Color.WHITE) Color.BLACK else Color.WHITE
  }

  private def easeInOut(t: Double): Double =
    if (t < 0.5) 2*t*t
    else 1 - math.pow(-2*t + 2, 2) / 2
}
