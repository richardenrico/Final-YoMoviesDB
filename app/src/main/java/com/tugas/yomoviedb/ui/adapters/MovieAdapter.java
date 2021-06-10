package com.tugas.yomoviedb.ui.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.tugas.yomoviedb.ImageSize;
import com.tugas.yomoviedb.R;
import com.tugas.yomoviedb.data.models.movie.Movie;
import com.tugas.yomoviedb.ui.adapters.clicklistener.OnMovieClickListener;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {
    private final List<Movie> movieList;
    private OnMovieClickListener clickListener;

    public MovieAdapter(List<Movie> movieList) {
        this.movieList = movieList;
    }

    public void setClickListener(OnMovieClickListener clickListener) {
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public MovieAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item_grid, parent, false);
        return new MovieAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieAdapter.ViewHolder holder, int position) {
        holder.onBindItemView(movieList.get(position));
    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    public void appendList(List<Movie> listToAppend) {
        movieList.addAll(listToAppend);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        Movie movie;
        ImageView ivItemImg;
        TextView tvItemTitle;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivItemImg = itemView.findViewById(R.id.iv_item_image);
            tvItemTitle = itemView.findViewById(R.id.tv_item_title);
            itemView.setOnClickListener(this);
        }

        public void onBindItemView(Movie movie) {
            this.movie = movie;
            Glide.with(itemView.getContext())
                    .load(movie.getPosterPath(ImageSize.W154))
                    .into(ivItemImg);
            tvItemTitle.setText(movie.getName());
        }

        @Override
        public void onClick(View v) {
            clickListener.onClick(movie);
        }
    }
}
