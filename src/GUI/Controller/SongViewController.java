package GUI.Controller;

import BE.Song;
import BLL.util.MusicSound;
import GUI.Model.SongModel;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ResourceBundle;

public class SongViewController extends BaseController implements Initializable {
    public TextField txtFilter;
    public Button btnEditSong,btnDeleteSong,btnRestartSong,btnSkipSong,btnAddPlaylist,btnEditPlaylist,btnDeletePlaylist,
            btnMovePlaylistSongUp,btnMovePlaylistSongDown,addSongToPlaylist,btnAddSong,btnSearch,btnPlaySong;

    public ListView lstSongsOnPlaylist,lstPlaylist;

    public ListView<Song> lstSongs;

    public Slider sliMusicVolume;


    private SongModel songModel;


    private boolean songIsPlayed=false; //used to stop songs from playing in case that no song is marked

    Song previousSong;

    private String errorText;

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

        MusicSound musicSound = new MusicSound();
        sliMusicVolume.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                musicSound.soundVolume(sliMusicVolume.getValue());
            }
        });
    }

    public SongViewController() {
        try {
            // Istatiates a songModel inside a try catch.
            songModel = new SongModel();
        } catch (Exception e) {
            displayError(e);
            e.printStackTrace();
        }


    }


    public void handleAddSong(ActionEvent actionEvent) throws Exception {

        if (songIsPlayed) //Stopper afspilning af musik, hvis noget skal ændres
        {
            MusicSound musicSound = new MusicSound();
            musicSound.stopMusic();
        }


        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/GUI/View/addNewSong.fxml"));
        AnchorPane pane = (AnchorPane) loader.load();

        SongDataInputs songdatainputs = loader.getController();
        songdatainputs.setModel(super.getModel());
        //showAllSongsAndPlaylists();
        //songCrud.setup();


        Stage dialogWindow = new Stage();
        dialogWindow.setTitle("Add Song");
        dialogWindow.initModality(Modality.WINDOW_MODAL);
        dialogWindow.initOwner(((Node)actionEvent.getSource()).getScene().getWindow());
        Scene scene = new Scene(pane);
        dialogWindow.setScene(scene);

        dialogWindow.showAndWait();
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


    public void handleEditSong(ActionEvent actionEvent) throws Exception {

        if (songIsPlayed) //Stopper afspilning af musik, hvis noget skal ændres
        {
            MusicSound musicSound = new MusicSound();
            musicSound.stopMusic();
        }

        try {
           Song selectedSong = lstSongs.getSelectionModel().getSelectedItem();

           if(selectedSong != null) {
               FXMLLoader fxmlLoader = new FXMLLoader();
               fxmlLoader.setLocation(getClass().getResource("/GUI/View/editSong.fxml"));

               Scene scene = new Scene(fxmlLoader.load(), 600, 400);
               Stage stage = new Stage();
               stage.setTitle("Edit the song");
               stage.initModality(Modality.WINDOW_MODAL);
               stage.initOwner(((Node) actionEvent.getSource()).getScene().getWindow());
               stage.setScene(scene);
               SongDataInputs songDataInputs = fxmlLoader.getController();
               songDataInputs.setSelectSong(selectedSong);
               stage.showAndWait();
           }
           else {
               alertUser("Please select a song to edit");
           }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void handleDeleteSong(ActionEvent actionEvent) throws Exception {
        if (songIsPlayed) //Stopper afspilning af musik, hvis noget skal ændres
        {
            MusicSound musicSound = new MusicSound();
            musicSound.stopMusic();
        }

        Song selectedSong = lstSongs.getSelectionModel().getSelectedItem();
        if(selectedSong != null){

        }
        else{
            alertUser("Please select the song you wish to delete");
        }
    }

    public void handleSliMusicVolume(MouseEvent mouseEvent) throws Exception {

    }

    public void handleRestart() throws Exception {

        lstSongs.getSelectionModel().selectPrevious();

        if (songIsPlayed) //Stopper afspilning af musik, hvis noget skal ændres
        {
            handlePlaySong(); //Stopper den sang, som er i gang

        }

    }

    public void handleSkipSong(ActionEvent actionEvent) throws Exception {

        lstSongs.getSelectionModel().selectNext();

        if (songIsPlayed) //Stopper afspilning af musik, hvis noget skal ændres
        {
            handlePlaySong(); //Stopper den sang, som er i gang

        }
    }


    public void handleAddPlaylist(ActionEvent actionEvent) {


        String inputValue =JOptionPane.showInputDialog("Please insert playlist name "); // Her er den dovne mulighed
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




    public void handleSearchAllSongs(ActionEvent actionEvent) {
    }

    public void handlePlaySong() throws Exception {


                    String path = null;
                    boolean startSong = true;
                    MusicSound musicSound = new MusicSound();




        if (songIsPlayed) //Denne if statement sikre,at man kan stoppe musikken selvom den ikke er markeret.
                    {
                        musicSound.stopMusic(); //Stop music
                        songIsPlayed=false;

                        if (lstSongs.getSelectionModel().getSelectedItem()==previousSong) //Hvis brugeren ikke har valgt en anden sang. Så stopper musikken.
                            startSong=false;

                    }


        if (lstSongs.getSelectionModel().getSelectedItem()!=null && startSong) //Man skal kun kunne starte musik, hvis den er markeret.
                    {
                        Song selectedSong = lstSongs.getSelectionModel().getSelectedItem();
                        previousSong=selectedSong;          //Gemmer nuværende sang, så vi kan se om sangen har skiftet.

                        path=selectedSong.getFilePath();


                        boolean filesExits= Files.exists(Path.of(path)); //check om filen eksisterer

                        if (filesExits)
                        {
                            musicSound.playMusic(path);
                            songIsPlayed=true;
                        }
                        else
                           // JOptionPane.showMessageDialog(null,"File do not exist!");
                            informationUser("File do not exist!");
                     }


    }

    private void informationUser(String information){
        Alert info = new Alert(Alert.AlertType.INFORMATION);
        info.setTitle("Regarding music");
        info.setHeaderText(information + "");
        info.showAndWait();
    }

    private void alertUser(String error){
    Alert alert = new Alert(Alert.AlertType.ERROR);
    alert.setTitle(error);
    alert.setHeaderText(error + "");
    alert.showAndWait();
    }

    private void displayError(Throwable t) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(errorText);
        alert.setHeaderText(t.getMessage());
        alert.showAndWait();
    }
    public void handleEdit(ActionEvent actionEvent) throws IOException {

    }

    public void handlePauseMusic(ActionEvent actionEvent) {
    }
}
