package BLL.util;

import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;

import java.io.File;

public class SecondsToMinute {

    public int timeMusic (String path) throws Exception
    {

        AudioFile audioFile = AudioFileIO.read(new File(path)); //Kaster en masse røde infobeskeder. Det gør den bare og gør arbejdet.
        // Det lever vi med.

        int duration = audioFile.getAudioHeader().getTrackLength();

        System.out.println("Det er ikke en fejl. Sådan gør den bare!");
        return duration;
    }

    public String secondsMinute(int seconds)  //Metoden er af string typen. Derfor returnere den en streng.
    {

        int hours=0;
        int minutes = 0;
        int secondRest=0;
        String hourString="";
        String minuteString="";
        String secondString="";


        secondRest=seconds;
        hours=secondRest/3600; //Først udregnes antallet af timer. Eftersom der er 3600 sekunder på time dividers med det tal.

        if (hours>=1)
        {
            secondRest=seconds-3600*hours; //Her udregnes resten. Det kunne vi også havde gjort med % metoden. Altså seconds % 3600. Det vil give resten.
                     }
        minutes=secondRest/60;   //Vi tager resten af sekunderne og fortsætter med udregning af antallet af minutter. Her divideres med 60.

        if (minutes>=1)
        {
            secondRest=secondRest-60*minutes;
        }

        if (hours<=10)
            hourString="0"+hours;  //Vi vil have formatet 00:00:00. Derfor tilføjes manglende nuller.
        else
            hourString=hours+"";

        if (minutes<=10)
            minuteString="0"+minutes;
        else
            minuteString=minutes+"";

        if (secondRest<=10)
            secondString="0"+secondRest;
        else
            secondString=secondRest+"";




        String time=  hourString+ ":"+minuteString+":"+ secondString;

        return time;
    }




}
