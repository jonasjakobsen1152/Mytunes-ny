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


    public void deleteSongFromPlaylist(Song selectedSong, Playlist selectedPlaylist, int selectedRank) throws Exception {



        String sql= "Delete from PlaylistAndSongs \n" +
                "WHERE PlaylistAndSongs.MusicID = ? \n" +
                "and PlaylistAndSongs.PlaylisteID = ?\n" +
                "and PlaylistAndSongs.Rank = ?";


        try (Connection conn = databaseConnector.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(sql);

            //Bind parameters

            int playlistId = selectedPlaylist.getId();
            int songId = selectedSong.getId();

            stmt.setInt(1, songId);
            stmt.setInt(2, playlistId);
            stmt.setInt(3,selectedRank);


            stmt.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new Exception("Could not delete song", ex);

        }
    }

    public int getRank(int songNumber,int playlisteID) throws SQLServerException {

        //int songNumber=song.getId();
        int rank = 0;

        String sql = "SELECT * FROM PlaylistAndSongs P WHERE P.MusicID=" + songNumber + " AND " + "P.playlisteID=" + playlisteID +";";

        try (Connection conn = databaseConnector.getConnection();
             Statement stmt = conn.createStatement()) {

            ResultSet rs = stmt.executeQuery(sql);


            // Loop through rows from the database result set
            while (rs.next()) {

                rank = rs.getInt("Rank");

            }


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

                return rank;



    }


        public void updateSongAndPlaylist(int rank2,  int  musicID1 ,int playlisteID1, int rank1 )
        {

          String  sql =    "UPDATE PlaylistAndSongs set Rank = ? WHERE  MusicID= ? AND PlaylisteID = ? AND Rank = ?";

            try (Connection conn = databaseConnector.getConnection()) {
                PreparedStatement stmt = conn.prepareStatement(sql);

                // Bind parameters
                stmt.setInt(1, rank2); //PlaylisteID First
                stmt.setInt(2, musicID1);//Rank First
                stmt.setInt(3, playlisteID1); //MusicID First
                stmt.setInt(4, rank1);//MusicID second



                stmt.executeUpdate();

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }



        public void swap(int number1, int number2, int playlistID) throws SQLServerException {

            int rank1=getRank(number1,playlistID);
            int rank2=getRank(number2,playlistID);

            updateSongAndPlaylist(rank2, number1, playlistID, rank1);
            updateSongAndPlaylist(rank1, number2, playlistID, rank2);


        }






}