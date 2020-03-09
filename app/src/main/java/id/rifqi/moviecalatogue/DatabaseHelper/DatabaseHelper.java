package id.rifqi.moviecalatogue.DatabaseHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DatabaseHelper extends SQLiteOpenHelper {
    private static String DATABASE_NAME = "dbmovies";
    private static final int DATABASE_VERSION = 1;

    private static final String SQL_CREATE_TABLE_TBMOVIE = String.format("CREATE TABLE %s"
                    + " (%s INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + " %s TEXT NOT NULL,"
                    + " %s TEXT NOT NULL UNIQUE,"
                    + " %s TEXT NOT NULL,"
                    + " %s TEXT NOT NULL)",
            DatabaseContract.TABLE_NAME,
            DatabaseContract.MovieColumn._ID,
            DatabaseContract.MovieColumn.POSTER,
            DatabaseContract.MovieColumn.TITLE,
            DatabaseContract.MovieColumn.RILIS,
            DatabaseContract.MovieColumn.OVERVIEW

    );

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQL_CREATE_TABLE_TBMOVIE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + DatabaseContract.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

}
