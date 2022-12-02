package BLL.util;


import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;

import java.io.File;

public class MusicSound {
   static double soundLevel = 100;
    static MediaPlayer play; //Vi er nød til at kunne huske mediaplayer, media og boolean isMusicPlaying. Ellers kan sange ikke stoppes, når klassen lukkes.
    static Media hit;

    static boolean isMusicPlaying=false;



    public void playMusic(String path) throws Exception {



        if (isMusicPlaying)
        {
             isMusicPlaying=false;
            play.stop();

        }
        else
        {

                hit = new Media(new File(path).toURI().toString());
                play = new MediaPlayer(hit);
                isMusicPlaying=true;
                soundVolume(soundLevel);
                play.play();

        }


    }



    public void soundVolume(double soundLevel) {

        this.soundLevel=soundLevel;
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
