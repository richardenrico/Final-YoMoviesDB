package com.tugas.yomoviedb.data.api;

import com.tugas.yomoviedb.data.models.TvShow;
import com.tugas.yomoviedb.data.models.TvShowResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface Service {
    @GET("tv/{sort_by}")
    Call<TvShowResponse> getResults(
            @Path("sort_by") String sortBy,
            @Query("api_key") String apiKey,
            @Query("page") int page
    );

    @GET("tv/{id}")
    Call<TvShow> getDetail(
            @Path("id") int id,
            @Query("api_key") String apiKey
    );

    @GET("search/tv")
    Call<TvShowResponse> search(
            @Query("api_key") String apiKey,
            @Query("query") String query,
            @Query("page") int page
    );
}
