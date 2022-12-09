package BLL;

import BE.Playlist;
import BE.Song;
import DAL.db.SongToPlaylistDAO_DB;

import java.sql.SQLException;
import java.util.List;

public class SongToPlaylistManager {

    SongToPlaylistDAO_DB songToPlayListDAO = new SongToPlaylistDAO_DB();

    public SongToPlaylistManager() throws Exception {

    }

    public List<Song> getAllSongToPlaylistlist(int playlisteID) throws Exception {
        return songToPlayListDAO.getAllSongsFromPlaylist(playlisteID);
    }


    public void addSongToPlaylist(Song selectedSong, Playlist selectedPlaylist) throws SQLException {
        songToPlayListDAO.addSongToPlaylist(selectedSong,selectedPlaylist);
    }
}

