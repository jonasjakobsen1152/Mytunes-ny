package GUI.Controller;

import BE.Playlist;
import GUI.Model.PlaylistModel;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class PlaylistDataInputs {
    public TextField txtName;
    public TextField txtEditName;
    public Button txtAddInput;
    public Button btnEditPlaylist;
    private Playlist selectPlaylist;
    private PlaylistModel playlistModel = new PlaylistModel();

    public PlaylistDataInputs() throws Exception {
    }

    public void handleAddInput(ActionEvent actionEvent) throws Exception {

        String Name = txtName.getText();
        try{
            playlistModel.createNewPlaylist(Name);
            Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
            stage.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void setSelectPlaylist(Playlist p) {
        selectPlaylist = p;

        // Here we get the text into the inputs
        txtName.setText(selectPlaylist.getName());
    }
    public void handleEditPlaylist(ActionEvent actionEvent) throws Exception {
        int id = selectPlaylist.getId();
        String updatedName = txtName.getText();
        playlistModel.updatePlaylist(new Playlist(id, updatedName));

        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        stage.close();
    }
}
