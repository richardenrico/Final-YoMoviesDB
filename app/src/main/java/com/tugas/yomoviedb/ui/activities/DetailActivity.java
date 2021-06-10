package com.tugas.yomoviedb.ui.activities;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import android.graphics.PorterDuff;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.tugas.yomoviedb.ImageSize;
import com.tugas.yomoviedb.R;
import com.tugas.yomoviedb.data.api.repository.MovieRepository;
import com.tugas.yomoviedb.data.api.repository.TvShowRepository;
import com.tugas.yomoviedb.data.api.repository.callback.OnMovieDetailCallback;
import com.tugas.yomoviedb.data.api.repository.callback.OnTvDetailCallback;
import com.tugas.yomoviedb.data.local.database.AppDatabase;
import com.tugas.yomoviedb.data.models.FavouriteMovie;
import com.tugas.yomoviedb.data.models.FavouriteTvShow;
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
    private String type, favouriteTitle, favouriteImgPath;
    private Float favouriteRate;
    private boolean isFavourite;
    private AppDatabase database;

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
        database = AppDatabase.getInstance(getApplicationContext());

        favouriteImgPath = "";
        favouriteTitle = "";

        loadData(type, id);
    }

    private void setActionBar(String title) {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(title);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.detail_menu, menu);
        isFavourite(type, menu);
        return super.onCreateOptionsMenu(menu);
    }

    private void isFavourite(String type, Menu menu) {
        if (type.equalsIgnoreCase("MOVIE") || type.equalsIgnoreCase("FAV_MOVIE")) {
            isFavourite = database.favouriteDao().isMovieExists(id);
        } else if (type.equalsIgnoreCase("TV_SHOW") || type.equalsIgnoreCase("FAV_TV_SHOW")){
            isFavourite = database.favouriteDao().isTvShowExists(id);
        }

        if (!isFavourite) {
            menu.getItem(0).setIcon(ContextCompat.getDrawable(this, R.drawable.ic_outline_favorite_border_24));
        } else {
            menu.getItem(0).setIcon(ContextCompat.getDrawable(this, R.drawable.ic_baseline_favorite_24));
            menu.getItem(0).getIcon().setColorFilter(getResources().getColor(R.color.textColorPrimaryDark), PorterDuff.Mode.SRC_ATOP);
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.add_to_favourite:
                addToFav(type, id, item);
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void addToFav(String type, int id, MenuItem item) {
        switch (type){
            case "MOVIE":
                isMovieExistInDB(id, item);
                break;
            case "TV_SHOW":
                isTvShowExistInDB(id, item);
                break;
            case "FAV_MOVIE":
                isMovieExistInDB(id, item);
                break;
            case "FAV_TV_SHOW":
                isTvShowExistInDB(id, item);
                break;
        }
    }

    private void isTvShowExistInDB(int id, MenuItem item) {
        isFavourite = database.favouriteDao().isTvShowExists(id);
        if (isFavourite) {
            FavouriteTvShow favouriteTvShow = database.favouriteDao().findByFavTvShowId(id);
            Log.d("Remove Tv Show Debug", favouriteTvShow.getName());
            database.favouriteDao().deleteFavTvShow(favouriteTvShow).subscribe(() -> {
                item.setIcon(R.drawable.ic_outline_favorite_border_24);

                Toast.makeText(this, "Removed from favourite", Toast.LENGTH_SHORT).show();
            }, throwable -> {
                Toast.makeText(this, "Operation bshdbfsh failed", Toast.LENGTH_SHORT).show();
            });
        } else {
            FavouriteTvShow favoriteTvShow = new FavouriteTvShow(id, favouriteTitle, favouriteImgPath, favouriteRate);
            database.favouriteDao().addFavTvShow(favoriteTvShow).subscribe(()->{
                item.setIcon(R.drawable.ic_baseline_favorite_24);
                item.getIcon().setColorFilter(getResources().getColor(R.color.textColorPrimaryDark), PorterDuff.Mode.SRC_ATOP);
                Toast.makeText(this, "Add to Favorite", Toast.LENGTH_SHORT).show();
            },throwable->{
                Toast.makeText(this, "Failed to add", Toast.LENGTH_SHORT).show();
            });
        }
    }

    private void isMovieExistInDB(int id, MenuItem item) {
        isFavourite = database.favouriteDao().isMovieExists(id);
        if (isFavourite) {
            FavouriteMovie favouriteMovie = database.favouriteDao().findByFavMovieId(id);
            database.favouriteDao().deleteFavMovie(favouriteMovie).subscribe(() -> {
                item.setIcon(R.drawable.ic_outline_favorite_border_24);
                Toast.makeText(this, "Removed from favourite", Toast.LENGTH_SHORT).show();
            }, throwable -> {
                Toast.makeText(this, "Operation failed", Toast.LENGTH_SHORT).show();
            });
        } else {
            FavouriteMovie favoriteMovie = new FavouriteMovie(id, favouriteTitle, favouriteImgPath, favouriteRate);
            database.favouriteDao().addFavMovie(favoriteMovie).subscribe(()->{
                item.setIcon(R.drawable.ic_baseline_favorite_24);
                item.getIcon().setColorFilter(getResources().getColor(R.color.textColorPrimaryDark), PorterDuff.Mode.SRC_ATOP);
                Toast.makeText(this, "Add to Favorite", Toast.LENGTH_SHORT).show();
            },throwable->{
                Toast.makeText(this, "Failed to add", Toast.LENGTH_SHORT).show();
            });
        }
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
                    public void onFailure(String message) { }
                });
                break;
            case "TV_SHOW":
                tvShowRepository.getTvDetail(id, new OnTvDetailCallback() {
                    @Override
                    public void onSuccess(TvShow tvShow, String message) {
                        onTvBindView(tvShow);
                    }

                    @Override
                    public void onFailure(String message) { }
                });
                break;
            case "FAV_MOVIE":
                isFavourite = database.favouriteDao().isMovieExists(id);
                if (isFavourite) {
                    FavouriteMovie favouriteMovie = database.favouriteDao().findByFavMovieId(id);
                    Log.d("Detail Debug", favouriteMovie.getName());
                    onFavMovieBindView(favouriteMovie);
                } else {
                    Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
                }
                break;
            case "FAV_TV_SHOW":
                isFavourite = database.favouriteDao().isTvShowExists(id);
                if (isFavourite) {
                    FavouriteTvShow favouriteTvShow = database.favouriteDao().findByFavTvShowId(id);
                    onFavTvShowBindView(favouriteTvShow);
                } else {
                    Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
                }
                break;
        }

    }

    private void onFavTvShowBindView(FavouriteTvShow favouriteTvShow) {
        setActionBar(favouriteTvShow.getName());
        Glide.with(this)
                .load(favouriteTvShow.getPosterPath())
                .into(ivPoster);
        tvTitle.setText(favouriteTvShow.getName());
//        tvSynopsis.setText(favouriteMovie.getOverview());

        favouriteTitle = favouriteTvShow.getName();
        favouriteImgPath = favouriteTvShow.getPosterPath();
        favouriteRate = favouriteTvShow.getRate();
    }

    private void onFavMovieBindView(FavouriteMovie favouriteMovie) {
        setActionBar(favouriteMovie.getName());
        Glide.with(this)
                .load(favouriteMovie.getPosterPath())
                .into(ivPoster);
        tvTitle.setText(favouriteMovie.getName());
//        tvSynopsis.setText(favouriteMovie.getOverview());

        favouriteTitle = favouriteMovie.getName();
        favouriteImgPath = favouriteMovie.getPosterPath();
        favouriteRate = favouriteMovie.getRate();

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

        favouriteTitle = tvShow.getName();
        favouriteImgPath = tvShow.getPosterPath(ImageSize.W154);
        favouriteRate = tvShow.getVoteAverage();

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

        favouriteTitle = movie.getName();
        favouriteImgPath = movie.getPosterPath(ImageSize.W154);
        favouriteRate = movie.getVoteAverage();
    }

}