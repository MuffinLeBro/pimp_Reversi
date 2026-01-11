package classes

import javax.sound.sampled.{AudioSystem, Clip}

class Audio(path: String){
  var audioClip: Clip = _

  try {
    val url = classOf[Audio].getResource(path)
    println(url)
    val audioStream = AudioSystem.getAudioInputStream(url)

    audioClip = AudioSystem.getClip.asInstanceOf[Clip]
    audioClip.open(audioStream)
  } catch {
    case e: Exception =>
      e.printStackTrace()
  }

  def play(): Unit = {
    // Open stream and play
    try {
      if (!audioClip.isOpen) audioClip.open()
      audioClip.stop()
      if(path == "/sounds/tictoc.wav" || path == "/sounds/intro.wav"){
        audioClip.loop(Clip.LOOP_CONTINUOUSLY) // to loop a sound
      }
      audioClip.start()
    } catch {
      case e: Exception =>
        e.printStackTrace()
    }
  }

  def stop(): Unit = {
    // Open stream and play
    try {
      if (audioClip.isOpen) {
        audioClip.stop()
        audioClip.setFramePosition(0); // important if we want to do a stop and play after action
        audioClip.setMicrosecondPosition(0)
      }
    } catch {
      case e: Exception =>
        e.printStackTrace()
    }
  }
}
