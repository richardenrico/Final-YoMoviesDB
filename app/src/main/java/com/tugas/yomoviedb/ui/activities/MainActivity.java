package com.tugas.yomoviedb.ui.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toolbar;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.tugas.yomoviedb.R;
import com.tugas.yomoviedb.ui.fragments.FavouriteFragment;
import com.tugas.yomoviedb.ui.fragments.MovieFragment;
import com.tugas.yomoviedb.ui.fragments.TvShowFragment;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        bottomNavigationView.setSelectedItemId(R.id.page_movie);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment = null;
        String sortBy = null;
        switch (item.getItemId()) {
            case R.id.page_movie:
                setActionBar("Movies");
                sortBy = "now_playing";
                fragment = new MovieFragment();
                break;
            case R.id.page_tv_show:
                setActionBar("Tv Show");
                sortBy = "airing_today";
                fragment = new TvShowFragment();
                break;
            case R.id.page_favourite:
                setActionBar("Favourite");
                fragment = new FavouriteFragment();
                break;
        }

        if (fragment != null) {
            startFragment(fragment, sortBy);
        }

        return false;
    }


    private void setActionBar(String title) {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(title);
        }
    }

    private void startFragment(Fragment fragment, String bundle) {
        if (bundle != null) {
            Bundle sortBy = new Bundle();
            sortBy.putString("SORT_BY", bundle);
            fragment.setArguments(sortBy);
        }

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frame_layout, fragment)
                .commit();
    }

}