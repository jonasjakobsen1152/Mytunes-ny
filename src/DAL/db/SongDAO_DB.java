package DAL.db;

import BE.Song;
import BLL.util.MusicSound;
import DAL.IMyTunesDataAccess;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class SongDAO_DB implements IMyTunesDataAccess {

    private DAL.db.MyDatabaseConnector databaseConnector;
    public SongDAO_DB() throws IOException {
        databaseConnector = new DAL.db.MyDatabaseConnector();
    }
    @Override
    public List<Song> getAllSongs() throws Exception {
        ArrayList<Song> allSongs = new ArrayList<>();

        try (Connection conn = databaseConnector.getConnection();
             Statement stmt = conn.createStatement())
        {
            String sql = "SELECT * FROM dbo.Song;";


            ResultSet rs = stmt.executeQuery(sql);

            // Loop through rows from the database result set
            while (rs.next()) {

                //Map DB row to Movie object
                int id = rs.getInt("Id");
                String title = rs.getString("Title");
                String artist = rs.getString("Artist");
                String category = rs.getString("Category");
                int seconds = rs.getInt("Seconds");
                String filePath = rs.getString("FilePath");

                Song song = new Song(id, title, artist, category, seconds, filePath);

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
    public Song createSong(String title, String artist, String category, String filePath) throws Exception {

        String sql = "INSERT INTO song (Title,Artist,Category,Seconds,FilePath) VALUES (?,?,?,?,?);";

        try (Connection conn = databaseConnector.getConnection()){
            PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            // Bind parameters
            stmt.setString(1, title);
            stmt.setString(2,artist);
            stmt.setString(3, category);

            //Her kalder vi musik klassen
            MusicSound musicSound= new MusicSound();
            int seconds =musicSound.timeMusic(filePath);

            stmt.setInt(4,seconds); //I musikklassen kaldes timeMusik som sender tiden i sekunder for nummeret tilbage.
            stmt.setString(5,filePath);

            // Run the specified SQL statement
            stmt.executeUpdate();

            // Get the generated ID from the DB
            ResultSet rs = stmt.getGeneratedKeys();
            int id = 0;

            if ( rs.next()) {
                id = rs.getInt(1);
            }

            // Create Song object and send up the layers
            Song song = new Song(id, title, artist, category, seconds, filePath);
            return song;
        }
        catch (SQLException ex){
            ex.printStackTrace();
            throw new Exception("Could not create song", ex);
        }
    }

    @Override
    public void updateSong(Song song) throws Exception {

        String sql = "UPDATE song SET Title = ?, Artist = ?, Category = ?, Seconds = ? FilePath=? WHERE ID = ?";

        try (Connection conn = databaseConnector.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(sql);

            // Bind parameters
            stmt.setString(1, song.getTitle());
            stmt.setString(2, song.getArtist());
            stmt.setString(3, song.getCategory());


            String filePath= song.getFilePath(); //Her gemmes sti navnet til sangen

            MusicSound musicSound= new MusicSound();
            int seconds =musicSound.timeMusic(filePath);  //Her måles spilletiden.

            stmt.setInt(4,seconds); //I musikklassen kaldes timeMusic som sender tiden i sekunder for nummeret tilbage.
            stmt.setString(5, song.getFilePath());
            


            stmt.executeUpdate();
        }
        catch (SQLException ex) {
            ex.printStackTrace();
            throw new Exception("Could not update song", ex);
        }
    }

    @Override
    public void deleteSong(Song song) throws Exception  {
        try(Connection conn = databaseConnector.getConnection()) {


            String sql= "Delete song  WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);


            //Bind parameters

            stmt.setInt(1, song.getId());


            stmt.executeUpdate();


        }
        catch (SQLException ex){
            ex.printStackTrace();
            throw new Exception("Could not delete song", ex);

        }
    }

}
