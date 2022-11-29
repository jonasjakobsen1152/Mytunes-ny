package DAL.db;

import BE.Song;
import DAL.IMyTunesDataAccess;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class SongDAO_DB implements IMyTunesDataAccess {

    private DAL.db.MyDatabaseConnector databaseConnector;

    // Return data structure
    public SongDAO_DB() {
        databaseConnector = new DAL.db.MyDatabaseConnector();
    }
    @Override
    public List<Song> getAllSongs() throws Exception {
        ArrayList<Song> allSongs = new ArrayList<>();

        try (Connection conn = databaseConnector.getConnection();
             Statement stmt = conn.createStatement())
        {
            String sql = "SELECT * FROM Song;";


            ResultSet rs = stmt.executeQuery(sql);

            // Loop through rows from the database result set
            while (rs.next()) {

                //Map DB row to Movie object
                int id = rs.getInt("Id");
                String title = rs.getString("Title");
                String artist = rs.getString("artist");
                String category = rs.getString("category");
                int seconds = rs.getInt("seconds");

                Song song = new Song(id, title, artist, category, seconds);
                allSongs.add(song);
            }
            return allSongs;

        }
        catch (SQLException ex)
        {
            ex.printStackTrace();
            throw new Exception("Could not get Songs from database", ex);
        }
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
