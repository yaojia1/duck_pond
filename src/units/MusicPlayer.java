package units;

import java.applet.Applet;
import java.applet.AudioClip;
import java.io.File;
import java.net.MalformedURLException;

public class MusicPlayer {
    private static File file = null;
    //创建audioclip对象
    private static AudioClip audioClip = null;
    public static void play(String fileUrl) {
//选择播放文件
        file = new File(fileUrl);

//将file转换为url
        try {
            audioClip = Applet.newAudioClip(file.toURI().toURL());
        } catch (MalformedURLException e) {
// TODO Auto-generated catch block
            e.printStackTrace();
        }
//循环播放  播放一次可以使用audioClip.play
        audioClip.loop();
//      audioClip.play();

    }
    public static void stop() {
        audioClip.stop();
    }

}
