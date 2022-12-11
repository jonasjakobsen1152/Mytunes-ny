package DAL.db;

import BE.Playlist;
import BE.Song;

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

        String sql= "DELETE PlaylistAndSongs FROM PlaylistAndSongs inner join song on Song.Id=PlaylistAndSongs.ID " +
                "WHERE PlaylistAndSongs.ID=? DELETE from Song WHERE Song.Id=?;";

        try(Connection conn = databaseConnector.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(sql);

            //Bind parameters

            int playlistId = selectedPlaylist.getId();
            int songId = selectedSong.getId();

            stmt.setInt(1, playlistId);
            stmt.setInt(2, songId);

            stmt.executeUpdate();
        }
        catch (SQLException ex){
            ex.printStackTrace();
            throw new Exception("Could not delete song", ex);

        }
    }
    }

