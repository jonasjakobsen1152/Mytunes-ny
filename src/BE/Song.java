package BE;
public class Song {

    private int id;
    private String title;
    private String artist;
    private String category;
    private int seconds;

    public Song(int id, String title, String artist, String category, int seconds) {
        this.id = id;
        this.title = title;
        this.artist = artist;
        this.category = category;
        this.seconds = seconds;
    }
    public int getId(){return id;}
    public String getTitle(){return title;}
    public String getArtist(){return artist;}
    public String getCategory(){return category;}
    public int getSeconds() {return seconds;}

    @Override
    public String toString() {
        return super.toString();
    }
}
