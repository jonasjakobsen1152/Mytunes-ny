package GUI.Controller;

import BE.Playlist;
import BE.Song;
import BLL.util.MusicSound;
import GUI.Model.MYTModel;
import GUI.Model.PlaylistModel;
import GUI.Model.SongModel;
import GUI.Model.SongToPlaylistModel;
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
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

public class SongViewController extends BaseController implements Initializable {
    public TextField txtFilter;
    public Button btnEditSong,btnDeleteSong,btnRestartSong,btnSkipSong,btnAddPlaylist,btnEditPlaylist,btnDeletePlaylist,
            btnMovePlaylistSongUp,btnMovePlaylistSongDown,addSongToPlaylist,btnAddSong,btnSearch,btnPlaySong;
    public ListView lstSongsOnPlaylist;
    public ListView<Playlist> lstPlaylist;
    public ListView<Song> lstSongs;
    public Slider sliMusicVolume;
    public Text txtShowSong;
    public Button btnDeleteSongFromPlaylist;
    private SongModel songModel;
    private MYTModel mytModel;
    private PlaylistModel playlistModel;
    private SongToPlaylistModel songToPlaylistModel;
    private boolean songIsPlayed = false; //used to stop songs from playing in case that no song is marked
    private boolean endOfPlayList=false;
    private boolean clickPlaylistNotMusicList;
    private boolean inPlaylister;
    public Playlist selectedPlaylist;
    public Song previousSong,selectedSong;
    private String errorText;
    private String songTitle;
    double soundLevel = 50;
    private MediaPlayer play;
    private Media hit;
    private Timer timer;
    private TimerTask task;
    private java.awt.event.MouseEvent mouseEvent;
private int playlistNumber;



