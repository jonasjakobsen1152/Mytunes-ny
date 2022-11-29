package BLL.util;

import BE.Song;

import java.util.ArrayList;
import java.util.List;

public class SongSearcher {


    public List<Song> search(List<Song> searchBase, String query) {
        List<Song> searchResult = new ArrayList<>();

        for (Song Song : searchBase) {
            if(compareToSongTitle(query, Song) || compareToSongArtist(query, Song))
            {
                searchResult.add(Song);
            }
        }

        return searchResult;
    }

    //private boolean compareToMovieYear(String query, Song movie) {
        //return Integer.toString(movie.getYear()).contains(query);}

    private boolean compareToSongTitle(String query, Song song) {
        return song.getTitle().toLowerCase().contains(query.toLowerCase());
    }

    private boolean compareToSongArtist(String query, Song song) {
        return song.getArtist().toLowerCase().contains(query.toLowerCase());
    }

}
