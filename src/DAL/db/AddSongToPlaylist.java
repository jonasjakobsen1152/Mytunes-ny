package DAL.db;

import BE.Song;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class AddSongToPlaylist  {

    private DAL.db.MyDatabaseConnector databaseConnector;

    public AddSongToPlaylist() {

        databaseConnector = new DAL.db.MyDatabaseConnector();
            }




    public List<Song> getAllSongs() throws Exception {

        ArrayList<Song> allSongs = new ArrayList<>();

        try (Connection conn = databaseConnector.getConnection();
             Statement stmt = conn.createStatement())
        {

            //so.Title, so.Artist, so.Category, so.Seconds, so.Filepath

         String sql = "Select * From PlaylistAndSongs ps, PlaylistInfo pl, Song so " +
                "Where pl.PlaylisteID=ps.PlaylisteID AND so.Id=ps.MusicID AND pl.PlaylisteID=;";






            ResultSet rs = stmt.executeQuery(sql);

            // Loop through rows from the database result set
            while (rs.next()) {

                //Map DB row to Song object
                int id = rs.getInt("Id");
                String title = rs.getString("Title");
                String artist = rs.getString("Artist");
                String category = rs.getString("Category");
                int seconds = rs.getInt("Seconds");
                String filePath = rs.getString("FilePath");

                Song song = new Song(id, title, artist, category, seconds, filePath);

                allSongs.add(song);

            }
            System.out.println(allSongs);
            return allSongs;

        }
        catch (SQLException ex)
        {
            ex.printStackTrace();
            throw new Exception("Could not get Songs from database", ex);
        }

    }


    public Song createSong(String title, String artist, String category, String filePath) throws Exception {
        return null;
    }


    public void updateSong(Song song) throws Exception {

    }


    public void deleteSong(Song song) throws Exception {

    }
}







