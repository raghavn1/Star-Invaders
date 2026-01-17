import java.io.IOException;
import javax.sound.sampled.*;

public class Sound {

    private Clip clip;

    public Sound(String path) {
        try {
            AudioInputStream audio = AudioSystem.getAudioInputStream(getClass().getResource(path));

            clip = AudioSystem.getClip();
            clip.open(audio);

        } catch (UnsupportedAudioFileException |IOException |LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    public void play() {
        if (clip != null){
            clip.stop();
            clip.setFramePosition(0);
            clip.start();
        }
    }

    public void loop() {
        if (clip != null){
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        }
    }

    public void stop() {
        if (clip != null){
            clip.stop();
        }
    }
}