package BE;

public class Playlist {
    private int id;
    private String name;


    public Playlist(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
    public String toString() {
        return id + name;
    }
}

