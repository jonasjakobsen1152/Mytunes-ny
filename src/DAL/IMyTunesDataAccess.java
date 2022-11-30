package DAL;

import BE.Song;

import java.util.List;

public interface IMyTunesDataAccess {
    public List<Song> getAllSongs() throws Exception;

    public Song createSong(String title, String artist, String category, int seconds,String filePath) throws Exception;

    public void updateSong(Song movie) throws Exception;

    public void deleteSong(Song movie) throws Exception;

}
