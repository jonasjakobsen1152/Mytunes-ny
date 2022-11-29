package GUI.Controller;

import BE.Song;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SongViewController extends BaseController implements Initializable {
    
    public Button btnEditSong;
    public Button btnDeleteSong;
    public Button btnRestartSong;
    public Button btnSkipSong;
    public Button btnAddPlaylist;
    public Button btnEditPlaylist;
    public Button btnDeletePlaylist;
    public Button btnMovePlaylistSongUp;
    public Button btnMovePlaylistSongDown;
    public ListView lstSongsOnPlaylist;
    public ListView lstPlaylist;
    public ListView lstSongs;
    public Button addSongToPlaylist;
    public Slider sliMusicVolume;
    public Button btnAddSong;

    public void handleAddSong(ActionEvent actionEvent) {
    }

    public void handleEditSong(ActionEvent actionEvent) {
    }

    public void handleDeleteSong(ActionEvent actionEvent) {
    }

    public void handleRestart(ActionEvent actionEvent) {
    }

    public void handleSkipSong(ActionEvent actionEvent) {
    }

    public void handleAddPlaylist(ActionEvent actionEvent) {
    }

    public void handleAddSongToPlaylist(ActionEvent actionEvent) {
    }

    public void handleEditPlaylist(ActionEvent actionEvent) {
    }

    public void handleDeletePlaylist(ActionEvent actionEvent) {
    }

    public void handleMovePlaylistSongUp(ActionEvent actionEvent) {
    }

    public void handleMovePlaylistSongDown(ActionEvent actionEvent) {
    }

    public void handleSliMusicVolume(MouseEvent mouseEvent) {
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
    @Override
    public void setup() {
        //songModel = getModel().getSongModel();
    }
    public void changed(ObservableValue<? extends Song> observable, Song oldValue, Song newValue) {

    }
    private void displayError(Throwable t) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Something went wrong");
        alert.setHeaderText(t.getMessage());
        alert.showAndWait();
    }
    public void handleAddNewSong(ActionEvent actionEvent) {

    }
    public void handleEdit(ActionEvent actionEvent) throws IOException {

    }

}
