package BLL.util;


import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;

import java.io.File;

public class MusicSound {
    int soundLevel = 0;
    static MediaPlayer play;
    static Media hit;

    static boolean isMusicPlaying;

//
// C:\Musik\01 Slotstema - Minuet.mp3

    public void playMusic(String path) throws Exception {

        hit = new Media(new File(path).toURI().toString());
        play = new MediaPlayer(hit);
        System.out.println("inden if "+isMusicPlaying);

        if (isMusicPlaying==true)
        {

            isMusicPlaying=false;
            System.out.println(isMusicPlaying);
            play.stop();

        }
        else if (isMusicPlaying==false)
        {

            isMusicPlaying=true;
            System.out.println(isMusicPlaying);
            play.play();

        }


    }

    public void stopMusic(String path)
    {
     //  hit = new Media(new File(path).toURI().toString());
      // play = new MediaPlayer(hit);
        System.out.println("Test");
        play.stop();

    }


    public void soundVolume(double soundLevel) {

        double soundLev = soundLevel / 100;
        play.setVolume(soundLev);


    }

    public int timeMusic(String path) throws Exception
    {

            AudioFile audioFile = AudioFileIO.read(new File(path)); //Kaster en masse røde infobeskeder. Det gør den bare og gør arbejdet.
        // Det lever vi med.

            int duration = audioFile.getAudioHeader().getTrackLength();

        System.out.println(duration);
        return duration;
                    }

    }
