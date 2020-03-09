package id.rifqi.moviecalatogue.activity;

import androidx.appcompat.app.AppCompatActivity;
import id.rifqi.moviecalatogue.R;
import id.rifqi.moviecalatogue.model.Movie;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class MovieDetailActivity extends AppCompatActivity {
    TextView tvTitle, tvRilis, tvDescription;
    ImageView imgPoster;
    ProgressBar progressBar;

    public static final String EXTRA_MOVIE = "extra_movie";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        setTitle(getString(R.string.title_movie_detail));

        tvTitle = findViewById(R.id.tv_detail_title_movie);
        tvRilis = findViewById(R.id.tv_detatil_rilis_movie);
        imgPoster = findViewById(R.id.img_detail_movie);
        tvDescription = findViewById(R.id.tv_detail_description_movie);
        progressBar = findViewById(R.id.progressBar_movie_deatil);

        progressBar.setVisibility(View.VISIBLE);
        final Handler handler = new Handler();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                   Thread.sleep(3000);
                } catch (Exception e){
                    Log.e("error", e.getMessage());
                }
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = getIntent();
                        Movie movie = intent.getParcelableExtra(EXTRA_MOVIE);
                        tvTitle.setText(movie.getTitle());
                        tvRilis.setText(movie.getRilis());
                        tvDescription.setText(movie.getDescription());
                        Picasso.get()
                                .load(Movie.urlPoster + movie.getPoster())
                                .into(imgPoster);
                        progressBar.setVisibility(View.GONE);
                    }
                });

            }
        }).start();

    }

}
