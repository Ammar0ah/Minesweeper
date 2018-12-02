package sample;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

public class Music extends Thread {
    private String path;
    private InputStream music;
    private AudioStream audio;
    String bip = "bip.mp3";

    public Music() {
        path = "D:\\Compressed\\Nader\\Minesweeper-second_Part\\Music\\tune.mp3";
    }

    @Override
    public void run() {
        try {
//            music = new FileInputStream(path);
//            audio = new AudioStream(music);
//            AudioPlayer.player.start(audio);
            Media hit = new Media(new File(path).toURI().toString());
            MediaPlayer mediaPlayer = new MediaPlayer(hit);
            mediaPlayer.play();

        } catch (Exception ex) {
        }
    }

}
