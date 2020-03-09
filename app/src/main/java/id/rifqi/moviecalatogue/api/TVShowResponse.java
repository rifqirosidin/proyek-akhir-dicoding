package id.rifqi.moviecalatogue.api;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import id.rifqi.moviecalatogue.model.TVShow;

public class TVShowResponse {
    @SerializedName("results")
    private ArrayList<TVShow> resultsTVShow;

    public ArrayList<TVShow> getResultsTVShow() {
        return resultsTVShow;
    }
}
