package com.tugas.yomoviedb.ui.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.appcompat.widget.SearchView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.tugas.yomoviedb.R;
import com.tugas.yomoviedb.data.api.repository.MovieRepository;
import com.tugas.yomoviedb.data.api.repository.TvShowRepository;
import com.tugas.yomoviedb.data.api.repository.callback.OnMovieCallback;
import com.tugas.yomoviedb.data.api.repository.callback.OnMovieSearchCallback;
import com.tugas.yomoviedb.data.api.repository.callback.OnTvSearchCallback;
import com.tugas.yomoviedb.data.api.repository.callback.OnTvShowCallback;
import com.tugas.yomoviedb.data.models.movie.Movie;
import com.tugas.yomoviedb.data.models.tvshow.TvShow;
import com.tugas.yomoviedb.ui.activities.DetailActivity;
import com.tugas.yomoviedb.ui.adapters.MovieAdapter;
import com.tugas.yomoviedb.ui.adapters.TvShowAdapter;
import com.tugas.yomoviedb.ui.adapters.clicklistener.OnMovieClickListener;
import com.tugas.yomoviedb.ui.adapters.clicklistener.OnTvShowClickListener;

import java.util.List;

public class MovieFragment extends Fragment
        implements OnMovieClickListener,
        SwipeRefreshLayout.OnRefreshListener,
        SearchView.OnQueryTextListener{

    private SwipeRefreshLayout refreshLayout;
    private RecyclerView recyclerView;
    private MovieAdapter adapter;
    private MovieRepository repository;
    private boolean isFetching;
    private int currentPage = 1;
    private ConstraintLayout clEmpty;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        inflater.inflate(R.menu.action_bar_menu, menu);
        MenuItem item = menu.findItem(R.id.item_search);
        SearchView searchView = (SearchView) item.getActionView();
        setSearchView(searchView);

    }

    private void setSearchView(SearchView searchView) {
        searchView.setQueryHint("Type here...");
        searchView.setOnQueryTextListener(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this
        View view = inflater.inflate(R.layout.fragment_movie, container, false);

        refreshLayout = view.findViewById(R.id.swl_movie);
        recyclerView = view.findViewById(R.id.recycler_view);
        repository = MovieRepository.getInstance();
        clEmpty = view.findViewById(R.id.cl_empty);
        getRepositoryData("", currentPage);
        onScrollListener();
        refreshLayout.setOnRefreshListener(this);

        return view;
    }

    private void onScrollListener() {
        final GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 3);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                int totalItem = layoutManager.getItemCount();
                int visibleItem = layoutManager.getChildCount();
                int firstVisibleItem = layoutManager.findFirstVisibleItemPosition();
                if (firstVisibleItem + visibleItem >= totalItem / 2) {
                    if (!isFetching) {
                        isFetching = true;
                        currentPage++;
                        getRepositoryData("", currentPage);
                        isFetching = false;
                    }
                }
            }
        });
    }

    private void getRepositoryData(String query, int page) {
        isFetching = true;
        if (query.equals("")) {
            repository.getMovie(page, new OnMovieCallback() {
                @Override
                public void onSuccess(int page, List<Movie> movieList) {
                    if (adapter == null) {
                        adapter = new MovieAdapter(movieList);
                        adapter.setClickListener(MovieFragment.this);
                        adapter.notifyDataSetChanged();
                        recyclerView.setAdapter(adapter);
                        clEmpty.setVisibility(View.GONE);
                        recyclerView.setVisibility(View.VISIBLE);
                    } else {
                        adapter.appendList(movieList);
                    }
                    currentPage = page;
                    isFetching = false;
                    refreshLayout.setRefreshing(false);
                }

                @Override
                public void onFailure(String message) {
                    clEmpty.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.GONE);
                    Log.d("ERROR", "onFailure:" + message);
                }
            });
        } else {
            repository.searchMovie(query, page, new OnMovieSearchCallback() {
                @Override
                public void onSuccess(List<Movie> movieList, String msg, int page) {
                    if (adapter == null) {
                        adapter = new MovieAdapter(movieList);
                        adapter.setClickListener(MovieFragment.this);
                        adapter.notifyDataSetChanged();
                        recyclerView.setAdapter(adapter);
                    } else {
                        adapter.appendList(movieList);
                    }
                    currentPage = page;
                    isFetching = false;
                    refreshLayout.setRefreshing(false);
                }

                @Override
                public void onFailure(String msg) {

                }
            });
        }
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        if (newText.length() > 0) {
            adapter = null;
            getRepositoryData(newText, currentPage);
        } else {
            adapter = null;
            getRepositoryData("", currentPage);
        }
        return true;
    }

    @Override
    public void onRefresh() {
        adapter = null;
        getRepositoryData("", currentPage);
    }

    @Override
    public void onClick(Movie movie) {
        Intent intent = new Intent(getContext(), DetailActivity.class);
        intent.putExtra("ID", movie.getId());
        intent.putExtra("TYPE", "MOVIE");
        startActivity(intent);
    }
}