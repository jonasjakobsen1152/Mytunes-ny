package DAL;

import BE.Playlist;

import java.util.List;

public interface IPlaylistDAO {
    public List<Playlist> getAllPlaylist() throws Exception;

    public Playlist createPlaylist(String name) throws Exception;

    public void updatePlaylist(Playlist playlist) throws Exception;

    public void deletePlaylist(Playlist playlist) throws Exception;
}
