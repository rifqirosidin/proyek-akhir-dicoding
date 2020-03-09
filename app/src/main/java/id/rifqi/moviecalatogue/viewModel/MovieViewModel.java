package id.rifqi.moviecalatogue.viewModel;

import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import id.rifqi.moviecalatogue.api.RetrofitClient;
import id.rifqi.moviecalatogue.api.WebService;
import id.rifqi.moviecalatogue.model.Movie;
import id.rifqi.moviecalatogue.api.MovieResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieViewModel extends ViewModel {

    private MutableLiveData<ArrayList<Movie>> listMovie = new MutableLiveData<>();
    ArrayList<Movie> listItem = new ArrayList<>();
    Date dateNow = new Date();
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    String date = dateFormat.format(dateNow);

    public void setMovie(final String movie)
    {
        WebService movieAPI = RetrofitClient.getClient().create(WebService.class);
        Call<MovieResponse> call = movieAPI.getMovieData();
        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {

                if (response.isSuccessful()){
                    ArrayList<Movie> movieData = response.body().getResults();
                    listItem.addAll(movieData);
                    listMovie.postValue(listItem);
                } else {
                    Log.d("msg", "response gagal");
                }
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                Log.e("error", t.getMessage());
            }
        });
    }

    public LiveData<ArrayList<Movie>> getListMovie() {
        return listMovie;
    }
}
