package frame;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;

public class musicStuff {
    public static void main(String[] args) {
        String filepath = "./music/1.wav";
        musicStuff musicObject = new musicStuff();
        musicObject.playMusic(filepath);
    }
    public void playMusic(String musicLocation)
    {
        try
        {
            File musicPath = new File(musicLocation);

            if(musicPath.exists())
            {
                AudioInputStream audioInput = AudioSystem.getAudioInputStream(musicPath);
                Clip clip = AudioSystem.getClip();
                clip.open(audioInput);
                clip.start();
                clip.loop(Clip.LOOP_CONTINUOUSLY);
            }
            else
            {

            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }

}

