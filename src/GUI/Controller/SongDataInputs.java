package GUI.Controller;

import BE.Song;
import GUI.Model.SongModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.awt.*;

public class SongDataInputs extends BaseController{
    SongModel songModel;
    public TextField txtTitle;
    public TextField txtArtist;
    public TextField txtCategory;
    public TextField txtFilePath;
    public Button txtAddInput;

    public void handleAddInput(ActionEvent actionEvent) throws Exception {
        String updatedTitle = txtTitle.getText();
        String updatedArtist = txtArtist.getText();
        String updatedCategory = txtCategory.getText();
        int updatedSeconds = -1;
        String updatedFilePath = txtFilePath.getText();
        try{
            songModel = new SongModel();
            songModel.createNewSong(updatedTitle, updatedArtist, updatedCategory, updatedSeconds,updatedFilePath);
        } catch (Exception e) {
            e.printStackTrace();
        }


        //TODO add method that calculates seconds


    }




    /*
    public void handleUpdate(ActionEvent actionEvent) throws Exception{
        String updatedTitle = txtTitle.getText();
        String updatedArtist = txtArtist.getText();
        String updatedCategory = txtCategory.getText();
        int updatedSeconds = Integer.parseInt(txtSeconds.getText());
        //Song updatedSong = new Song(model.getSelectedSong().getId(),
                updatedTitle, updatedArtist, updatedCategory, updatedSeconds);

        model.updateSong(updatedSong);

        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        stage.close();
    }
    */

   // @Override
    public void setup() {
        //model = getModel().getSongModel();
        /*
        txtTitle.setText(model.getSelectedSong().getTitle());
        txtArtist.setText(model.getSelectedSong().getArtist());
        txtCategory.setText(model.getSelectedSong().getCategory());
        txtSeconds.setText(String.valueOf(model.getSelectedSong().getSeconds()));
         */
    }
}
