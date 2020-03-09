package id.rifqi.moviecalatogue.DatabaseHelper;

import android.database.Cursor;

import java.util.ArrayList;

import id.rifqi.moviecalatogue.DatabaseHelper.DatabaseContract.MovieColumn;
import id.rifqi.moviecalatogue.model.Movie;

public class MappingHelper {

    public static ArrayList<Movie> mapCursorToArrayList(Cursor movieCursor) {
        ArrayList<Movie> movieList = new ArrayList<>();
        while (movieCursor.moveToNext()) {
            int id = movieCursor.getInt(movieCursor.getColumnIndexOrThrow(MovieColumn._ID));
            String title = movieCursor.getString(movieCursor.getColumnIndexOrThrow(MovieColumn.TITLE));
            String description = movieCursor.getString(movieCursor.getColumnIndexOrThrow(MovieColumn.OVERVIEW));
            String rilis = movieCursor.getString(movieCursor.getColumnIndexOrThrow(MovieColumn.RILIS));
            String poster = movieCursor.getString(movieCursor.getColumnIndexOrThrow(MovieColumn.POSTER));
            movieList.add(new Movie(id,title, description, rilis, poster));
        }
    return movieList;
    }
}
