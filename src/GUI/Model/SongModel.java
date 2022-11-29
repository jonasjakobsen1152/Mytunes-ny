package GUI.Model;

import BE.Song;
import BLL.SongManager;
import javafx.collections.ObservableList;

public class SongModel {
    private ObservableList<Song> songsToBeViewed;
    private SongManager songManager;

    public SongModel() throws Exception{
        songManager = new SongManager();
    }
}
