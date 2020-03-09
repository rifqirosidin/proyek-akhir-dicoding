package id.rifqi.moviecalatogue.DatabaseHelper;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import id.rifqi.moviecalatogue.model.Movie;

import static android.provider.BaseColumns._ID;
import static id.rifqi.moviecalatogue.DatabaseHelper.DatabaseContract.MovieColumn.OVERVIEW;
import static id.rifqi.moviecalatogue.DatabaseHelper.DatabaseContract.MovieColumn.POSTER;
import static id.rifqi.moviecalatogue.DatabaseHelper.DatabaseContract.MovieColumn.RILIS;
import static id.rifqi.moviecalatogue.DatabaseHelper.DatabaseContract.MovieColumn.TITLE;
import static id.rifqi.moviecalatogue.DatabaseHelper.DatabaseContract.TABLE_NAME;

public class MovieHelper {

    private static final String DATABASE_TABLE =  TABLE_NAME;
    private static DatabaseHelper dataBaseHelper;
    private static  MovieHelper INSTANCE;
    private static SQLiteDatabase database;
    Context context;
    public MovieHelper() {
    }

    public MovieHelper(Context context) {
        this.context = context;
        dataBaseHelper =  new DatabaseHelper(context);
    }


    public static MovieHelper getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (SQLiteOpenHelper.class) {
                if (INSTANCE == null) {
                    INSTANCE = new MovieHelper(context);
                }
            }
        }
        return INSTANCE;
    }

    public MovieHelper open() throws SQLException {
        database = dataBaseHelper.getWritableDatabase();
        return this;
    }
    public void close() throws SQLiteException {
        database = dataBaseHelper.getReadableDatabase();
        if (database != null && database.isOpen())
            database.close();

//        dataBaseHelper.close();
//        if (database.isOpen())
//            database.close();
    }

    public Cursor queryAll() {
        return database.query(DATABASE_TABLE,
                null,
                null,
                null,
                null,
                null,
                _ID + " DESC");
    }

    public Cursor queryById(String id) {
        return database.query(DATABASE_TABLE, null
                , _ID + " = ?"
                , new String[]{id}
                , null
                , null
                , null
                , null);
    }

    public void insert(Movie movie) {
        open();
        ContentValues values = new ContentValues();
        values.put(TITLE, movie.getTitle());
        values.put(RILIS, movie.getRilis());
        values.put(OVERVIEW, movie.getDescription());
        values.put(POSTER, movie.getPoster());

        database.insertOrThrow(TABLE_NAME, null, values);
//        context.getContentResolver().insert(DatabaseContract.CONTENT_URI, values);

        close();
    }

    public long insertProvider(ContentValues values) {
        return database.insertOrThrow(DATABASE_TABLE, null, values);
    }

    public int update(String id, ContentValues values) {
        return database.update(DATABASE_TABLE, values, _ID + " = ?", new String[]{id});
    }

    public int deleteById(String id) {
        return database.delete(DATABASE_TABLE, _ID + " = ?", new String[]{id});
    }

    public Cursor queryProvider() {
        return null;
    }
}
