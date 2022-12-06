package BLL;

import BE.Playlist;
import BE.Song;
import DAL.IPlaylistDAO;
import DAL.ISongDAO;
import DAL.db.PlaylistDAO_DB;
import DAL.db.SongDAO_DB;

import java.util.List;

public class PlaylistManager {
    private IPlaylistDAO playlistDAO;

    public PlaylistManager() throws Exception {playlistDAO = new PlaylistDAO_DB();}

    public List<Playlist>getAllPlaylist() throws Exception {
        return playlistDAO.getAllPlaylist();
    }
    public Playlist createPlaylist(String name) throws Exception {
        return playlistDAO.createPlaylist(name);
    }
    public void updatePlaylist(Playlist updatedPlaylist) throws Exception {
        playlistDAO.updatePlaylist(updatedPlaylist);
    }
    public void deletePlaylist(Playlist playlist) throws Exception{
        //TODO deletePlaylist
    }
}
