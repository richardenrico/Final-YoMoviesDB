package com.tugas.yomoviedb.data.api.repository.callback;

import com.tugas.yomoviedb.data.models.movie.Movie;

import java.util.List;

public interface OnMovieSearchCallback {

    void onSuccess(List<Movie> movieList, String msg, int page);

    void onFailure(String msg);

}
