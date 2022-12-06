package DAL;

import BE.Song;

import java.util.List;

public interface ISongDAO {
    public List<Song> getAllSongs() throws Exception;

    public Song createSong(String title, String artist, String category, String filePath) throws Exception;

    public void updateSong(Song song) throws Exception;

    public void deleteSong(Song song) throws Exception;
}
