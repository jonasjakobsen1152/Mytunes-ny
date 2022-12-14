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

            //sql koden henter fra vores tre tabeller, hvor musik id i krydstabellen og song skal være ens
            // og playliste nummer skal være ens i playliste og i krydstabellen.
            // Vi sortere på sangenes rank.



            ResultSet rs = stmt.executeQuery(sql);  //Her vises resultat settet

            // Loop through rows from the database result set
            while (rs.next()) {

                //Map DB row to Song object
                int id = rs.getInt("Id");
                String title = rs.getString("Title");
                String artist = rs.getString("Artist");
                String category = rs.getString("Category");
                int seconds = rs.getInt("Seconds");
                String filePath = rs.getString("FilePath");


                Song song = new Song(id, title, artist, category, seconds, filePath); //De hentede data gemmes i sang objekter

                allSongs.add(song); //Sang objekterne tilføjes en arrayList
            }


            return allSongs; //Arraylisten sendes hele vejen op og vises  til sidst i vores gui.

        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new Exception("Could not get Songs from database", ex);
        }
    }


    public void addSongToPlaylist(Song selectedSong, Playlist selectedPlaylist, int playlistSize) throws SQLException {

        String sql = "INSERT INTO PlaylistAndSongs (MusicID, PlaylisteID, Rank) VALUES (?,?,?);";

        //Metoden hvor man bruger en preparedStatement sikre mod SQL injection. Således at man ikke kan ødelægge databasen.
        // Ved spørgsmåltegnene indsættes data her tal. Data er tilføjet metoden og indsættes i setInt nedenunder.



        try (Connection conn = databaseConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            int playlistId = selectedPlaylist.getId();
            int songId = selectedSong.getId();
            int nextSongRank = playlistSize + 1;

            stmt.setInt(1, songId);
            stmt.setInt(2, playlistId);
            stmt.setInt(3, nextSongRank);
            stmt.executeUpdate();       //Her opdateres databasen.

        }
    }


    public void deleteSongFromPlaylist(Song selectedSong, Playlist selectedPlaylist, int selectedRank) throws Exception {



        String sql= "Delete from PlaylistAndSongs \n" +
                "WHERE PlaylistAndSongs.MusicID = ? \n" +
                "and PlaylistAndSongs.PlaylisteID = ?\n" +
                "and PlaylistAndSongs.Rank = ?";

// Her slettes en linje i vores krydstabel. Hvor musikID og playlistID og rank er defineret.



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

    public int getRank(int songID, int playlistID) throws SQLServerException {

        //int songNumber=song.getId();
        int rank = 0;

        String sql = "SELECT * FROM PlaylistAndSongs P WHERE P.MusicID=" + songID + " AND " + "P.playlisteID=" + playlistID +";";

        //Denne metode sender et heltal retur fra krydstabellen hvor musikID og playlistID er givet.
        // Vi har valgt at bruge variabler i vores sql streng. Da vi ikke skriver til databasen er der ikke fare for SQL exploit injection.




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

          //Metoden bruges til at opdaterer rank på linjen med sange og playlister i krydstabellen.


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

        //Metoden bruges at bytte rank mellem to sange. Metoden får to sang numre og playlist nummeret.


            int rank1=getRank(number1,playlistID);  //Først slås sangenes rank op.
            int rank2=getRank(number2,playlistID);


            updateSongAndPlaylist(rank2, number1, playlistID, rank1); //Derefter updateres i krydstabellen den første sang og playlist,
            updateSongAndPlaylist(rank1, number2, playlistID, rank2);  //men med den andens rank. Det gøres to gange.
        }
}