package BLL.util;


import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;

import java.io.File;

public class MusicSound {
    int soundLevel = 0;
    MediaPlayer play;
    Media hit;

    public String getMusicPath() {
        String path = "C:\\Musik\\file_example_WAV_10MG.wav";
        return path;
    }
//
// C:\Musik\01 Slotstema - Minuet.mp3

    public void playMusic() throws Exception {
        hit = new Media(new File(getMusicPath()).toURI().toString());
        play = new MediaPlayer(hit);
        play.play();



    }

    public void stopMusic() {

        play.stop();

    }


    public void soundVolume(double soundLevel) {

        double soundLev = soundLevel / 100;
        play.setVolume(soundLev);


    }

    public int timeMusic(String path) throws Exception
    {

            AudioFile audioFile = AudioFileIO.read(new File(getMusicPath())); //Kaster en masse røde infobeskeder. Det gør den bare og gør arbejdet.
        // Det lever vi med.

            int duration = audioFile.getAudioHeader().getTrackLength();

        System.out.println(duration);
        return duration;
                    }

    }
