package com.tugas.yomoviedb.ui.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.tugas.yomoviedb.R;
import com.tugas.yomoviedb.data.models.favourite.FavouriteMovie;
import com.tugas.yomoviedb.ui.adapters.clicklistener.OnFavouriteMovieClickListener;

import java.util.List;

public class FavouriteMovieAdapter extends RecyclerView.Adapter<FavouriteMovieAdapter.ViewHolder> {
    private List<FavouriteMovie> favouriteMovieList;
    private OnFavouriteMovieClickListener clickListener;

    public FavouriteMovieAdapter(List<FavouriteMovie> favouriteMovieList) {
        this.favouriteMovieList = favouriteMovieList;
    }

    public void setClickListener(OnFavouriteMovieClickListener clickListener) {
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public FavouriteMovieAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item_linear, parent, false);
        return new FavouriteMovieAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavouriteMovieAdapter.ViewHolder holder, int position) {
        holder.onBindItemView(favouriteMovieList.get(position));
    }

    @Override
    public int getItemCount() {
        return favouriteMovieList.size();
    }

    public void appendList(List<FavouriteMovie> listToAppend) {
        favouriteMovieList.addAll(listToAppend);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        FavouriteMovie favouriteMovie;
        ImageView ivItemImg;
        TextView tvItemTitle;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivItemImg = itemView.findViewById(R.id.iv_item_image);
            tvItemTitle = itemView.findViewById(R.id.tv_item_title);
            itemView.setOnClickListener(this);
        }

        public void onBindItemView(FavouriteMovie favouriteMovie) {
            this.favouriteMovie = favouriteMovie;
            Glide.with(itemView.getContext())
                    .load(favouriteMovie.getPosterPath())
                    .into(ivItemImg);
            tvItemTitle.setText(favouriteMovie.getName());
        }

        @Override
        public void onClick(View v) {
            clickListener.onClick(favouriteMovie);
        }
    }
}
