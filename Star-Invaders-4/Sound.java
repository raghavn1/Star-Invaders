//Class to handle loading and playing sound effects or music
import java.io.IOException;
import javax.sound.sampled.*;

public class Sound {

    //Variable to hold the actual audio data
    private Clip clip;

    //Constructor to find the sound file and get it ready to play
    public Sound(String path) {
        try {
            //Find the audio file using the path provided
            AudioInputStream audio = AudioSystem.getAudioInputStream(getClass().getResource(path));

            //Create a clip object and open it with the audio file
            clip = AudioSystem.getClip();
            clip.open(audio);

        } catch (UnsupportedAudioFileException |IOException |LineUnavailableException e) {
            //Print an error if the file can't be found or played
            e.printStackTrace();
        }
    }

    //Method to play the sound once from the beginning
    public void play() {
        if (clip != null){
            clip.stop();
            clip.setFramePosition(0); //Rewind to the start
            clip.start(); //Play the sound
        }
    }

    //Method to play the sound on a loop forever
    public void loop() {
        if (clip != null){
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        }
    }

    //Method to stop the sound 
    public void stop() {
        if (clip != null){
            clip.stop();
        }
    }
}