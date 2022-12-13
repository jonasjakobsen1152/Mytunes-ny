package GUI.Controller;

import BE.Playlist;
import BE.Song;
import GUI.Model.MYTModel;
import GUI.Model.PlaylistModel;
import GUI.Model.SongModel;
import GUI.Model.SongToPlaylistModel;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
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
    @FXML
    private MYTModel mytModel;
    private PlaylistModel playlistModel;
    private SongToPlaylistModel songToPlaylistModel;
    private boolean songIsPlayed = false; //used to stop songs from playing in case that no song is marked
    private boolean endOfPlayList=false;
    private boolean clickPlaylistNotMusicList;
    private boolean inPlaylister;

    private boolean isNewPlay = true;

    public Playlist selectedPlaylist;
    public Song previousSong,selectedSong;
    private String errorText;
    private String songTitle;
    double soundLevel = 50;
    private MediaPlayer play;
    private Media hit;
    private Timer timer;
    private TimerTask task;
    private int playlistNumber;


    @Override
    public void initialize (URL url, ResourceBundle resourceBundle){
        lstSongs.setItems(songModel.getObservableSong());
        lstPlaylist.setItems(playlistModel.getObservablePlaylist());
        lstSongsOnPlaylist.setItems(songToPlaylistModel.getObservablePlaylist());


        lstSongs.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
        isNewPlay = true;
        });

        lstSongsOnPlaylist.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            isNewPlay = true;
        });

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

        lstSongsOnPlaylist.setOnMouseClicked(event -> { //Vi har en "lytter" på museklik på de tre listview tabeller.
            clickPlaylistNotMusicList=true;     //Vi har boolean til at undersøge, om vi er I musiklisten eller musikplaylisten. Her musikplaylisten.
            inPlaylister=false;                 //Vi er ikke i playlisten.

            if (event.getClickCount() == 2 ) //Ved dobbeltklik kan man starte musikken
            {
                try {
                    handlePlaySong();       //Håndtere afspilning af sange.
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }  });


                lstSongs.setOnMouseClicked(event -> {

                    clickPlaylistNotMusicList=false;     //Vi har boolean til at undersøge, om vi er I musiklisten eller musikplaylisten. Her musiklisten
                    inPlaylister=false;

                    if (event.getClickCount() == 2 ) { //Ved dobbeltklik kan man starte musikken
                        try {
                            handlePlaySong();
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    }
                });

        sliMusicVolume.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                soundVolume(sliMusicVolume.getValue());         //Lytter om lyden skal skues op. Værdien sendes til soundVolume.
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
        lstSongs.getSelectionModel().selectNext();

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
            Playlist selectedPlaylist = lstPlaylist.getSelectionModel().getSelectedItem(); // Gets selected playlist

            if(selectedPlaylist != null) { // Checks if the playlist is null
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
        if(deletedPlaylist == null){ // Checks if a playlist is selected
            alertUser("Please select the song you wish to delete");
        }
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Current project is modified");
        alert.setContentText("Save?");
        ButtonType okButton = new ButtonType("Yes", ButtonBar.ButtonData.YES);
        ButtonType noButton = new ButtonType("No", ButtonBar.ButtonData.NO);
        alert.getButtonTypes().setAll(okButton, noButton);
        alert.showAndWait().ifPresent(type -> {
            if (type == okButton) { // Check if user wants to complete the deletion of a song
                try {
                    playlistModel.deletePlaylist(deletedPlaylist); // Sends Playlist to be deleted to PlaylistModel.
                    updatePlaylistModel();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            } else if (type == noButton) { // Cancel the deletion of a song
            }
        });
    }

    private void updateSongModel() throws Exception {
        SongModel updatedSongModel = new SongModel(); // Istantiates a new songModel
        songModel = updatedSongModel; // Updates it to our instance variable
        lstSongs.setItems(songModel.getObservableSong()); // Sets the list to the updated instance variable
    }

    private void updatePlaylistModel() throws Exception {
        PlaylistModel updatedPlaylistModel = new PlaylistModel(); // Istantiates a new PlayListModel
        playlistModel = updatedPlaylistModel; // Updates it to our instance variable
        lstPlaylist.setItems(playlistModel.getObservablePlaylist()); // Sets the list to the updated instance variable
    }

    private void updateSongToPlaylistModel() throws Exception {
        SongToPlaylistModel updatedSongToPlaylistModel = new SongToPlaylistModel(); // Istantiates a new songToPlayListModel
        songToPlaylistModel = updatedSongToPlaylistModel; // Updates it to our instance variable
        lstSongsOnPlaylist.setItems(songToPlaylistModel.getObservablePlaylist()); // Updates the list to the new list from songToPlayListModel
        songToPlaylistModel.showList(playlistNumber); // Clears and shows the new list

    }

    public void handleAddSongToPlaylist(ActionEvent actionEvent) throws Exception {
        int lastIndex = songToPlaylistModel.getObservablePlaylist().size(); // Gets the size and stashes it in variable lastIndex
        Song getSongForRank = (Song) lstSongsOnPlaylist.getItems().get(lastIndex - 1); // Gets the last song and creates a object with its values
        int lastSongID = getSongForRank.getId(); // Holds the value for the songID of the last song
        int lastPlaylistID = selectedPlaylist.getId(); // Holds the value for the playlistID
        int lastRank = songToPlaylistModel.getRank(lastSongID, lastPlaylistID); // Gets the highest/last rank from the database

        selectedSong = lstSongs.getSelectionModel().getSelectedItem(); // Gets the selectedSong that should be added
        selectedPlaylist = lstPlaylist.getSelectionModel().getSelectedItem(); // Gets the selected playlist the song will be added to
        songToPlaylistModel.addSongToPlaylist(selectedSong,selectedPlaylist,lastRank + 1); // Adds the song to the database
        updateSongToPlaylistModel(); // Updates the SongToPlaylistModel
    }

    public void handleDeleteSongFromPlaylist(ActionEvent actionEvent) throws Exception {
        selectedPlaylist = lstPlaylist.getSelectionModel().getSelectedItem(); // Sets instance variable to the selected playlist
        selectedSong = (Song) lstSongsOnPlaylist.getSelectionModel().getSelectedItem(); // Sets the instance variable to selected song
        int songID = selectedSong.getId(); // Gets the song id from our selected song
        int playlistID = selectedPlaylist.getId(); // Gets the id from the selected playlist
        int songToBeDeleted = songToPlaylistModel.getRank(songID, playlistID); // Uses the id's from instance variables

        songToPlaylistModel.deleteSongFromPlaylist(selectedSong,selectedPlaylist,songToBeDeleted); // Sends down the selectedSong, from selected playlist with the rank of the song that should be deleted.
        updateSongToPlaylistModel(); // Updates SongToPlaylistModel
    }

    public void handleMovePlaylistSongUp(ActionEvent actionEvent) throws Exception {



        if (clickPlaylistNotMusicList && inPlaylister==false)  //Vi sikre os, at vi er musikplayliste vinduet.
        {
            if (songIsPlayed)
                handlePlaySong(); //Stop music

            selectedSong = (Song) lstSongsOnPlaylist.getSelectionModel().getSelectedItem(); // Her gemmes sangen vi står på.
            int songID1 = selectedSong.getId(); //Her hentes sangens databasenummer.


            lstSongsOnPlaylist.getSelectionModel().selectPrevious(); //Her hentes linjen før.

            selectedSong = (Song) lstSongsOnPlaylist.getSelectionModel().getSelectedItem(); //Sangen gemmes
            int songID2 = selectedSong.getId();                                             //Sangens ID gemmes
            int place=lstSongsOnPlaylist.getSelectionModel().getSelectedIndex();            //Vi gemmes placering vi står på.


            songToPlaylistModel.songSwap(songID1, songID2, playlistNumber);      //Vi kalder songSwap i Model klassen. Der trækker via en mellemstation
            songToPlaylistModel.showList(playlistNumber);                       // i manageren en metode i DAO som bytter rank mellem to gemte sange.
                                                                                // Listen opdateres
            lstSongsOnPlaylist.getSelectionModel().select(place);               //Markeringen placeres hvor den før opdateringen.



        }
        }


    public void handleMovePlaylistSongDown(ActionEvent actionEvent) throws Exception {


        if (clickPlaylistNotMusicList && inPlaylister==false) {         //Metoden er beskrevet ovenfor.

            if (songIsPlayed)
                handlePlaySong(); //Stop music

            selectedSong = (Song) lstSongsOnPlaylist.getSelectionModel().getSelectedItem();
            int number1 = selectedSong.getId();


            lstSongsOnPlaylist.getSelectionModel().selectNext();
            selectedSong = (Song) lstSongsOnPlaylist.getSelectionModel().getSelectedItem();
            int number2 = selectedSong.getId();
            int number3=lstSongsOnPlaylist.getSelectionModel().getSelectedIndex();

            songToPlaylistModel.songSwap(number1, number2, playlistNumber);
            songToPlaylistModel.showList(playlistNumber);

            lstSongsOnPlaylist.getSelectionModel().select(number3);

        }
    }


    public void handlePlaySong() throws Exception {

        if (clickPlaylistNotMusicList && inPlaylister==false)               //Vi sikre os, aat vi er i musikplayliste vinduet
            playSongInPlaylist();                                           //Her kaldes metoden, der for musikplaylisten
        else if (clickPlaylistNotMusicList==false && inPlaylister==false)   //Her er i musiklisten og kalder dens metode.
            playSongInMusicList();

        endOfPlayList=false; //Vi nulstiller endOfPlayList

    }

     public void playSongInPlaylist() throws Exception {

         boolean startSong = true;

         if (songIsPlayed) //Vi vil stopppe/pause musik, hvis den spilles..
         {

             stopMusic(); //Stop music eller pause den
             songIsPlayed=false;    //Nu spilles musikken ikke. Der sættes en boolean der fortæller det.


             if (lstSongsOnPlaylist.getSelectionModel().getSelectedItem()==previousSong) //Hvis brugeren ikke har valgt en anden sang skal en ny sang ikke startes.
                 startSong=false;       //Derfor sættes en boolean startSong til false

         }

         if (endOfPlayList)         //Hvis vi er på automatisk sangskifte, så skal en ny sang ikke startes, hvis vi er ved listens ende.
             startSong=false;

         selectedSong= (Song) lstSongsOnPlaylist.getSelectionModel().getSelectedItem(); //vi Gemmer den sang, som vi står på.

         if (selectedSong!=null && startSong) //Man skal kun kunne starte musik, hvis den er markeret og startSong er true.
         {

             String path=selectedSong.getFilePath(); //finder stinavnet
             songTitle=selectedSong.getTitle(); //Vi gemmer titlen og viser den i en label sat i metoden playSong

             previousSong=selectedSong; //Gemmer nuværende sang, så vi næste gang kan se om sangen har skiftet.

             filePath(path);    //kalder metoden filepath

         }
     }

    public void playSongInMusicList() throws Exception {    //Denne metode ligner den foregående.

        boolean startSong = true;

        if (songIsPlayed)
        {

            stopMusic();
            songIsPlayed=false;

            if (lstSongs.getSelectionModel().getSelectedItem()==previousSong)
                startSong=false;

        }

        if (endOfPlayList)
            startSong=false;

        selectedSong=lstSongs.getSelectionModel().getSelectedItem();


        if (selectedSong!=null && startSong)
        {
            String path=selectedSong.getFilePath();
            songTitle=selectedSong.getTitle();

            previousSong=selectedSong;

            filePath(path);

        }
    }

        public void filePath(String path) throws Exception {
         boolean filesExits= Files.exists(Path.of(path)); //check om filen eksisterer

         if (filesExits)
         {
            playMusic(path);    //Her kaldes musikspille metoden
             songIsPlayed=true; //Vi sætter boolean sang afspilles til true
         }
         else
            informationUser("File do not exist!");      //Her kaldes en metode, der viser et vindue med besked om, at filen ikke findes.
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

        public void playMusic(String path) throws Exception {
             if (isNewPlay) //Alt musik er kun sat på pause. Hvis vi henter en nye mediaplayer ind, så stoppes reelt musikken.
             {
                 hit = new Media(new File(path).toURI().toString());
                 play = new MediaPlayer(hit);
                 txtShowSong.setText("Playing: " + songTitle); //Label tekst til skærmen om hvilket sang der afspilles.
                 isNewPlay = false; // Fortsætter med at spille samme sang som blev pauset.

             }
                 soundVolume(soundLevel);   //soundVolumen sættes. Den skal være ens fra sang til sang.
                 timeTest();                //Vi kalder en metode, der afgøre om en sang er færdig.
                 play.play();               //Musik afspilles.
        }

        public void stopMusic() {
             timer.cancel();            //Vores timer stoppes.
             play.pause();              //Musikken sættes på pause.
        }

         public void soundVolume(double soundLevel) {
             this.soundLevel = soundLevel;

             if (play != null) {    //Vi skal have valgt en mediaplayer. Hvis brugeren justerer lyden inden afspilning, kan man  ikke justerer lyden.
                 double soundLev = soundLevel / 100;    //Lyden skal sættes ind som et dedimaltal mellem 0 og 1.
                 play.setVolume(soundLev);  //Her sættes lyden.
             }
         }

         public void timeTest() {

              timer = new Timer();  //Da vi programmerede kendte vi ikke metoden i mediaplayer klassen til at bestemme om sangen er værdig.
              task = new TimerTask() {
            public void run() {
                double current = play.getCurrentTime().toSeconds(); //Her får vi den nuværende spilletid.
                double end = hit.getDuration().toSeconds();         //Her er sangens længde.

                if (current/ end ==1) //Hvis de divideres og værdien er 1, så er sangen færdig.
                {
                    timer.cancel();     //Her stoppes tiden.

                    if (clickPlaylistNotMusicList) {    //Her vælger vi mellem to forskellige musiklister
                        if (lstSongsOnPlaylist.getItems().size() == lstSongsOnPlaylist.getSelectionModel().getSelectedIndex() + 1) //Hvis det er sidste sang i playlisten, så
                                                                                                                                    //skal den stoppe med at spille.
                            endOfPlayList = true;   //Vi sætter boolean endOfPlayList til true. Det vil stoppe lydafspilning.
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
                        selectedSong = lstSongs.getSelectionModel().getSelectedItem(); //Her vælges sangen på linjen.

                    }

                        songIsPlayed=false;                 //Sangen spilles ikke. Derfor er songIsPlayed sat til false.
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


