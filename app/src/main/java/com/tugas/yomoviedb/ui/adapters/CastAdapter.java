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
import com.tugas.yomoviedb.data.models.Cast;

import java.util.List;

public class CastAdapter extends RecyclerView.Adapter<CastAdapter.ViewHolder>{
    private List<Cast> castList;

    public CastAdapter(List<Cast> castList) {
        this.castList = castList;
    }

    @NonNull
    @Override
    public CastAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item_cast, parent, false);
        return new CastAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CastAdapter.ViewHolder holder, int position) {
        holder.onBindItemView(castList.get(position));
    }

    @Override
    public int getItemCount() {
        return castList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        Cast cast;
        ImageView ivCastImg;
        TextView tvCastName, tvCastChar;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivCastImg = itemView.findViewById(R.id.iv_cast);
            tvCastName = itemView.findViewById(R.id.tv_cast_name);
            tvCastChar = itemView.findViewById(R.id.tv_cast_character);
        }

        public void onBindItemView(Cast cast) {
            this.cast = cast;
            Glide.with(itemView.getContext())
                    .load(cast.getProfilePath(ImageSize.W92))
                    .into(ivCastImg);
            tvCastName.setText(cast.getName());
            tvCastChar.setText(cast.getCharacter());
        }
    }
}
