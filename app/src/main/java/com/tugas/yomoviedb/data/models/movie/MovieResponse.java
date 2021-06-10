package com.tugas.yomoviedb.data.models.movie;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MovieResponse {
    @SerializedName("page")
    private int page;
    @SerializedName("total_results")
    private int totalResluts;
    @SerializedName("total_pages")
    private int totalPages;
    @SerializedName("results")
    @Expose
    private List<Movie> movieResult;

    public List<Movie> getMovieResult() {
        return movieResult;
    }

    public int getPage() {
        return page;
    }
}
