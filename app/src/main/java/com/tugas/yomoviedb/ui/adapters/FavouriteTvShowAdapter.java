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
import com.tugas.yomoviedb.data.models.favourite.FavouriteTvShow;
import com.tugas.yomoviedb.ui.adapters.clicklistener.OnFavouriteTvShowClickListener;

import java.util.List;

public class FavouriteTvShowAdapter extends RecyclerView.Adapter<FavouriteTvShowAdapter.ViewHolder> {
    private final List<FavouriteTvShow> favouriteTvShows;
    private OnFavouriteTvShowClickListener clickListener;

    public FavouriteTvShowAdapter(List<FavouriteTvShow> favouriteTvShows) {
        this.favouriteTvShows = favouriteTvShows;
    }

    public void setClickListener(OnFavouriteTvShowClickListener clickListener) {
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public FavouriteTvShowAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item_linear, parent, false);
        return new FavouriteTvShowAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavouriteTvShowAdapter.ViewHolder holder, int position) {
        holder.onBindItemView(favouriteTvShows.get(position));
    }

    @Override
    public int getItemCount() {
        return favouriteTvShows.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        FavouriteTvShow favouriteTvShow;
        ImageView ivItemImg;
        TextView tvItemTitle;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            ivItemImg = itemView.findViewById(R.id.iv_item_image);
            tvItemTitle = itemView.findViewById(R.id.tv_item_title);
            itemView.setOnClickListener(this);
        }

        public void onBindItemView(FavouriteTvShow favouriteTvShow) {
            this.favouriteTvShow = favouriteTvShow;
            Glide.with(itemView.getContext())
                    .load(favouriteTvShow.getPosterPath())
                    .into(ivItemImg);
            tvItemTitle.setText(favouriteTvShow.getName());
        }

        @Override
        public void onClick(View v) {
            clickListener.onClick(favouriteTvShow);
        }
    }
}
