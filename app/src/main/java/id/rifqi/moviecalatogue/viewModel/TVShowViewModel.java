package id.rifqi.moviecalatogue.viewModel;

import android.util.Log;

import java.util.ArrayList;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import id.rifqi.moviecalatogue.api.RetrofitClient;
import id.rifqi.moviecalatogue.api.TVShowResponse;
import id.rifqi.moviecalatogue.api.WebService;
import id.rifqi.moviecalatogue.model.TVShow;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TVShowViewModel extends ViewModel {

    private MutableLiveData<ArrayList<TVShow>> tvShowList = new MutableLiveData<>();
    ArrayList<TVShow> listItem = new ArrayList<>();

    public  void setTvShowList()
    {
        WebService tvShowAPI = RetrofitClient.getClient().create(WebService.class);
        Call<TVShowResponse> call = tvShowAPI.getTVShowData();
        call.enqueue(new Callback<TVShowResponse>() {
            @Override
            public void onResponse(Call<TVShowResponse> call, Response<TVShowResponse> response) {

                if (response.isSuccessful()){
                    ArrayList<TVShow> movieData = response.body().getResultsTVShow();
                    listItem.addAll(movieData);
                    tvShowList.postValue(listItem);
                } else {
                    Log.d("msg", "response gagal");
                }
            }

            @Override
            public void onFailure(Call<TVShowResponse> call, Throwable t) {
                Log.e("error", t.getMessage());
            }
        });
    }
    public LiveData<ArrayList<TVShow>> getTvShowList() {
        return tvShowList;
    }
}

