package id.rifqi.moviecalatogue.ui.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import java.util.ArrayList;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import id.rifqi.moviecalatogue.R;
import id.rifqi.moviecalatogue.adapter.MovieAdapter;
import id.rifqi.moviecalatogue.api.MovieResponse;
import id.rifqi.moviecalatogue.api.RetrofitClient;
import id.rifqi.moviecalatogue.api.WebService;
import id.rifqi.moviecalatogue.model.Movie;
import id.rifqi.moviecalatogue.viewModel.MovieViewModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A placeholder fragment containing a simple view.
 */
public class MovieFragment extends Fragment  {

    MovieAdapter movieAdapter;
    RecyclerView rvMovieList;
    MovieViewModel movieViewModel;
    ProgressBar progressBar;
    private String search = "tes";
    private static final String  TAG = "SearchcableActivity";
    private MovieAdapter adapter;
    private ProgressDialog mProgress;
    ArrayList<Movie> movieArrayList = new ArrayList<>();

    public  MovieFragment() {

    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_movie, container, false);
        progressBar = root.findViewById(R.id.progressBar_movie);
        movieAdapter = new MovieAdapter(getContext());
        rvMovieList = root.findViewById(R.id.rv_movie_list);
        rvMovieList.setHasFixedSize(true);
        rvMovieList.setLayoutManager(new LinearLayoutManager(getContext()));


        movieViewModel = ViewModelProviders.of(this).get(MovieViewModel.class);
        movieViewModel.getListMovie().observe(this, getMovie);
        movieViewModel.setMovie("EXTRA_MOVIE");
        showLoading(true);

        return root;
    }

   private Observer<ArrayList<Movie>> getMovie = new Observer<ArrayList<Movie>>() {
       @Override
       public void onChanged(ArrayList<Movie> movies) {
           if (movies != null){
               movieAdapter.setMovieList(movies);
               rvMovieList.setAdapter(movieAdapter);
           }
           showLoading(false);
       }
   };

    private void showLoading(Boolean state)
    {
        if (state){
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }

    }

}