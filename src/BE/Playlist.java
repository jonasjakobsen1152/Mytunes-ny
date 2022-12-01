package BE;
import BLL.util.InvalidLengthException;

import java.util.*;
import java.io.*;

public class Playlist implements Serializable
{
    private ArrayList<Song> playlist;

    //Creates a Playlist object that contains an ArrayList for Songs.
    public Playlist()
    {
        playlist = new ArrayList<Song>();
    }

    //Adds a Song to a Playlist.
    public void addSong(Song song)
    {
        playlist.add(song);
    }

    //Returns the size of a Playlist.
    public int getPlaylistSize()
    {
        return playlist.size();
    }

    //Returns the artist of the Song at position (index) of a Playlist.
    public String getArtist(int index)
    {
        return playlist.get(index).getArtist();
    }

    //Returns the title of the Song at position (index) of a Playlist.
    public String getTitle(int index)
    {
        return playlist.get(index).getTitle();
    }

    //Returns the length of the Song at position (index) of a Playlist.
    public int getSeconds(int index)
    {
        return playlist.get(index).getSeconds();
    }

    //Returns the genre of the Song at position (index) of a Playlist.
    public String getCategory(int index)
    {
        return playlist.get(index).getCategory();
    }
    /**
    //Sets the artist of the Song at position (index) to artist.
    public void updateArtist(int index, String artist)
    {
        playlist.get(--index).setArtist(artist);
    }

    //Sets the title of the Song at position (index) to the title.
    public void updateTitle(int index, String title)
    {
        playlist.get(--index).setTitle(title);
    }

    //Sets the Seconds of the Song at position (index) to Seconds; throws InvalidLengthException.
    public void updateLength(int index, String Seconds) throws InvalidLengthException
    {
        if(Seconds.matches("(\\d.*):(\\d.*)"))
        {
            playlist.get(--index).setSeconds(Seconds);
        }
        else
        {
            throw new InvalidLengthException(Seconds);
        }
    }

    //Sets the genre of the Song at position (index) to genre.
    public void updateCategory(int index, String category)
    {
        playlist.get(--index).setCategory(category);
    }
    */

    //Removes the song located at the specified index from the Playlist.
    public void removeSong(int index)
    {
        playlist.remove(index);
    }

    //Clears the Playlist of all Songs by invoking clear() on a Playlist.
    public void deletePlaylist()
    {
        if(playlist.size() == 0)
        {
            System.out.print("Playlist is empty!\n");
        }
        else
        {
            playlist.clear();
            System.out.print("Playlist has been deleted.\n");
        }
    }

} //End class
