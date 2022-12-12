package BLL;

import BE.Playlist;
import BE.Song;
import DAL.db.SongToPlaylistDAO_DB;
import com.microsoft.sqlserver.jdbc.SQLServerException;

import java.sql.SQLException;
import java.util.List;

public class SongToPlaylistManager {

    SongToPlaylistDAO_DB songToPlayListDAO = new SongToPlaylistDAO_DB();

    public SongToPlaylistManager() throws Exception {

    }

    public List<Song> getAllSongToPlaylistlist(int playlisteID) throws Exception {
        return songToPlayListDAO.getAllSongsFromPlaylist(playlisteID);
    }


    public void addSongToPlaylist(Song selectedSong, Playlist selectedPlaylist, int playlistSize) throws SQLException {
        songToPlayListDAO.addSongToPlaylist(selectedSong,selectedPlaylist,playlistSize);
    }

    public void deleteSongFromPlaylist(Song selectedSong, Playlist selectedPlaylist, int selectedRank) throws Exception {
        songToPlayListDAO.deleteSongFromPlaylist(selectedSong,selectedPlaylist,selectedRank);
    }

    public void songSwap(int number1, int number2, int playlist) throws SQLServerException {
        songToPlayListDAO.swap(number1, number2, playlist);

    }

}

