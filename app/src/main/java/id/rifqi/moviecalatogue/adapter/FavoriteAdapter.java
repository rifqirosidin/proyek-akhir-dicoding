package id.rifqi.moviecalatogue.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import id.rifqi.moviecalatogue.CustomOnItemClickListener;
import id.rifqi.moviecalatogue.DatabaseHelper.MovieHelper;
import id.rifqi.moviecalatogue.R;

import id.rifqi.moviecalatogue.activity.MovieDetailActivity;
import id.rifqi.moviecalatogue.model.Favorite;
import id.rifqi.moviecalatogue.model.Movie;
import id.rifqi.moviecalatogue.model.TVShow;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.ViewHolder> {
    Context context;

    ArrayList<Movie> listMovieFavorite = new ArrayList<>();


    public FavoriteAdapter(Context context) {
        this.context = context;
    }

    public ArrayList<Movie> getListMovieFavorite() {
        return listMovieFavorite;
    }

    public void setListMovieFavorite(ArrayList<Movie> listMovieFavorite) {

        this.listMovieFavorite = listMovieFavorite;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public FavoriteAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.favorite_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteAdapter.ViewHolder holder, final int position) {

        final Movie movie = getListMovieFavorite().get(position);
        Log.d("movie", String.valueOf(movie.getId()));
        holder.tvTitle.setText(movie.getTitle());
        holder.tvRilis.setText(movie.getRilis());
//        holder.tvOverview.setText(getListMovieFavorite().get(position).getDescription());
        Picasso.get()
                .load(Movie.urlPoster + movie.getPoster())
                .placeholder(R.drawable.ic_image_default)
                .error(R.drawable.ic_image_default)
                .into(holder.poster);

        holder.iconDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDeleteDialog(String.valueOf(movie.getId()), position);

            }
        });
    }

    @Override
    public int getItemCount() {
        return getListMovieFavorite().size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView tvTitle, tvRilis, tvOverview;
        ImageView poster, iconDelete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv_title_favorite);
            tvRilis = itemView.findViewById(R.id.tv_rilis_favorite);
            poster = itemView.findViewById(R.id.img_poster_favorite);
            iconDelete = itemView.findViewById(R.id.icon_delete_favorite);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Movie movie = getListMovieFavorite().get(getAdapterPosition());

            Intent intent = new Intent(view.getContext(), MovieDetailActivity.class);
            intent.putExtra(MovieDetailActivity.EXTRA_MOVIE, movie);
            view.getContext().startActivity(intent);
        }
    }

    public void showDeleteDialog(final String id, final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(R.string.message_delete);
        builder.setMessage(R.string.message_detail_delete);

        builder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                deleteItem(id, position);
            }
        }).setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void deleteItem(String id, int position) {
        MovieHelper movieHelper = new MovieHelper(context);
        movieHelper.deleteById(id);
        listMovieFavorite.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, listMovieFavorite.size());

    }

}
