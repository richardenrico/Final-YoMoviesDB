package com.tugas.yomoviedb.data.api.repository.callback;

import com.tugas.yomoviedb.data.models.movie.Movie;

public interface OnMovieDetailCallback {
    void onSuccess(Movie movie, String message);

    void onFailure(String message);
}
