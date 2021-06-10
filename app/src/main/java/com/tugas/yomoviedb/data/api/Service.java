package com.tugas.yomoviedb.data.api;

import com.tugas.yomoviedb.data.models.movie.Movie;
import com.tugas.yomoviedb.data.models.movie.MovieResponse;
import com.tugas.yomoviedb.data.models.tvshow.TvShow;
import com.tugas.yomoviedb.data.models.tvshow.TvShowResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface Service {
    @GET("tv/{sort_by}")
    Call<TvShowResponse> getTvResults(
            @Path("sort_by") String sortBy,
            @Query("api_key") String apiKey,
            @Query("page") int page
    );

    @GET("tv/{id}")
    Call<TvShow> getTvDetail(
            @Path("id") int id,
            @Query("api_key") String apiKey
    );

    @GET("search/tv")
    Call<TvShowResponse> searchTv(
            @Query("api_key") String apiKey,
            @Query("query") String query,
            @Query("page") int page
    );

    @GET("movie/{sort_by}")
    Call<MovieResponse> getMovieResults(
            @Path("sort_by") String sortBy,
            @Query("api_key") String apiKey,
            @Query("page") int page
    );

    @GET("movie/{id}")
    Call<Movie> getMovieDetail(
            @Path("id") int id,
            @Query("api_key") String apiKey
    );

    @GET("search/movie")
    Call<MovieResponse> searchMovie(
            @Query("api_key") String apiKey,
            @Query("query") String query,
            @Query("page") int page
    );
}
