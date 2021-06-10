package com.tugas.yomoviedb.data.local.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.tugas.yomoviedb.data.models.FavouriteMovie;
import com.tugas.yomoviedb.data.models.FavouriteTvShow;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;

@Dao
public interface FavouriteDao {
    // Favourite Movie
    @Query("SELECT * FROM movie")
    LiveData<List<FavouriteMovie>> getListFavMovie();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    Completable addFavMovie(FavouriteMovie favouriteMovie);

    @Delete
    Completable deleteFavMovie(FavouriteMovie favouriteMovie);

    @Query("SELECT EXISTS (SELECT * FROM movie WHERE id = :id )")
    boolean isMovieExists(int id);

    @Query("SELECT * FROM movie WHERE id=:id LIMIT 1 ")
    FavouriteMovie findByFavMovieId(int id);

    // Favourite Tv Show
    @Query("SELECT * FROM tv_show")
    LiveData<List<FavouriteTvShow>> getListFavTvShow();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    Completable addFavTvShow(FavouriteTvShow favouriteTvShow);

    @Delete
    Completable deleteFavTvShow(FavouriteTvShow favouriteTvShow);

    @Query("SELECT EXISTS (SELECT * FROM tv_show WHERE id = :id )")
    boolean isTvShowExists(int id);

    @Query("SELECT * FROM tv_show WHERE id=:id LIMIT 1 ")
    FavouriteTvShow findByFavTvShowId(int id);
}
