package GUI.Model;

import BE.Playlist;
import BE.Song;
import BLL.SongToPlaylistManager;
import com.microsoft.sqlserver.jdbc.SQLServerException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.SQLException;

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

    public void addSongToPlaylist(Song selectedSong, Playlist selectedPlaylist, int playlistSize) throws SQLException {
        songToPlaylistManager.addSongToPlaylist(selectedSong,selectedPlaylist,playlistSize);
    }

    public void deleteSongFromPlaylist(Song selectedSong, Playlist selectedPlaylist, int selectedRank) throws Exception {
        songToPlaylistManager.deleteSongFromPlaylist(selectedSong,selectedPlaylist,selectedRank);
    }


    public void songSwap(int number1, int number2, int playlist) throws SQLServerException {

        songToPlaylistManager.songSwap(number1, number2, playlist);
    }

    public int getRank(int songID, int playlistID) throws SQLServerException {
        return songToPlaylistManager.getRank(songID, playlistID);

    }
}
