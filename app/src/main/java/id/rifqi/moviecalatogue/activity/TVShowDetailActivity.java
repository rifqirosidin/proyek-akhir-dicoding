package id.rifqi.moviecalatogue.activity;

import androidx.appcompat.app.AppCompatActivity;
import id.rifqi.moviecalatogue.R;
import id.rifqi.moviecalatogue.model.Movie;
import id.rifqi.moviecalatogue.model.TVShow;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class TVShowDetailActivity extends AppCompatActivity {
    public static final String EXTRA_TVSHOW = "extra_tvshow";
    TextView tvName, tvRilis, tvDescription;
    ImageView imageView;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tvshow_detail);
        setTitle(getString(R.string.title_tvshow_detail));

        tvName = findViewById(R.id.tv_name_detail_tv_show);
        tvRilis = findViewById(R.id.tv_rilis_detail_tv_show);
        imageView = findViewById(R.id.img_poster_detail_tv_show);
        progressBar = findViewById(R.id.progressBar_detail_tv_show);
        tvDescription = findViewById(R.id.tv_description_detail_tv_show);

        progressBar.setVisibility(View.VISIBLE);
        final Handler handler = new Handler();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(3000);
                }catch (Exception e){

                }
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = getIntent();
                        TVShow tvShow = intent.getParcelableExtra(EXTRA_TVSHOW);
                        tvName.setText(tvShow.getTitle());
                        tvRilis.setText(tvShow.getRilis());
                        tvDescription.setText(tvShow.getDescription());
                        Picasso.get()
                                .load(Movie.urlPoster + tvShow.getPoster())
                                .into(imageView);
                        progressBar.setVisibility(View.GONE);
                    }
                });
            }
        }).start();
    }
}
