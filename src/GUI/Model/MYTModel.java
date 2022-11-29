package GUI.Model;

public class MYTModel {
    private SongModel songModel;
    public MYTModel() throws Exception {
        songModel = new SongModel();
    }

    public SongModel getSongModel() {
        return songModel;
    }

    public void setSongModel(SongModel songModel) {
        this.songModel = songModel;
    }
}