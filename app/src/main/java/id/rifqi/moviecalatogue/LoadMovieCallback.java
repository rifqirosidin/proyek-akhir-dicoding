package id.rifqi.moviecalatogue;

import java.util.ArrayList;

import id.rifqi.moviecalatogue.model.Favorite;
import id.rifqi.moviecalatogue.model.Movie;

public interface LoadMovieCallback {
    void preExecute();
    void postExecute(ArrayList<Movie> movies);
}
