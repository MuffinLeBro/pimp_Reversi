package utils

import java.awt.Font
import javax.swing.{JFrame, JOptionPane, JPasswordField, JTextField, WindowConstants}
import scala.annotation.tailrec


/**
 * This class allows to display simple messages and have graphical interfaces to
 * enter words and characters.
 *
 * @version 2.0
 *
 */
object Dialogs {
  /**
   * This function open a dialog box to enter a hidden String. The dialog box
   * asks for a String containing a minimum of one character.
   *
   * @param message
   * The message displayed to ask for the hidden String
   * @return The hidden String entered
   */
  @tailrec
  def getString(message: String): String = {
    val frame = new JFrame(message)
    frame.setSize(100, 100)
    val s = JOptionPane.showInputDialog(frame, message)
    frame.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE)
    if (s == null) System.exit(-1)
    if (s.nonEmpty) s
    else getString("Please enter the name of the player")
  }


  /**
   * This function open a dialog box to enter a character. This function
   * accepts only one character.
   *
   * @param message
   * The message asking for the character.
   * @return The character entered.
   */
  @tailrec
  def getChar(message: String): Char = {
    val frame = new JFrame("Input a character please")
    val s = JOptionPane.showInputDialog(frame, message)
    if (s == null) System.exit(-1)
    if (s.length == 1) s.charAt(0)
    else getChar("Just one character")
  }

  /**
   * Open a dialog box to display a message.
   *
   * @param message
   * The message to display.
   */
  def displayMessage(message: String): Unit = {
    JOptionPane.showMessageDialog(null, message, null, JOptionPane.PLAIN_MESSAGE)
  }
}