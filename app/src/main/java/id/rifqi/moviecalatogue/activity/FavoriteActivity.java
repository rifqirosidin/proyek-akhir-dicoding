package id.rifqi.moviecalatogue.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import id.rifqi.moviecalatogue.DatabaseHelper.MappingHelper;
import id.rifqi.moviecalatogue.DatabaseHelper.MovieHelper;
import id.rifqi.moviecalatogue.LoadMovieCallback;
import id.rifqi.moviecalatogue.R;
import id.rifqi.moviecalatogue.adapter.FavoriteAdapter;
import id.rifqi.moviecalatogue.model.Movie;

import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.google.android.material.snackbar.Snackbar;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class FavoriteActivity extends AppCompatActivity implements LoadMovieCallback {

    RecyclerView rvFavorite;
    FavoriteAdapter favoriteAdapter;
    ProgressBar progressBar;
    ArrayList<Movie> moviesFavorite;
    private MovieHelper movieHelper;
    private static final String EXTRA_STATE = "EXTRA_STATE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);
        setTitle(getString(R.string.title_favorite_activity));
        rvFavorite = findViewById(R.id.rv_favorite_activity);
        progressBar = findViewById(R.id.progressBar_favorite_activity);
        favoriteAdapter = new FavoriteAdapter(this);
        rvFavorite.setLayoutManager(new LinearLayoutManager(this));
        rvFavorite.setHasFixedSize(true);

        movieHelper = MovieHelper.getInstance(this);
        movieHelper.open();


        if (savedInstanceState == null) {
            new LoadAsyncMovie(movieHelper, this).execute();

        } else {
            ArrayList<Movie> list = savedInstanceState.getParcelableArrayList(EXTRA_STATE);
            if (list != null) {
                favoriteAdapter.setListMovieFavorite(list);
            }
        }

        new LoadAsyncMovie(movieHelper, this).execute();
    }
    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(EXTRA_STATE, favoriteAdapter.getListMovieFavorite());
    }

    @Override
    public void preExecute() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                progressBar.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public void postExecute(ArrayList<Movie> movies) {
        progressBar.setVisibility(View.GONE);

        if (movies.size() > 0) {
            favoriteAdapter.setListMovieFavorite(movies);

            favoriteAdapter.notifyDataSetChanged();
            rvFavorite.setAdapter(favoriteAdapter);
        } else {
            favoriteAdapter.setListMovieFavorite(new ArrayList<Movie>());
            showSnackbarMessage(getString(R.string.message_favorite_item));
        }
    }


    private class LoadAsyncMovie extends AsyncTask<Void, Void, ArrayList<Movie>> {

        private final WeakReference<MovieHelper> weakMovieHelper;
        private final WeakReference<LoadMovieCallback> weakCallback;

        public LoadAsyncMovie(MovieHelper movieHelper, LoadMovieCallback callback) {
            weakMovieHelper = new WeakReference<>(movieHelper);
            weakCallback = new WeakReference<>(callback);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
            weakCallback.get().preExecute();
        }

        @Override
        protected ArrayList<Movie> doInBackground(Void... voids) {
            Cursor dataCursor = weakMovieHelper.get().queryAll();
            return MappingHelper.mapCursorToArrayList(dataCursor);
        }

        @Override
        protected void onPostExecute(ArrayList<Movie> movies) {
            super.onPostExecute(movies);
            weakCallback.get().postExecute(movies);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        movieHelper.close();
    }

    private void showSnackbarMessage(String message) {
        Snackbar.make(rvFavorite, message, Snackbar.LENGTH_SHORT).show();
    }
}
