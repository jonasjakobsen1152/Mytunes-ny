package BE;

import java.util.*;
import java.io.*;

public class Playlist implements Serializable {
    private int id;
    private String name;

    //Creates a Playlist object that contains an ArrayList for Songs.
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
}

