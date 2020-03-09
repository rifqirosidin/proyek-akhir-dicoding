package id.rifqi.moviecalatogue.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import id.rifqi.moviecalatogue.R;
import id.rifqi.moviecalatogue.adapter.SearchAdapter;
import id.rifqi.moviecalatogue.api.MovieResponse;
import id.rifqi.moviecalatogue.api.RetrofitClient;
import id.rifqi.moviecalatogue.api.WebService;
import id.rifqi.moviecalatogue.model.Movie;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.Toast;

import java.util.ArrayList;

public class SearchableActivity extends AppCompatActivity {

    RecyclerView rvSearch;

    private static final String  TAG = "SearchcableActivity";
    private SearchAdapter adapter;
    private String search = null;
    private String searchChange;
    private ProgressBar mProgress;
    ArrayList<Movie> movieArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchable);
        handleIntent(getIntent());

        rvSearch = findViewById(R.id.rv_search);
        adapter = new SearchAdapter(this);
        mProgress = findViewById(R.id.progressBar_search);



        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(search);
            ActionBar ab = getSupportActionBar();
            ab.setDisplayHomeAsUpEnabled(true);
        }

        mProgress.setVisibility(View.VISIBLE);
        rvSearch.setLayoutManager(new LinearLayoutManager(this));
        if (savedInstanceState != null) {
            movieArrayList = savedInstanceState.getParcelableArrayList("movies");
            prepare();
        } else {
            getsearchResult();
        }

    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState, @NonNull PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        outState.putParcelableArrayList("movies", new ArrayList<>(adapter.getMovies()));
    }

    public void getsearchResult()
    {
        if (searchChange == null) {
            WebService api = RetrofitClient.getClient().create(WebService.class);
            Call<MovieResponse> call = api.getSearchMovie(search);
            call.enqueue(new Callback<MovieResponse>() {
                @Override
                public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {

                    if (response.isSuccessful()){
                        mProgress.setVisibility(View.GONE);
                        movieArrayList = response.body().getResults();
                        adapter.setMovies(movieArrayList);
                        rvSearch.setAdapter(adapter);
                    }
                    if (movieArrayList.size() <= 0) {
                        Toast.makeText(SearchableActivity.this, "Tidak ada data", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<MovieResponse> call, Throwable t) {
                    Log.e(TAG,t.getMessage());
                }
            });
        } else {
            WebService api = RetrofitClient.getClient().create(WebService.class);
            Call<MovieResponse> call = api.getSearchMovie(searchChange);
            call.enqueue(new Callback<MovieResponse>() {
                @Override
                public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                    if (response.isSuccessful()){
                        mProgress.setVisibility(View.GONE);
                        movieArrayList = response.body().getResults();
                        adapter.setMovies(movieArrayList);
                        rvSearch.setAdapter(adapter);
                    }

                    if (movieArrayList.size() <= 0) {
                        Toast.makeText(SearchableActivity.this, "Tidak ada data", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<MovieResponse> call, Throwable t) {
                    Log.e(TAG,t.getMessage());
                }
            });
        }

    }

    public void prepare(){
        mProgress.setVisibility(View.GONE);
        adapter = new SearchAdapter(this);
        adapter.setMovies(movieArrayList);
        rvSearch.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.option_menu, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchChange = query;
                finish();
                getsearchResult();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
        return  true;
    }

    @Override
    protected void onNewIntent(Intent intent) {
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            search = intent.getStringExtra(SearchManager.QUERY);
            Log.d("search", search);
        }
    }
    @Override
    public void onDestroy() {
        super.onDestroy();

            mProgress.setVisibility(View.GONE);


    }
}
