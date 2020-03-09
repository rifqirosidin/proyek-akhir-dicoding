package id.rifqi.moviecalatogue.adapter;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteException;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import id.rifqi.moviecalatogue.DatabaseHelper.DatabaseContract;
import id.rifqi.moviecalatogue.DatabaseHelper.MovieHelper;
import id.rifqi.moviecalatogue.R;
import id.rifqi.moviecalatogue.activity.MovieDetailActivity;
import id.rifqi.moviecalatogue.model.Movie;

import static id.rifqi.moviecalatogue.DatabaseHelper.DatabaseContract.MovieColumn.OVERVIEW;
import static id.rifqi.moviecalatogue.DatabaseHelper.DatabaseContract.MovieColumn.POSTER;
import static id.rifqi.moviecalatogue.DatabaseHelper.DatabaseContract.MovieColumn.RILIS;
import static id.rifqi.moviecalatogue.DatabaseHelper.DatabaseContract.MovieColumn.TITLE;


public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {

    private Context context;
    ArrayList<Movie> MovieList;

    public MovieAdapter(Context context) {
        this.context = context;

    }

    public void setMovieList(ArrayList<Movie> movieList) {
        MovieList = movieList;
        notifyDataSetChanged();
    }

    public ArrayList<Movie> getMovieList() {
        return MovieList;
    }

    @NonNull
    @Override
    public MovieAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.movie_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieAdapter.ViewHolder holder, final int position) {

        holder.tvTitle.setText(getMovieList().get(position).getTitle());
        holder.tvRilis.setText(getMovieList().get(position).getRilis());
        Picasso.get()
                .load(Movie.urlPoster + getMovieList().get(position).getPoster())
                .placeholder(R.drawable.ic_image_default)
                .error(R.drawable.ic_image_default)
                .into(holder.poster);


        holder.btnFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Movie movie = getMovieList().get(position);
                MovieHelper movieHelper = new MovieHelper(context);
                try {

                    movieHelper.insert(movie);

                    Toast.makeText(view.getContext(),context.getString(R.string.message_add_favorite)+" " + movie.getTitle(), Toast.LENGTH_SHORT).show();
                }catch (SQLiteException e){
                    Toast.makeText(view.getContext(), R.string.message_sqlite_error, Toast.LENGTH_SHORT).show();

                }

            }
        });

    }

    @Override
    public int getItemCount() {
        return getMovieList().size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tvTitle, tvRilis;
        ImageView poster;
        Button btnFavorite;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvRilis = itemView.findViewById(R.id.tv_rilis);
            tvTitle = itemView.findViewById(R.id.tv_title);
            poster = itemView.findViewById(R.id.imageView_poster);
            btnFavorite = itemView.findViewById(R.id.btn_favorite);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Movie movie = getMovieList().get(getAdapterPosition());
            Intent intent = new Intent(view.getContext(), MovieDetailActivity.class);
            intent.putExtra(MovieDetailActivity.EXTRA_MOVIE, movie);
            view.getContext().startActivity(intent);

        }
    }
}
