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
        SongToPlayToBeViewed.addAll(songToPlaylistManager.getAllSongToPlaylistlist( 0));

    }

    public ObservableList<Song> getObservablePlaylist()
    {
        return SongToPlayToBeViewed;
    }

    public void showList(int playlisteID) throws Exception {
        //Update the listview
        SongToPlayToBeViewed.clear();
        SongToPlayToBeViewed.addAll(songToPlaylistManager.getAllSongToPlaylistlist(playlisteID));

    }
}
