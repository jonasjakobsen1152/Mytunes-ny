package GUI.Model;

import BE.Song;
import BLL.SongToPlaylistManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class SongToPlaylistModel {

    private ObservableList<Song> SongToPlayToBeViewed;
    private SongToPlaylistManager songToPlaylistManager;
    private Song selectedSong;


    public SongToPlaylistModel() throws Exception {
         songToPlaylistManager = new SongToPlaylistManager();
        SongToPlayToBeViewed = FXCollections.observableArrayList();
        SongToPlayToBeViewed.addAll(songToPlaylistManager.getAllSongToPlaylistlist());

    }

    public ObservableList<Song> getObservablePlaylist()
    {
        return SongToPlayToBeViewed;
    }




}
