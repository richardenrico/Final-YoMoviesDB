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
import com.tugas.yomoviedb.data.models.tvshow.TvShow;
import com.tugas.yomoviedb.ui.adapters.clicklistener.OnTvShowClickListener;

import java.util.List;

public class TvShowAdapter extends RecyclerView.Adapter<TvShowAdapter.ViewHolder> {
    private List<TvShow> tvShowList;
    private OnTvShowClickListener clickListener;

    public TvShowAdapter(List<TvShow> tvShowList) {
        this.tvShowList = tvShowList;
    }

    public void setClickListener(OnTvShowClickListener clickListener) {
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item_grid, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.onBindItemView(tvShowList.get(position));
    }

    @Override
    public int getItemCount() {
        return tvShowList.size();
    }

    public void appendList(List<TvShow> listToAppend) {
        tvShowList.addAll(listToAppend);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TvShow tvShow;
        ImageView ivItemImg;
        TextView tvItemTitle;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivItemImg = itemView.findViewById(R.id.iv_item_image);
            tvItemTitle = itemView.findViewById(R.id.tv_item_title);
            itemView.setOnClickListener(this);
        }

        public void onBindItemView(TvShow tvShow) {
            this.tvShow = tvShow;
            Glide.with(itemView.getContext())
                    .load(tvShow.getPosterPath(ImageSize.W154))
                    .into(ivItemImg);
            tvItemTitle.setText(tvShow.getName());
        }


        @Override
        public void onClick(View v) {
            clickListener.onClick(tvShow);
        }
    }
}

