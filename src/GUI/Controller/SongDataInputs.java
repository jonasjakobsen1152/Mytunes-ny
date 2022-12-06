package GUI.Controller;

import BE.Song;
import GUI.Model.MYTModel;
import GUI.Model.SongModel;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class SongDataInputs {
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

    private MYTModel model = new MYTModel();

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
    public void setModel() {
        songModel = model.getSongModel();
    }

    public void setModel(MYTModel model) {
        this.songModel = model.getSongModel();
    }
}