    @Override
    public void initialize (URL url, ResourceBundle resourceBundle){
        lstSongs.setItems(songModel.getObservableSong());
        lstPlaylist.setItems(playlistModel.getObservablePlaylist());

    lstSongsOnPlaylist.setItems(songToPlaylistModel.getObservablePlaylist());
        


        txtFilter.textProperty().addListener(((observable, oldValue, newValue) -> {
            try{
                songModel.searchSong(newValue);
            } catch (Exception e) {
                displayError(e);
                e.printStackTrace();
            }
        }));



        lstPlaylist.setOnMouseClicked(event -> {

            selectedPlaylist = lstPlaylist.getSelectionModel().getSelectedItem();
            playlistNumber=selectedPlaylist.getId();
            inPlaylister=true;

            try {
                songToPlaylistModel.showList(playlistNumber); //Vælger playlisten der skal vises
            } catch (Exception e) {
                throw new RuntimeException(e);
            }   });



        lstSongsOnPlaylist.setOnMouseClicked(event -> {
            clickPlaylistNotMusicList=true;
            inPlaylister=false;

            if (event.getClickCount() == 2 ) //Ved dobbeltklik kan man starte musikken
            {
                try {
                    handlePlaySong();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }  });


                lstSongs.setOnMouseClicked(event -> {

                    clickPlaylistNotMusicList=false;
                    inPlaylister=false;

                    if (event.getClickCount() == 2 ) { //Ved dobbeltklik kan man starte musikken
                        try {
                            handlePlaySong();
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }

                    } });



        sliMusicVolume.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                soundVolume(sliMusicVolume.getValue());         //Lytter om lyden skal skues op.
            }
        });
    }
    public SongViewController() {
        try {
            // Instantiates a songModel inside a try catch.
            playlistModel = new PlaylistModel();
            songModel = new SongModel();
            mytModel = new MYTModel();
          songToPlaylistModel = new SongToPlaylistModel();



        } catch (Exception e) {
            displayError(e);
            e.printStackTrace();

        }
    }

    public void handleAddSong(ActionEvent actionEvent) throws Exception {

        if (songIsPlayed) //Stopper afspilning af musik, hvis noget skal ændres

                    stopMusic();


        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/GUI/View/addNewSong.fxml"));
        AnchorPane pane = (AnchorPane) loader.load();

        SongDataInputs songdatainputs = loader.getController();

        mytModel.setSongModel(super.getModel().getSongModel());
        //songDatainputs.setModel(super.getModel());
        //showAllSongsAndPlaylists();

        Stage dialogWindow = new Stage();
        dialogWindow.setTitle("Add Song");
        dialogWindow.initModality(Modality.WINDOW_MODAL);
        dialogWindow.initOwner(((Node)actionEvent.getSource()).getScene().getWindow());
        Scene scene = new Scene(pane);
        dialogWindow.setScene(scene);

        dialogWindow.showAndWait();
        updateSongModel();
    }

   @Override
    public void setup() {
        songModel = getModel().getSongModel();
    }
    public void handleEditSong(ActionEvent actionEvent) throws Exception {

        if (songIsPlayed) //Stopper afspilning af musik, hvis noget skal ændres


            stopMusic();

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
               updateSongModel();
           }
           else {
               alertUser("Please select a song to edit");
           }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void handleDeleteSong(ActionEvent actionEvent) throws Exception {
        MusicSound musicSound = new MusicSound();
        Song deletedSong = lstSongs.getSelectionModel().getSelectedItem();
        if(deletedSong == null){
            alertUser("Please select the song you wish to delete");
        }
        else{
            if(songIsPlayed) { //Stops all music from playing)
                stopMusic();  //Stops all music from playing
            }
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Current project is modified");
            alert.setContentText("Save?");
            ButtonType okButton = new ButtonType("Yes", ButtonBar.ButtonData.YES);
            ButtonType noButton = new ButtonType("No", ButtonBar.ButtonData.NO);
            ButtonType cancelButton = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
            alert.getButtonTypes().setAll(okButton, noButton, cancelButton);
            alert.showAndWait().ifPresent(type -> {
                if (type == okButton) {
                    try {
                        songModel.deleteSong(deletedSong); // Sends Song to be delted to songModel.
                        updateSongModel();
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                } else if (type == noButton) { // do something
                }
            });
        }
    }


    public void handleSliMusicVolume(MouseEvent mouseEvent) throws Exception {
    }

    public void handleRestart() throws Exception {

        if (clickPlaylistNotMusicList)
            lstSongsOnPlaylist.getSelectionModel().selectPrevious();
        else
            lstSongs.getSelectionModel().selectPrevious();

        if (songIsPlayed) //Stopper afspilning af musik, hvis noget skal ændres
        {
            handlePlaySong(); //Stopper den sang, som er i gang
        }
    }

    public void handleSkipSong(ActionEvent actionEvent) throws Exception {

        if (clickPlaylistNotMusicList)
        lstSongsOnPlaylist.getSelectionModel().selectNext();
        else
        lstSongs.getSelectionModel().selectNext()
;


        if (songIsPlayed) //Stopper afspilning af musik, hvis noget skal ændres
        {
            handlePlaySong(); //Stopper den sang, som er i gang
        }
    }

    public void handleAddPlaylist(ActionEvent actionEvent) throws Exception {
        String inputValue = JOptionPane.showInputDialog("Please insert playlist name "); // Her er den dovne mulighed
        if (inputValue == null || inputValue.equals("")){
            alertUser("Please enter a name");
        }
        else {
            playlistModel.createNewPlaylist(inputValue);
            updatePlaylistModel();
        }
    }

    public void handleEditPlaylist(ActionEvent actionEvent) {
        try {
            Playlist selectedPlaylist = lstPlaylist.getSelectionModel().getSelectedItem();

            if(selectedPlaylist != null) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/GUI/View/editPlaylist.fxml"));

                Scene scene = new Scene(fxmlLoader.load(), 360, 70);
                Stage stage = new Stage();
                stage.setTitle("Edit the playlist");
                stage.initModality(Modality.WINDOW_MODAL);
                stage.initOwner(((Node) actionEvent.getSource()).getScene().getWindow());
                stage.setScene(scene);
                PlaylistDataInputs playlistDataInputs = fxmlLoader.getController();
                playlistDataInputs.setSelectPlaylist(selectedPlaylist);
                stage.showAndWait();
                updatePlaylistModel();
            }
            else {
                alertUser("Please select a playlist to edit");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void handleDeletePlaylist(ActionEvent actionEvent) {
        Playlist deletedPlaylist = lstPlaylist.getSelectionModel().getSelectedItem();
        if(deletedPlaylist == null){
            alertUser("Please select the song you wish to delete");
        }
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Current project is modified");
        alert.setContentText("Save?");
        ButtonType okButton = new ButtonType("Yes", ButtonBar.ButtonData.YES);
        ButtonType noButton = new ButtonType("No", ButtonBar.ButtonData.NO);
        alert.getButtonTypes().setAll(okButton, noButton);
        alert.showAndWait().ifPresent(type -> {
            if (type == okButton) {
                try {
                    playlistModel.deletePlaylist(deletedPlaylist); // Sends Playlist to be deleted to PlaylistModel.
                    updatePlaylistModel();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            } else if (type == noButton) { // do something
            }
        });
    }

    private void updateSongModel() throws Exception {
        SongModel updatedSongModel = new SongModel();
        songModel = updatedSongModel;
        lstSongs.setItems(songModel.getObservableSong());
    }

    private void updatePlaylistModel() throws Exception {
        PlaylistModel updatedPlaylistModel = new PlaylistModel();
        playlistModel = updatedPlaylistModel;
        lstPlaylist.setItems(playlistModel.getObservablePlaylist());
    }

    private void updateSongToPlaylistModel() throws Exception {
        SongToPlaylistModel updatedSongToPlaylistModel = new SongToPlaylistModel();
        songToPlaylistModel = updatedSongToPlaylistModel;
        lstSongsOnPlaylist.setItems(songToPlaylistModel.getObservablePlaylist());
        songToPlaylistModel.showList(playlistNumber);

    }

    public void handleAddSongToPlaylist(ActionEvent actionEvent) throws Exception {
        selectedSong = lstSongs.getSelectionModel().getSelectedItem();
        selectedPlaylist = lstPlaylist.getSelectionModel().getSelectedItem();
        int playlistSize =  lstSongsOnPlaylist.getItems().size();
        songToPlaylistModel.addSongToPlaylist(selectedSong,selectedPlaylist,playlistSize);
        updateSongToPlaylistModel();
    }

    public void handleDeleteSongFromPlaylist(ActionEvent actionEvent) throws Exception {
        selectedPlaylist = lstPlaylist.getSelectionModel().getSelectedItem();
        selectedSong = (Song) lstSongsOnPlaylist.getSelectionModel().getSelectedItem();
        int selectedRank = lstSongsOnPlaylist.getSelectionModel().getSelectedIndex() + 1; // Gets the position of the Song and adds 1 to as java uses Zero-based numbering and the database does not.
        songToPlaylistModel.deleteSongFromPlaylist(selectedSong,selectedPlaylist,selectedRank);
        updateSongToPlaylistModel();
    }

    public void handleMovePlaylistSongUp(ActionEvent actionEvent) {
    }

    public void handleMovePlaylistSongDown(ActionEvent actionEvent) {
    }

    public void handleSearchAllSongs(ActionEvent actionEvent) {
    }

    public void handlePlaySong() throws Exception {


        if (clickPlaylistNotMusicList && inPlaylister==false)
            playSongInPlaylist();
        else if (clickPlaylistNotMusicList==false && inPlaylister==false)
            playSongInMusicList();

        endOfPlayList=false; //Vi nulstiller endOfPlayList

    }

     public void playSongInPlaylist() throws Exception {

         boolean startSong = true;

         if (songIsPlayed) //Denne if statement sikre, at man kan stoppe musikken selvom den ikke er markeret.
         {

             stopMusic(); //Stop music
             songIsPlayed=false;


             if (lstSongsOnPlaylist.getSelectionModel().getSelectedItem()==previousSong) //Hvis brugeren ikke har valgt en anden sang. Så stopper musikken.
                 startSong=false;


         }

         if (endOfPlayList)
             startSong=false;

         selectedSong= (Song) lstSongsOnPlaylist.getSelectionModel().getSelectedItem();

         if (selectedSong!=null && startSong) //Man skal kun kunne starte musik, hvis den er markeret.
         {

             String path=selectedSong.getFilePath(); //finder stinavnet
             songTitle=selectedSong.getTitle(); //Titlen viser i en label sat i metoden playSong

             previousSong=selectedSong; //Gemmer nuværende sang, så vi næste gang kan se om sangen har skiftet.

             filePath(path);

         }
     }


    public void playSongInMusicList() throws Exception {

        boolean startSong = true;

        if (songIsPlayed) //Denne if statement sikre, at man kan stoppe musikken selvom den ikke er markeret.
        {

            stopMusic(); //Stop music
            songIsPlayed=false;


            if (lstSongs.getSelectionModel().getSelectedItem()==previousSong) //Hvis brugeren ikke har valgt en anden sang. Så stopper musikken.
                startSong=false;

        }

        if (endOfPlayList)
            startSong=false;

        selectedSong=lstSongs.getSelectionModel().getSelectedItem();


        if (selectedSong!=null && startSong) //Man skal kun kunne starte musik, hvis den er markeret.
        {
            String path=selectedSong.getFilePath(); //finder stinavnet
            songTitle=selectedSong.getTitle(); //Titlen viser i en label sat i metoden playSong

            previousSong=selectedSong; //Gemmer nuværende sang, så vi næste gang kan se om sangen har skiftet.

            filePath(path);

        }
    }


public void filePath(String path) throws Exception {
    boolean filesExits= Files.exists(Path.of(path)); //check om filen eksisterer


    if (filesExits)
    {
        playMusic(path);
        songIsPlayed=true;
    }
    else

        informationUser("File do not exist!");
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

    public void playMusic(String path) throws Exception {

        hit = new Media(new File(path).toURI().toString());
        play = new MediaPlayer(hit);
        soundVolume(soundLevel);

        txtShowSong.setText("Playing: " + songTitle); //Label tekst til skærmen om hvilket sang der afspilles.

        timeTest();
        play.play();
    }

    public void stopMusic() {
        timer.cancel();
        play.stop();
    }
    public void soundVolume(double soundLevel)
    {
        this.soundLevel = soundLevel;

        if (play != null) {
            double soundLev = soundLevel / 100;
            play.setVolume(soundLev);
        }
    }

    public void timeTest() {

        timer = new Timer();
        task = new TimerTask() {
            public void run() {
                double current = play.getCurrentTime().toSeconds();
                double end = hit.getDuration().toSeconds();

                if (current/ end ==1)
                {
                    timer.cancel();


                    if (clickPlaylistNotMusicList) {
                        if (lstSongsOnPlaylist.getItems().size() == lstSongsOnPlaylist.getSelectionModel().getSelectedIndex() + 1) //Hvis det er sidste sang i playlisten, så
                                                                                                                                    //skal den stoppe med at spille.
                            endOfPlayList = true;
                        else
                            endOfPlayList = false;


                        lstSongsOnPlaylist.getSelectionModel().selectNext(); //Her skifter til næste linje
                    }
                    else {

                        if (lstSongs.getItems().size() == lstSongs.getSelectionModel().getSelectedIndex() + 1)
                            endOfPlayList = true;
                        else
                            endOfPlayList = false;


                        lstSongs.getSelectionModel().selectNext(); //Her skifter til næste linje
                        selectedSong = lstSongs.getSelectionModel().getSelectedItem();

                    }

                        songIsPlayed=false;






                    try {
                        handlePlaySong();
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        };
        timer.scheduleAtFixedRate(task,10,1000); //Den måler hver sekund altså 1000 ms med en lille delay.
    }
}


