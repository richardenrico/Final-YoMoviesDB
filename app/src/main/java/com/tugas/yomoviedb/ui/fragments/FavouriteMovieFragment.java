package com.tugas.yomoviedb.ui.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tugas.yomoviedb.R;
import com.tugas.yomoviedb.data.local.database.AppDatabase;
import com.tugas.yomoviedb.data.models.favourite.FavouriteMovie;
import com.tugas.yomoviedb.ui.activities.DetailActivity;
import com.tugas.yomoviedb.ui.adapters.FavouriteMovieAdapter;
import com.tugas.yomoviedb.ui.adapters.clicklistener.OnFavouriteMovieClickListener;

import java.util.List;
import java.util.Objects;

public class FavouriteMovieFragment extends Fragment implements OnFavouriteMovieClickListener{
    private AppDatabase database;
    private RecyclerView recyclerView;
    private FavouriteMovieAdapter adapter;
    private ConstraintLayout clEmpty;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_favourite_movie, container, false);

        database = AppDatabase.getInstance(requireActivity().getApplicationContext());
        loadData();
        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        clEmpty = view.findViewById(R.id.cl_empty);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        System.out.println("ON RESUME");
        loadData();

    }
    private void loadData() {
        database.favouriteDao().getListFavMovie().observe(requireActivity(), favouriteMovies -> {
            if (favouriteMovies.size() == 0) {
                clEmpty.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.GONE);
            } else {
                adapter = new FavouriteMovieAdapter(favouriteMovies);
                adapter.setClickListener(FavouriteMovieFragment.this);
                adapter.notifyDataSetChanged();
                recyclerView.setAdapter(adapter);
                clEmpty.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public void onClick(FavouriteMovie favouriteMovie) {
        Intent intent = new Intent(getContext(), DetailActivity.class);
        intent.putExtra("ID", favouriteMovie.getId());
        intent.putExtra("TYPE", "FAV_MOVIE");
        startActivity(intent);
    }
}