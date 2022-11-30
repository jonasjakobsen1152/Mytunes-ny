package GUI.Model;

import BE.Song;
import BLL.SongManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;


import java.util.List;

public class SongModel {
    private ObservableList<Song> songsToBeViewed;
    private SongManager songManager;
    private Song selectedSong;

    public SongModel() throws Exception{
        songManager = new SongManager();
        songsToBeViewed = FXCollections.observableArrayList();
        songsToBeViewed.addAll(songManager.getAllSongs());
    }

    public ObservableList<Song> getObservableSong() {
        return songsToBeViewed;
    }
    public void searchSong(String query) throws Exception{
        List<Song> searchResults = songManager.searchSongs(query);
        songsToBeViewed.clear();
        songsToBeViewed.addAll(searchResults);
    }
    public void createNewSong(String title, String artist, String category, int seconds, String filePath) throws Exception{
        // Create Song in data storage
        Song s = songManager.createNewSong(title, artist, category, seconds,filePath);

        // Add Song to observable list (gui)
        songsToBeViewed.add(s);
    }

    public void updateSong(Song updateSong) throws Exception {
        // Call BLL
        // update Song in DB
        songManager.updateSong(updateSong);

        // update ListView
        songsToBeViewed.clear();
        songsToBeViewed.addAll(songManager.getAllSongs());
    }

    public Song getSelectedSong() {
        return selectedSong;
    }

    public void setSelectedSong(Song selectedMovie) {
        this.selectedSong = selectedSong;
    }
}
