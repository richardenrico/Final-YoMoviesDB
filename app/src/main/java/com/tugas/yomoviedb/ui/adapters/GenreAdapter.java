package com.tugas.yomoviedb.ui.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tugas.yomoviedb.R;
import com.tugas.yomoviedb.data.models.Genre;

import java.util.List;

public class GenreAdapter extends RecyclerView.Adapter<GenreAdapter.ViewHolder>{
    private List<Genre> genreList;

    public GenreAdapter(List<Genre> genreList) {
        this.genreList = genreList;
    }

    @NonNull
    @Override
    public GenreAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item_genre, parent, false);
        return new GenreAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GenreAdapter.ViewHolder holder, int position) {
        holder.onBindItemView(genreList.get(position));
    }

    @Override
    public int getItemCount() {
        return genreList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        Genre genre;
        TextView tvGenre;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvGenre = itemView.findViewById(R.id.tv_genre);

        }

        public void onBindItemView(Genre genre) {
            this.genre = genre;
            tvGenre.setText(genre.getName());
        }
    }
}
