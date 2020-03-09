package id.rifqi.moviecalatogue.api;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import id.rifqi.moviecalatogue.model.Movie;

public class MovieResponse {

    @SerializedName("results")
    private ArrayList<Movie> results;


    public ArrayList<Movie> getResults() {
        return results;
    }

}
