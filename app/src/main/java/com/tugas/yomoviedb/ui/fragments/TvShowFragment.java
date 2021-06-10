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
import com.tugas.yomoviedb.data.api.repository.TvShowRepository;
import com.tugas.yomoviedb.data.api.repository.callback.OnTvSearchCallback;
import com.tugas.yomoviedb.data.api.repository.callback.OnTvShowCallback;
import com.tugas.yomoviedb.data.models.tvshow.TvShow;
import com.tugas.yomoviedb.ui.activities.DetailActivity;
import com.tugas.yomoviedb.ui.adapters.TvShowAdapter;
import com.tugas.yomoviedb.ui.adapters.clicklistener.OnTvShowClickListener;

import java.util.List;

public class TvShowFragment extends Fragment
        implements OnTvShowClickListener,
        SwipeRefreshLayout.OnRefreshListener,
        SearchView.OnQueryTextListener {

    private SwipeRefreshLayout refreshLayout;
    private RecyclerView recyclerView;
    private TvShowAdapter adapter;
    private TvShowRepository repository;
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
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tv_show, container, false);

        refreshLayout = view.findViewById(R.id.swl_tv_show);
        recyclerView = view.findViewById(R.id.recycler_view);
        repository = TvShowRepository.getInstance();
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
            repository.getTvShow(page, new OnTvShowCallback() {
                @Override
                public void onSuccess(int page, List<TvShow> tvShowList) {
                    if (adapter == null) {
                        adapter = new TvShowAdapter(tvShowList);
                        adapter.setClickListener(TvShowFragment.this);
                        adapter.notifyDataSetChanged();
                        recyclerView.setAdapter(adapter);
                        clEmpty.setVisibility(View.GONE);
                        recyclerView.setVisibility(View.VISIBLE);
                    } else {
                        adapter.appendList(tvShowList);
                    }
                    currentPage = page;
                    isFetching = false;
                    refreshLayout.setRefreshing(false);
                }

                @Override
                public void onFailure(String message) {
                    Log.d("ERROR", "onFailure:" + message);
                    clEmpty.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.GONE);
                }
            });
        } else {
            repository.searchTv(query, page, new OnTvSearchCallback() {
                @Override
                public void onSuccess(List<TvShow> tvShowsList, String msg, int page) {
                    if (adapter == null) {
                        adapter = new TvShowAdapter(tvShowsList);
                        adapter.setClickListener(TvShowFragment.this);
                        adapter.notifyDataSetChanged();
                        recyclerView.setAdapter(adapter);
                    } else {
                        adapter.appendList(tvShowsList);
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
        return true;
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
    public void onClick(TvShow tvShow) {
        Intent intent = new Intent(getContext(), DetailActivity.class);
        intent.putExtra("ID", tvShow.getId());
        intent.putExtra("TYPE", "TV_SHOW");
        startActivity(intent);
    }
}