package com.tugas.yomoviedb.data.api;

import com.tugas.yomoviedb.data.models.Credits;
import com.tugas.yomoviedb.data.models.movie.Movie;
import com.tugas.yomoviedb.data.models.movie.MovieResponse;
import com.tugas.yomoviedb.data.models.tvshow.TvShow;
import com.tugas.yomoviedb.data.models.tvshow.TvShowResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface Service {
    // TV Show Endpoint
    @GET("tv/airing_today")
    Call<TvShowResponse> getTvResults(
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

    @GET("tv/{tv_id}/credits")
    Call<Credits> getTvCast (
            @Path("tv_id") int id,
            @Query("api_key") String apiKey
    );

    // Movie Endpoint
    @GET("movie/now_playing")
    Call<MovieResponse> getMovieResults(
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

    @GET("movie/{movie_id}/credits")
    Call<Credits> getMovieCast (
            @Path("movie_id") int id,
            @Query("api_key") String apiKey
    );
}
