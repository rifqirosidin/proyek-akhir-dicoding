package id.rifqi.moviecalatogue.ui.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import java.util.ArrayList;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import id.rifqi.moviecalatogue.R;
import id.rifqi.moviecalatogue.adapter.TVShowAdapter;
import id.rifqi.moviecalatogue.model.TVShow;
import id.rifqi.moviecalatogue.viewModel.TVShowViewModel;

public class TvShowFragment extends Fragment {

    RecyclerView rvMovieList;
    TVShowAdapter adapter;
    ProgressBar progressBar;
    TVShowViewModel tvShowViewModel;
    public  TvShowFragment() {

    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tv_show, container, false);
        rvMovieList = view.findViewById(R.id.rv_tv_show);
        progressBar = view.findViewById(R.id.progressBar_tvshow);
        adapter = new TVShowAdapter(getContext());
        rvMovieList.setHasFixedSize(true);
        rvMovieList.setLayoutManager(new LinearLayoutManager(getContext()));

        tvShowViewModel = ViewModelProviders.of(this).get(TVShowViewModel.class);
        tvShowViewModel.getTvShowList().observe(this, getTVShow);
        tvShowViewModel.setTvShowList();
        showLoading(true);

        return view;
    }

    public Observer<ArrayList<TVShow>> getTVShow = new Observer<ArrayList<TVShow>>() {
        @Override
        public void onChanged(ArrayList<TVShow> tvShow) {
            if (tvShow != null){
                adapter.setTVShowList(tvShow);
                rvMovieList.setAdapter(adapter);
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
