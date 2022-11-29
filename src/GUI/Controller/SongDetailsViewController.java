package GUI.Controller;

import BE.Song;
import GUI.Model.SongModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.stage.Stage;

import java.awt.*;

public class SongDetailsViewController extends BaseController{
    @FXML
    private TextField txtTitle, txtArtist, txtCategory, txtSeconds;
    private SongModel model;

    public void handleUpdate(ActionEvent actionEvent) throws Exception{
        String updatedTitle = txtTitle.getText();
        String updatedArtist = txtArtist.getText();
        String updatedCategory = txtCategory.getText();
        int updatedSeconds = Integer.parseInt(txtSeconds.getText());
        Song updatedSong = new Song(model.getSelectedSong().getId(),
                updatedTitle, updatedArtist, updatedCategory, updatedSeconds);

        model.updateSong(updatedSong);

        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        stage.close();
    }
    @Override
    public void setup() {

        model = getModel().getSongModel();

        txtTitle.setText(model.getSelectedSong().getTitle());
        txtArtist.setText(model.getSelectedSong().getArtist());
        txtCategory.setText(model.getSelectedSong().getCategory());
        txtSeconds.setText(String.valueOf(model.getSelectedSong().getSeconds()));
    }
}
