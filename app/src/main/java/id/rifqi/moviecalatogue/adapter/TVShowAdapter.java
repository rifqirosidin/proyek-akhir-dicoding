package id.rifqi.moviecalatogue.adapter;

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
import id.rifqi.moviecalatogue.DatabaseHelper.MovieHelper;
import id.rifqi.moviecalatogue.DatabaseHelper.TVShowHelper;
import id.rifqi.moviecalatogue.R;
import id.rifqi.moviecalatogue.activity.TVShowDetailActivity;
import id.rifqi.moviecalatogue.model.TVShow;

public class TVShowAdapter extends RecyclerView.Adapter<TVShowAdapter.ViewHolder> {
    private Context context;
    ArrayList<TVShow> TVShowList;

    public TVShowAdapter(Context context) {
        this.context = context;
    }

    public ArrayList<TVShow> getTVShowList() {
        return TVShowList;
    }

    public void setTVShowList(ArrayList<TVShow> TVShowList) {
        this.TVShowList = TVShowList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TVShowAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.movie_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

        holder.tvName.setText(getTVShowList().get(position).getTitle());
        holder.tvRilis.setText(getTVShowList().get(position).getRilis());
        Picasso.get().load(TVShow.URL_POSTER + getTVShowList().get(position).getPoster()).into(holder.poster);

        holder.btnFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TVShow movie = getTVShowList().get(position);
                TVShowHelper movieHelper = new TVShowHelper(context);
                try {
                    movieHelper.insert(movie);
                    notifyDataSetChanged();
                    Toast.makeText(view.getContext(),context.getString(R.string.message_add_favorite) + " " + movie.getTitle(), Toast.LENGTH_SHORT).show();
                }catch (SQLiteException e){
                    Toast.makeText(view.getContext(), R.string.message_sqlite_error, Toast.LENGTH_SHORT).show();
                    Log.e("error", e.getMessage());
                }

            }
        });

    }

    @Override
    public int getItemCount() {
        return getTVShowList().size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tvName, tvRilis;
        ImageView poster;
        Button btnFavorite;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvRilis = itemView.findViewById(R.id.tv_rilis);
            tvName = itemView.findViewById(R.id.tv_title);
            poster = itemView.findViewById(R.id.imageView_poster);
            btnFavorite = itemView.findViewById(R.id.btn_favorite);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            TVShow tvShow = getTVShowList().get(getAdapterPosition());
            Intent intent = new Intent(view.getContext(), TVShowDetailActivity.class);
            intent.putExtra(TVShowDetailActivity.EXTRA_TVSHOW, tvShow);
            view.getContext().startActivity(intent);
        }
    }
}
