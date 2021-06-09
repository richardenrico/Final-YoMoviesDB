package com.tugas.yomoviedb.data.api.repository.callback;

import com.tugas.yomoviedb.data.models.movie.Movie;

import java.util.List;

public interface OnMovieCallback {
    void onSuccess(int page, List<Movie> movieList);

    void onFailure(String message);
}
