package BLL;

import BE.Song;
import DAL.db.SongToPlaylistDAO_DB;

import java.util.List;

public class SongToPlaylistManager {

    SongToPlaylistDAO_DB songToPlayListDAO = new SongToPlaylistDAO_DB();

    public SongToPlaylistManager() throws Exception {

    }

    public List<Song> getAllSongToPlaylistlist(int playlisteID) throws Exception {
        return songToPlayListDAO.getAllSongsFromPlaylist(playlisteID);
    }




}

