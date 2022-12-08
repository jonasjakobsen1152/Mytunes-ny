package BLL;

import BE.Song;
import BLL.util.SongSearcher;
import DAL.ISongDAO;
import DAL.db.SongDAO_DB;

import java.util.List;

public class SongManager {
    private SongSearcher songSearcher = new SongSearcher();
    private ISongDAO songDAO;

    public SongManager() throws Exception {songDAO = new SongDAO_DB();}
    public List<Song> getAllSongs() throws Exception {
        return songDAO.getAllSongs();

    }
    public List<Song> searchSongs(String query) throws Exception {
        List<Song> allSongs = getAllSongs();
        List<Song> searchResult = songSearcher.search(allSongs, query);
        return searchResult;
    }

    public Song createNewSong(String title, String artist, String category, String filePath) throws Exception {
        return songDAO.createSong(title, artist, category,filePath);
    }
    public void updateSong(Song updatedSong) throws Exception{
        songDAO.updateSong(updatedSong);
    }
    public void deleteSong(Song deletedSong) throws Exception {
        songDAO.deleteSong(deletedSong);
    }

}
