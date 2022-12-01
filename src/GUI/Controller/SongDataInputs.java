package GUI.Controller;

import GUI.Model.SongModel;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class SongDataInputs extends BaseController{
    public TextField txtEditTitle;
    public TextField txtEditArtist;
    public TextField txtEditCategory;
    public TextField txtEditSongFilePath;
    public Button btnEditSong;
    SongModel songModel;
    public TextField txtTitle;
    public TextField txtArtist;
    public TextField txtCategory;
    public TextField txtFilePath;
    public Button txtAddInput;

    public void handleAddInput(ActionEvent actionEvent) throws Exception {
        String Title = txtTitle.getText();
        String Artist = txtArtist.getText();
        String Category = txtCategory.getText();
        String FilePath = txtFilePath.getText();
        try{
            songModel = new SongModel();
            songModel.createNewSong(Title, Artist, Category, FilePath);
            Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
            stage.close();
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

    public void handleEditSong(ActionEvent actionEvent) {
    }
}
