package id.rifqi.moviecalatogue.DatabaseHelper;
import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;

public class DatabaseContract {

    public static String TABLE_NAME = "tb_movies";

    public static final String AUTHORITY = "id.rifqi.moviecalatogue";

    public static final Uri CONTENT_URI = new Uri.Builder().scheme("content")
            .authority(AUTHORITY)
            .appendPath(TABLE_NAME)
            .build();

    public static final class MovieColumn implements BaseColumns {
        public static String POSTER = "poster";
        public static String TITLE = "title";
        public static String RILIS = "rilis";
        public static String OVERVIEW = "overview";


    }

    public static String getColumnString(Cursor cursor, String columnName) {
        return cursor.getString(cursor.getColumnIndex(columnName));
    }

    public static int getColumnInt(Cursor cursor, String columnName) {
        return cursor.getInt(cursor.getColumnIndex(columnName));
    }

}
