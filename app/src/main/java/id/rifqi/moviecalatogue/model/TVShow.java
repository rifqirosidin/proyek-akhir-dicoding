package id.rifqi.moviecalatogue.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class TVShow implements Parcelable {

    @SerializedName("name")
    private String title;
    @SerializedName("first_air_date")
    private String rilis;
    @SerializedName("poster_path")
    private String poster;
    @SerializedName("overview")
    private String description;
    public static String URL_POSTER = "https://image.tmdb.org/t/p/w185";

    public TVShow() {
    }

    protected TVShow(Parcel in) {
        title = in.readString();
        rilis = in.readString();
        poster = in.readString();
        description = in.readString();
    }

    public static final Creator<TVShow> CREATOR = new Creator<TVShow>() {
        @Override
        public TVShow createFromParcel(Parcel in) {
            return new TVShow(in);
        }

        @Override
        public TVShow[] newArray(int size) {
            return new TVShow[size];
        }
    };

    public String getTitle() {
        return title;
    }

    public String getRilis() {
        return rilis;
    }

    public String getPoster() {
        return poster;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(title);
        parcel.writeString(rilis);
        parcel.writeString(poster);
        parcel.writeString(description);
    }
}
