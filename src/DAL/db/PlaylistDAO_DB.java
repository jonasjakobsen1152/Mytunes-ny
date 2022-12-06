package DAL.db;

import BE.Playlist;
import DAL.IPlaylistDAO;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PlaylistDAO_DB implements IPlaylistDAO {
    private DAL.db.MyDatabaseConnector databaseConnector;
    public PlaylistDAO_DB() throws IOException {
        databaseConnector = new DAL.db.MyDatabaseConnector();
    }
    public List<Playlist> getAllPlaylist() throws Exception {
        ArrayList<Playlist> allPlaylists = new ArrayList<>();

        try (Connection conn = databaseConnector.getConnection();
             Statement stmt = conn.createStatement())
        {
            String sql = "SELECT * FROM dbo.Playlister;";


            ResultSet rs = stmt.executeQuery(sql);

            // Loop through rows from the database result set
            while (rs.next()) {

                //Map DB row to Playlist object
                int id = rs.getInt("PlaylistID");
                String name = rs.getString("Name");

                Playlist playlist = new Playlist(id, name);

                allPlaylists.add(playlist);
            }

            return allPlaylists;

        }
        catch (SQLException ex)
        {
            ex.printStackTrace();
            throw new Exception("Could not get Songs from database", ex);
        }
    }

    @Override
    public Playlist createPlaylist(String name) throws Exception {

        String sql = "INSERT INTO Playlister (Name) VALUES (?);";

        try (Connection conn = databaseConnector.getConnection()){
            PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            // Bind parameters
            stmt.setString(1, name);

            // Run the specified SQL statement
            stmt.executeUpdate();

            // Get the generated ID from the DB
            ResultSet rs = stmt.getGeneratedKeys();
            int id = 0;

            if ( rs.next()) {
                id = rs.getInt(1);
            }

            // Create Playlist object and send up the layers
            Playlist playlist = new Playlist(id, name);
            return playlist;
        }
        catch (SQLException ex){
            ex.printStackTrace();
            throw new Exception("Could not create Playlist", ex);
        }
    }

    @Override
    public void updatePlaylist(Playlist playlist) throws Exception {
        String sql = "UPDATE Playlister SET Name = ? WHERE PlaylistID = ?";

        try (Connection conn = databaseConnector.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(sql);

            // Bind parameters
            stmt.setString(1, playlist.getName());

            stmt.executeUpdate();
        }
        catch (SQLException ex) {
            ex.printStackTrace();
            throw new Exception("Could not update playlist", ex);
        }
    }

    @Override
    public void deletePlaylist(Playlist playlist) throws Exception {
        try(Connection conn = databaseConnector.getConnection()) {


            String sql= "Delete Playlister WHERE PlaylistID = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);


            //Bind parameters

            stmt.setInt(1, playlist.getId());


            stmt.executeUpdate();


        }
        catch (SQLException ex){
            ex.printStackTrace();
            throw new Exception("Could not delete song", ex);

        }
    }


}
