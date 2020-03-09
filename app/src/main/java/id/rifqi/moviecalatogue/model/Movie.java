package id.rifqi.moviecalatogue.model;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.SerializedName;

import id.rifqi.moviecalatogue.DatabaseHelper.DatabaseContract;

import static id.rifqi.moviecalatogue.DatabaseHelper.DatabaseContract.getColumnInt;
import static id.rifqi.moviecalatogue.DatabaseHelper.DatabaseContract.getColumnString;

public class Movie implements Parcelable{

    @SerializedName("title")
    private String title;
    @SerializedName("overview")
    private String description;
    @SerializedName("release_date")
    private String rilis;
    @SerializedName("poster_path")
    private String poster;
    private int id;

    public static final String urlPoster = "https://image.tmdb.org/t/p/w185_and_h278_bestv2";

    public Movie() {
    }

    public Movie(int  id,String title, String description, String rilis, String poster) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.rilis = rilis;
        this.poster = poster;
    }

    public Movie(Cursor cursor) {
        this.id = getColumnInt(cursor, DatabaseContract.MovieColumn._ID);
        this.title = getColumnString(cursor, DatabaseContract.MovieColumn.TITLE);
        this.rilis = getColumnString(cursor, DatabaseContract.MovieColumn.RILIS);
        this.description = getColumnString(cursor, DatabaseContract.MovieColumn.OVERVIEW);
        this.poster = getColumnString(cursor, DatabaseContract.MovieColumn.POSTER);

    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }


    public String getDescription() {
        return description;
    }


    public String getRilis() {
        return rilis;
    }


    public String getPoster() {
        return poster;
    }


    public Movie(Parcel in) {
        title = in.readString();
        description = in.readString();
        rilis = in.readString();
        poster = in.readString();
    }

    public static final Parcelable.Creator<Movie> CREATOR = new Parcelable.Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(description);
        dest.writeString(rilis);
        dest.writeString(poster);
    }
}
