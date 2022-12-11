package DAL.db;

import BE.Playlist;
import BE.Song;
import com.microsoft.sqlserver.jdbc.SQLServerException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SongToPlaylistDAO_DB {


    private DAL.db.MyDatabaseConnector databaseConnector;


    public SongToPlaylistDAO_DB() throws SQLException {
        databaseConnector = new DAL.db.MyDatabaseConnector();

    }

    public List<Song> getAllSongsFromPlaylist(int playlisteID) throws Exception {
        ArrayList<Song> allSongs = new ArrayList<>();


        try (Connection conn = databaseConnector.getConnection();
             Statement stmt = conn.createStatement()) {

            String sql = "SELECT *" +
                    "FROM PlaylistInfo PI, [PlaylistAndSongs] P, Song S " +
                    "WHERE S.Id = P.MusicID and PI.PlaylisteID = P.PlaylisteID and P.PlaylisteID=" + playlisteID +
                    "ORDER BY P.Rank;";


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


            return allSongs;

        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new Exception("Could not get Songs from database", ex);
        }
    }


    public void addSongToPlaylist(Song selectedSong, Playlist selectedPlaylist, int playlistSize) throws SQLException {
        String sql = "INSERT INTO PlaylistAndSongs (MusicID, PlaylisteID, Rank) VALUES (?,?,?);";

        try (Connection conn = databaseConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            int playlistId = selectedPlaylist.getId();
            int songId = selectedSong.getId();
            int nextSongRank = playlistSize + 1;

            stmt.setInt(1, songId);
            stmt.setInt(2, playlistId);
            stmt.setInt(3, nextSongRank);
            stmt.executeUpdate();

        }
    }


    public void deleteSongFromPlaylist(Song selectedSong, Playlist selectedPlaylist) throws Exception {

        String sql = "DELETE From PlaylistAndSongs where PlaylistAndSongs.MusicID=? and PlaylistAndSongs.ID=?";

        try (Connection conn = databaseConnector.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(sql);

            //Bind parameters

            int playlistId = selectedPlaylist.getId();
            int songId = selectedSong.getId();

            stmt.setInt(1, songId);
            stmt.setInt(2, playlistId);


            stmt.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new Exception("Could not delete song", ex);

        }
    }

    public void swapRows(int first, int second) throws SQLServerException {

        int[][] storedData = new int[2][4];


        String sql = "SELECT * FROM PlaylistAndSongs P WHERE P.MusicID=" + second + " or P.MusicID=" + first + ";";

        try (Connection conn = databaseConnector.getConnection();
             Statement stmt = conn.createStatement()) {

            ResultSet rs = stmt.executeQuery(sql);

            int number = 0;
            int counter = 0;
            // Loop through rows from the database result set
            while (rs.next()) {

                storedData[number][0] = rs.getInt("MusicID");
                storedData[number][1] = rs.getInt("PlaylisteID");
                storedData[number][2] = rs.getInt("Rank");
                storedData[number][3] = rs.getInt("Rank");
                System.out.println(counter);

                if (counter == 1)
                    number = 1;

            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


                sql =    "UPDATE PlaylistAndSongs SET MusicID = ?, PlaylisteID = ?, Rank = ? WHERE ID = ? " +
                        "UPDATE PlaylistAndSongs SET MusicID = ?, PlaylisteID = ?, Rank = ? WHERE ID = ?";

        try (Connection conn = databaseConnector.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(sql);

            // Bind parameters
            stmt.setInt(1, storedData[0][1]); //MusicID
            stmt.setInt(2, storedData[0][2]); //PlaylisteID
            stmt.setInt(3, storedData[0][3]);//Rank
            stmt.setInt(4, storedData[1][0]);//Rank
            stmt.setInt(5, storedData[1][1]);//MusicID
            stmt.setInt(6, storedData[1][2]);
            stmt.setInt(7, storedData[1][3]);
            stmt.setInt(8, storedData[0][0]);
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

}