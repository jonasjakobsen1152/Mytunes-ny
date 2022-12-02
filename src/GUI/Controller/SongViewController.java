package GUI.Controller;

import BE.Song;
import GUI.Model.SongModel;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SongViewController extends BaseController implements Initializable {
    public TextField txtFilter;
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
    public ListView<Song> lstSongs;
    public Button addSongToPlaylist;
    public Slider sliMusicVolume;
    public Button btnAddSong;
    public Button btnSearch;
    public Button btnPlaySong;
    private SongModel songModel;

    public SongViewController() {
        try {
            // Istatiates a songModel inside a try catch.
            songModel = new SongModel();
        } catch (Exception e) {
            displayError(e);
            e.printStackTrace();
        }


    }


    public void handleAddSong(ActionEvent actionEvent) throws IOException {
      try {
          FXMLLoader fxmlLoader = new FXMLLoader();
          fxmlLoader.setLocation(getClass().getResource("/GUI/View/addNewSong.fxml"));

          Scene scene = new Scene(fxmlLoader.load(), 600, 400);
          Stage stage = new Stage();
          stage.setTitle("Add that new song");
          stage.setScene(scene);
          stage.showAndWait();
      } catch (IOException e) {
          e.printStackTrace();
      }
    }
        @Override
        public void initialize (URL url, ResourceBundle resourceBundle){
        lstSongs.setItems(songModel.getObservableSong());
        txtFilter.textProperty().addListener(((observable, oldValue, newValue) -> {
            try{
                songModel.searchSong(newValue);
            } catch (Exception e) {
                displayError(e);
                e.printStackTrace();
            }
        }));
        }

    public void changed(ObservableValue<? extends Song> observable, Song oldValue, Song newValue) {
    if(newValue != null){
        //TODO When new window created, implement this to edit/update songs
    }
    }


   @Override
    public void setup() {
        songModel = getModel().getSongModel();
    }


    public void handleEditSong(ActionEvent actionEvent) {
        try {
           Song selectedSong = lstSongs.getSelectionModel().getSelectedItem();

            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/GUI/View/editSong.fxml"));

            Scene scene = new Scene(fxmlLoader.load(), 600, 400);
            Stage stage = new Stage();
            stage.setTitle("Edit the song");
            stage.setScene(scene);
            SongDataInputs songDataInputs = fxmlLoader.getController();
            songDataInputs.setSelectSong(selectedSong);
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
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

    public void handleSearchAllSongs(ActionEvent actionEvent) {
    }

    public void handlePlaySong(ActionEvent actionEvent) throws Exception {
        Song selectedSong = lstSongs.getSelectionModel().getSelectedItem();
        String path=selectedSong.getFilePath();

        SongModel songModel1 = new SongModel();
        songModel1.playSong(path);



        //Song selectedSong = lstSongs.getSelectionModel().getSelectedItem();


    }
    private void displayError(Throwable t) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("You did something wrong, the program did not");
        alert.setHeaderText(t.getMessage());
        alert.showAndWait();
    }
    public void handleEdit(ActionEvent actionEvent) throws IOException {

    }
}
