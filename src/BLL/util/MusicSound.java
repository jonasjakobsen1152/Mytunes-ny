package BLL.util;


import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;

import java.io.File;

public class MusicSound {




            public int timeMusic (String path) throws Exception
            {

                AudioFile audioFile = AudioFileIO.read(new File(path)); //Kaster en masse røde infobeskeder. Det gør den bare og gør arbejdet.
                // Det lever vi med.

                int duration = audioFile.getAudioHeader().getTrackLength();

                System.out.println("Det er ikke en fejl. Sådan gør den bare!");
                return duration;
            }






}