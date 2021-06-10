package com.tugas.yomoviedb.data.api.repository;

import androidx.annotation.NonNull;

import com.tugas.yomoviedb.Const;
import com.tugas.yomoviedb.data.api.Service;
import com.tugas.yomoviedb.data.api.repository.callback.OnCastCallback;
import com.tugas.yomoviedb.data.api.repository.callback.OnMovieCallback;
import com.tugas.yomoviedb.data.api.repository.callback.OnMovieDetailCallback;
import com.tugas.yomoviedb.data.api.repository.callback.OnMovieSearchCallback;
import com.tugas.yomoviedb.data.models.Credits;
import com.tugas.yomoviedb.data.models.movie.Movie;
import com.tugas.yomoviedb.data.models.movie.MovieResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MovieRepository {
    private static MovieRepository repository;
    private final Service service;

    public MovieRepository(Service service) {
        this.service = service;
    }

    public static MovieRepository getInstance() {
        if (repository == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Const.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            repository = new MovieRepository(retrofit.create(Service.class));
        }

        return repository;
    }

    public void getMovie(int page, final OnMovieCallback callback) {
        service.getMovieResults(Const.API_KEY, page).enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(@NonNull Call<MovieResponse> call, @NonNull Response<MovieResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        if (response.body().getMovieResult() != null) {
                            callback.onSuccess(response.body().getPage(), response.body().getMovieResult());
                        } else {
                            callback.onFailure("response.body().getResults() is null");
                        }
                    } else {
                        callback.onFailure("response.body() is null");
                    }
                } else {
                    callback.onFailure(response.message());
                }
            }

            @Override
            public void onFailure(@NonNull Call<MovieResponse> call, @NonNull Throwable t) {
                callback.onFailure(t.getLocalizedMessage());
            }
        });
    }

    public void getMovieDetail(int id, final OnMovieDetailCallback callback) {
        service.getMovieDetail(id, Const.API_KEY).enqueue(new Callback<Movie>() {
            @Override
            public void onResponse(@NonNull Call<Movie> call, @NonNull Response<Movie> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        callback.onSuccess(response.body(), response.message());
                    } else {
                        callback.onFailure("response.body() is null");
                    }
                } else {
                    callback.onFailure(response.message() + ", Error Code : " + response.code());
                }
            }

            @Override
            public void onFailure(@NonNull Call<Movie> call, @NonNull Throwable t) {
                callback.onFailure(t.getLocalizedMessage());
            }
        });
    }

    public void getMovieCast(int id, final OnCastCallback callback) {
        service.getMovieCast(id, Const.API_KEY).enqueue(new Callback<Credits>() {
            @Override
            public void onResponse(@NonNull Call<Credits> call, @NonNull Response<Credits> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        callback.onSuccess(response.body(), response.message());
                    } else {
                        callback.onFailure("response.body() is null");
                    }
                } else {
                    callback.onFailure(response.message() + ", Error Code : " + response.code());
                }
            }

            @Override
            public void onFailure(@NonNull Call<Credits> call, @NonNull Throwable t) {
                callback.onFailure(t.getLocalizedMessage());
            }
        });
    }

    public void searchMovie(String query, int page, final OnMovieSearchCallback callback) {
        service.searchMovie(Const.API_KEY, query, page).enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        if (response.body().getMovieResult() != null) {
                            callback.onSuccess(response.body().getMovieResult(), response.message(), response.body().getPage());
                        } else {
                            callback.onFailure("response.body().getMovieResults() is null");
                        }
                    } else {
                        callback.onFailure("response.body() is null");
                    }
                } else {
                    callback.onFailure(response.message());
                }
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                callback.onFailure(t.getLocalizedMessage());
            }
        });
    }
}
