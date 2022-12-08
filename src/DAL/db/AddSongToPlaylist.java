package DAL.db;

import BE.Song;
import BLL.util.MusicSound;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AddSongToPlaylist  {

    private DAL.db.MyDatabaseConnector databaseConnector;

    public AddSongToPlaylist() {

        databaseConnector = new DAL.db.MyDatabaseConnector();
            }




    public List<Song> getAllSongs(int playlistID) throws Exception {

        ArrayList<Song> allSongs = new ArrayList<>();



        String sql = "Select * From PlaylistAndSongs ps, PlaylistInfo pl, Song so " +
                "Where pl.PlaylisteID=ps.PlaylisteID AND so.Id=ps.MusicID AND pl.PlaylisteID="+playlistID+";";

        try (Connection conn = databaseConnector.getConnection();
             Statement stmt = conn.createStatement())
        {

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

    }
























