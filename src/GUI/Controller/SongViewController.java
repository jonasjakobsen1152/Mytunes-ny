package GUI.Controller;

import BE.Song;
import GUI.Model.SongModel;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.Node;


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
    public ListView lstSongs;
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
          stage.show();
      } catch (IOException e) {
          e.printStackTrace();
      }
      /*
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/GUI/View/addNewSong.fxml"));
        AnchorPane pane = (AnchorPane) loader.load();

        SongAddNewController controller = loader.getController();
        controller.setModel(super.getModel());
        controller.setup();


        // Create the dialog stage
        Stage dialogWindow = new Stage();
        dialogWindow.setTitle("Add New Song");
        dialogWindow.initModality(Modality.WINDOW_MODAL);
        dialogWindow.initOwner(((Node)actionEvent.getSource()).getScene().getWindow());
        Scene scene = new Scene(pane);
        dialogWindow.setScene(scene);

        //Show the dialog and wait ultil the user closes it
        dialogWindow.show();
       */
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
        //songModel = getModel().getSongModel();
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

    public void handleSearchAllSongs(ActionEvent actionEvent) {
    }

    public void handlePlaySong(ActionEvent actionEvent) {
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
