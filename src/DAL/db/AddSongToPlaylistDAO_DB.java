package DAL.db;

import BE.Song;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class AddSongToPlaylistDAO_DB{



    private DAL.db.MyDatabaseConnector databaseConnector;

    public AddSongToPlaylistDAO_DB()
    {
        databaseConnector = new DAL.db.MyDatabaseConnector();
    }


    public List<Song> getAllSongs(int playListNumber) throws Exception {
        ArrayList<Song> allSongs = new ArrayList<>();

        try (Connection conn = databaseConnector.getConnection();
             Statement stmt = conn.createStatement())
        {

            String sql = "SELECT * " +
                    "FROM dbo.Song " +
                    "where PlaylistID = ?;" +
                    "ORDER BY";



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




              }
        return null;
    }

}
