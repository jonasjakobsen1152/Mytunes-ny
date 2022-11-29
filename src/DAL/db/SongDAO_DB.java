package DAL.db;

import BE.Song;
import DAL.IMyTunesDataAccess;

import java.util.List;

public class SongDAO_DB implements IMyTunesDataAccess {

    private MyT.DAL.db.MyDatabaseConnector databaseConnector;

    // Return data structure
    public SongDAO_DB() {
        databaseConnector = new MyT.DAL.db.MyDatabaseConnector();
    }
    @Override
    public List<Song> getAllSongs() throws Exception {
        return null;
    }

    @Override
    public Song createSong(String title, String artist, String category, int seconds) throws Exception {
        return null;
    }

    @Override
    public void updateSong(Song movie) throws Exception {

    }

    @Override
    public void deleteSong(Song movie) throws Exception {

    }
}
