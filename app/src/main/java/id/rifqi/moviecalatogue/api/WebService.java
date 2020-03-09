package id.rifqi.moviecalatogue.api;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface WebService {

    String API_KEY = "94bbde36ee11cee4e4bfa34542bb3a96";

    @GET("discover/movie?api_key=" + API_KEY + "&language=en-US")
    Call<MovieResponse> getMovieData();

    @GET("discover/tv?api_key="+API_KEY+ "&language=en-US")
    Call<TVShowResponse> getTVShowData();

    @GET("search/movie?api_key="+API_KEY+ "&language=en-US")
    Call<MovieResponse> getSearchMovie(@Query("query") String movie);

    @GET("discover/movie?api_key=" + API_KEY)
    Call<MovieResponse> getReleaseMovieToday(@Query("primary_release_date.gte") String gteDate,
                                             @Query("primary_release_date.lte") String lteDate);
}
