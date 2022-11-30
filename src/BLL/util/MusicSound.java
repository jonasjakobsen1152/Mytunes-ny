package BLL.util;


import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;

import java.io.File;

public class MusicSound {
    int soundLevel = 0;
    MediaPlayer play;

    public String getMusicPath() {
        String path = "C:\\Musik\\06 Hop-Bobbelloppe.mp3";
        return path;
    }
//
// C:\Musik\01 Slotstema - Minuet.mp3

    public void playMusic() throws Exception {
        Media hit = new Media(new File(getMusicPath()).toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(hit);

        play = mediaPlayer;
        play.play();
timeMusic();
    }

    public void stopMusic() {

        play.stop();

    }


    public void soundVolume(double soundLevel) {

        double soundLev = soundLevel / 100;
        play.setVolume(soundLev);


    }

    public int timeMusic() throws Exception
    {

            AudioFile audioFile = AudioFileIO.read(new File(getMusicPath())); //Kaster en masse røde infobeskeder. Det gør den bare og gør arbejdet. Vi lever med det.
            int duration = audioFile.getAudioHeader().getTrackLength();
        System.out.println(duration);


        return duration;
        }

    }
