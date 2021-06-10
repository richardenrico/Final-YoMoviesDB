package com.tugas.yomoviedb.ui.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tugas.yomoviedb.R;
import com.tugas.yomoviedb.data.local.database.AppDatabase;
import com.tugas.yomoviedb.data.models.FavouriteMovie;
import com.tugas.yomoviedb.data.models.FavouriteTvShow;
import com.tugas.yomoviedb.ui.activities.DetailActivity;
import com.tugas.yomoviedb.ui.adapters.FavouriteMovieAdapter;
import com.tugas.yomoviedb.ui.adapters.FavouriteTvShowAdapter;
import com.tugas.yomoviedb.ui.adapters.clicklistener.OnFavouriteTvShowClickListener;

import java.util.ArrayList;
import java.util.List;

public class FavouriteTvShowFragment extends Fragment implements OnFavouriteTvShowClickListener {
    private AppDatabase database;
    private RecyclerView recyclerView;
    private FavouriteTvShowAdapter adapter;
    private List<FavouriteTvShow> favouriteTvShowList = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_favourite_tv_show, container, false);

        database = AppDatabase.getInstance(getActivity().getApplicationContext());
        loadData();
        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        setData();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        System.out.println("ON RESUME");
        loadData();
        setData();

    }

    private void setData() {
        adapter = new FavouriteTvShowAdapter(favouriteTvShowList);
        adapter.setClickListener(FavouriteTvShowFragment.this);
        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);
    }

    private void loadData() {
        database.favouriteDao().getListFavTvShow().observe(getActivity(), new Observer<List<FavouriteTvShow>>() {
            @Override
            public void onChanged(List<FavouriteTvShow> favouriteTvShows) {
                favouriteTvShowList = favouriteTvShows;
            }
        });
    }


    @Override
    public void onClick(FavouriteTvShow favouriteTvShow) {
        Intent intent = new Intent(getContext(), DetailActivity.class);
        intent.putExtra("ID", favouriteTvShow.getId());
        intent.putExtra("TYPE", "FAV_TV_SHOW");
        startActivity(intent);
    }
}