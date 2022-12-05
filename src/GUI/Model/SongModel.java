package GUI.Model;

import BE.Song;
import BLL.SongManager;
import BLL.util.MusicSound;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;

public class SongModel {
    private ObservableList<Song> songsToBeViewed;
    private SongManager songManager;
    private Song selectedSong;

    public SongModel() throws Exception {
        songManager = new SongManager();
        songsToBeViewed = FXCollections.observableArrayList();


        songsToBeViewed.addAll(songManager.getAllSongs());
    }

    public ObservableList<Song> getObservableSong() {
        return songsToBeViewed;
    }

    public void searchSong(String query) throws Exception {
        List<Song> searchResults = songManager.searchSongs(query);
        showList();
    }

    public void createNewSong(String title, String artist, String category, String filePath) throws Exception {
        // Create Song in data storage
        songManager.createNewSong(title, artist, category, filePath);

        //Updates the list when new song created.
        showList();
    }

    public void updateSong(Song updateSong) throws Exception {
        // Call BLL
        // update Song in DB
        songManager.updateSong(updateSong);

        // update ListView
        showList();
    }

    public void deleteSong(Song deletedSong) throws Exception {
        songManager.deleteSong(deletedSong);
        showList();
    }
    public Song getSelectedSong() {

        return selectedSong;
    }

    public void setSelectedSong(Song selectedMovie) {
        this.selectedSong = selectedSong;
    }



    public void soundLevel(double volumen) throws Exception {
        MusicSound musicSound = new MusicSound();
        musicSound.soundVolume(volumen);
    }

    public void showList() throws Exception {
        //Update the listview
        songsToBeViewed.clear();
        songsToBeViewed.addAll(songManager.getAllSongs());
    }
}