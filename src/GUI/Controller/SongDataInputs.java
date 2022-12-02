package GUI.Controller;

import BE.Song;
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
    private SongModel songModel = new SongModel();
    public TextField txtTitle;
    public TextField txtArtist;
    public TextField txtCategory;
    public TextField txtFilePath;
    public Button txtAddInput;
    private Song selectSong;

    public SongDataInputs() throws Exception {
    }


    public void handleAddInput(ActionEvent actionEvent) throws Exception {

        String Title = txtTitle.getText();
        String Artist = txtArtist.getText();
        String Category = txtCategory.getText();
        String FilePath = txtFilePath.getText();
        try{
            songModel.createNewSong(Title, Artist, Category, FilePath);
            Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
            stage.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

   public void setSelectSong(Song s) {
        selectSong = s;

        // Here we get the text into the inputs
        txtEditTitle.setText(selectSong.getTitle());
        txtEditArtist.setText(selectSong.getArtist());
        txtEditCategory.setText(selectSong.getCategory());
        txtEditSongFilePath.setText(selectSong.getFilePath());

    }


    public void handleEditSong(ActionEvent actionEvent) throws Exception {
        int id = selectSong.getId();
        String updatedTitle = txtEditTitle.getText();
        String updatedArtist = txtEditArtist.getText();
        String updatedCategory = txtEditCategory.getText();
        String updatedFilePath = txtEditSongFilePath.getText();
        songModel.updateSong(new Song(id, updatedTitle, updatedArtist, updatedCategory, selectSong.getSeconds(), updatedFilePath));

        //Koden her gør sådan at vinduet bliver lukket når man har trykket på Edit Song
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        stage.close();

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
