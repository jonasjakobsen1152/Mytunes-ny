package BLL.util;

import BE.Song;

import java.util.ArrayList;
import java.util.List;

public class SongSearcher {


    public List<Song> search(List<Song> searchBase, String query) {
        List<Song> searchResult = new ArrayList<>();

        for (Song Song : searchBase) {
            if(compareToMovieTitle(query, Song)) //|| compareToMovieYear(query, Song))
            {
                searchResult.add(Song);
            }
        }

        return searchResult;
    }

    //private boolean compareToMovieYear(String query, Song movie) {
        //return Integer.toString(movie.getYear()).contains(query);}

    private boolean compareToMovieTitle(String query, Song movie) {
        return movie.getTitle().toLowerCase().contains(query.toLowerCase());
    }

}
