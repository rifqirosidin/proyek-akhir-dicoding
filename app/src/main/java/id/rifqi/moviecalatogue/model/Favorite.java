package id.rifqi.moviecalatogue.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Favorite implements Parcelable {

    private String  title, descripsi, rilis, poster;
    private int id;

    public Favorite(int id, String title, String descripsi, String rilis, String poster) {
        this.id = id;
        this.title = title;
        this.descripsi = descripsi;
        this.rilis = rilis;
        this.poster = poster;
    }

    protected Favorite(Parcel in) {
        title = in.readString();
        descripsi = in.readString();
        rilis = in.readString();
        poster = in.readString();
        id = in.readInt();
    }

    public static final Creator<Favorite> CREATOR = new Creator<Favorite>() {
        @Override
        public Favorite createFromParcel(Parcel in) {
            return new Favorite(in);
        }

        @Override
        public Favorite[] newArray(int size) {
            return new Favorite[size];
        }
    };

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescripsi() {
        return descripsi;
    }

    public String getRilis() {
        return rilis;
    }

    public String getPoster() {
        return poster;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(title);
        parcel.writeString(descripsi);
        parcel.writeString(rilis);
        parcel.writeString(poster);
        parcel.writeInt(id);
    }
}
