package com.tugas.yomoviedb.ui.activities;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.tugas.yomoviedb.ImageSize;
import com.tugas.yomoviedb.R;
import com.tugas.yomoviedb.data.api.repository.MovieRepository;
import com.tugas.yomoviedb.data.api.repository.TvShowRepository;
import com.tugas.yomoviedb.data.api.repository.callback.OnMovieDetailCallback;
import com.tugas.yomoviedb.data.api.repository.callback.OnTvDetailCallback;
import com.tugas.yomoviedb.data.models.Detail;
import com.tugas.yomoviedb.data.models.movie.Movie;
import com.tugas.yomoviedb.data.models.tvshow.TvShow;

public class DetailActivity extends AppCompatActivity {
    private int id;
    private TvShowRepository tvShowRepository;
    private MovieRepository movieRepository;
    private ImageView ivBackdrop, ivPoster;
    private TextView tvTitle, tvSynopsis, tvExpandableBtn;
    private RatingBar rbRate;
    private Toolbar toolbar;
    private TvShow tvShow;
    private Movie movie;
    private String type;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        setActionBar("");

        ivBackdrop = findViewById(R.id.iv_backdrop_img);
        ivPoster = findViewById(R.id.iv_poster_img);
        tvTitle = findViewById(R.id.tv_item_title);
        tvSynopsis = findViewById(R.id.tv_item_synopsis);
        tvExpandableBtn = findViewById(R.id.tv_expandable_btn);

        tvSynopsis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tvExpandableBtn.getText().toString().equalsIgnoreCase("more")) {
                    tvSynopsis.setMaxLines(Integer.MAX_VALUE);
                    tvExpandableBtn.setText("less");
                } else {
                    tvSynopsis.setMaxLines(2);
                    tvExpandableBtn.setText("more");
                }
            }
        });

        tvExpandableBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tvExpandableBtn.getText().toString().equalsIgnoreCase("more")) {
                    tvSynopsis.setMaxLines(Integer.MAX_VALUE);
                    tvExpandableBtn.setText("less");
                } else {
                    tvSynopsis.setMaxLines(2);
                    tvExpandableBtn.setText("more");
                }
            }
        });

        if (getIntent() != null) {
            id = getIntent().getIntExtra("ID", 0);
            type = getIntent().getStringExtra("TYPE");

        }

        tvShowRepository = TvShowRepository.getInstance();
        movieRepository = MovieRepository.getInstance();
        
    }

    private void setActionBar(String title) {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(title);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        loadData(type, id);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.detail_menu, menu);
        // TODO: switch favourite button state
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.add_to_favourite:
//                if (helper.isFavorite(id)) {
//                    if (helper.delete(id) > 0) {
//                        // TODO: Set favorite button state
//                    }
//                } else {
//                    if (helper.insert(tvShow) > 0) {
//                        // TODO: Set favorite button state
//                    }
//                }
                break;
        }
        
        return super.onOptionsItemSelected(item);
    }

    private void loadData(String type, int id) {
        switch (type){
            case "MOVIE":
                movieRepository.getMovieDetail(id, new OnMovieDetailCallback() {
                    @Override
                    public void onSuccess(Movie movie, String message) {
                        onMovieBindView(movie);
                    }

                    @Override
                    public void onFailure(String message) {

                    }
                });
                break;
            case "TV_SHOW":
                tvShowRepository.getTvDetail(id, new OnTvDetailCallback() {
                    @Override
                    public void onSuccess(TvShow tvShow, String message) {
                        onTvBindView(tvShow);
                    }

                    @Override
                    public void onFailure(String message) {

                    }
                });
                break;
        }

    }

    private void onTvBindView(TvShow tvShow) {
        this.tvShow = tvShow;
        setActionBar(tvShow.getName());
        Glide.with(this)
                .load(tvShow.getBackdropPath(ImageSize.W500))
                .into(ivBackdrop);
        Glide.with(this)
                .load(tvShow.getPosterPath(ImageSize.W154))
                .into(ivPoster);
        tvTitle.setText(tvShow.getName());
        tvSynopsis.setText(tvShow.getOverview());
    }

    private void onMovieBindView(Movie movie) {
        this.movie = movie;
        setActionBar(movie.getName());
        Glide.with(this)
                .load(movie.getBackdropPath(ImageSize.W500))
                .into(ivBackdrop);
        Glide.with(this)
                .load(movie.getPosterPath(ImageSize.W154))
                .into(ivPoster);
        tvTitle.setText(movie.getName());
        tvSynopsis.setText(movie.getOverview());
    }

}