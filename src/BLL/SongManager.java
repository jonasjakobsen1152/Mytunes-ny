package BLL;

import BE.Song;
import BLL.util.SongSearcher;
import DAL.IMyTunesDataAccess;
import DAL.db.SongDAO_DB;

import java.io.IOException;
import java.util.List;

public class SongManager {
    private SongSearcher songSearcher = new SongSearcher();
    private IMyTunesDataAccess songDAO;

    public SongManager() throws IOException {songDAO = new SongDAO_DB();}

    public List<Song> getAllSongs() throws Exception {
        return songDAO.getAllSongs();
    }
    public List<Song> searchSongs(String query) throws Exception {
        List<Song> allSongs = getAllSongs();
        List<Song> searchResult = songSearcher.search(allSongs, query);
        return searchResult;
    }
    public Song createNewSong(String title, String artist, String category, int seconds, String filePath) throws Exception {
        return songDAO.createSong(title, artist, category, seconds,filePath);
    }
    public void updateSong(Song updatedSong) throws Exception{
        songDAO.updateSong(updatedSong);
    }
}
