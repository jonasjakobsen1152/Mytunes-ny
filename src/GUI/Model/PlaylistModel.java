package GUI.Model;

import BE.Playlist;
import BLL.PlaylistManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class PlaylistModel {
    private ObservableList<Playlist> playlistToBeViewed;
    private PlaylistManager playlistManager;
    private Playlist selectedPlaylist;

    public PlaylistModel() throws Exception {
        playlistManager = new PlaylistManager();
        playlistToBeViewed = FXCollections.observableArrayList();

        playlistToBeViewed.addAll(playlistManager.getAllPlaylist());
    }

    public ObservableList<Playlist> getObservablePlaylist() {
        return playlistToBeViewed;
    }

    public void createNewPlaylist(String name) throws Exception {
        //Crete Playlist in data storage
        playlistManager.createPlaylist(name);

        //Add Playlist to observable list (gui)
        showList();
    }
    public void updatePlaylist(Playlist updatePlaylist) throws Exception {
        // Call BLL and update Playlist in DB
        playlistManager.updatePlaylist(updatePlaylist);

        //update listview
        showList();
    }

    public Playlist getSelectedPlaylist(){
        return selectedPlaylist;
    }

    public void setSelectedPlaylist(Playlist selectedPlaylist) {
        this.selectedPlaylist = selectedPlaylist;
    }
    public void deletePlaylist(Playlist deletePlaylist) throws Exception {
        playlistManager.deletePlaylist(deletePlaylist);
        showList();
    }

    public void showList() throws Exception {
        // Update the listview
        playlistToBeViewed.clear();
        playlistToBeViewed.addAll(playlistManager.getAllPlaylist());
    }
}
