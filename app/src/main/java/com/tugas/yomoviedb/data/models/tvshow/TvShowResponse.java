package com.tugas.yomoviedb.data.models.tvshow;

import com.google.gson.annotations.SerializedName;
import com.tugas.yomoviedb.data.models.tvshow.TvShow;

import java.util.List;

public class TvShowResponse {
    @SerializedName("page")
    private int page;
    @SerializedName("total_results")
    private int totalResluts;
    @SerializedName("total_pages")
    private int totalPages;
    @SerializedName("results")
    private List<TvShow> results;

    public int getPage() {
        return page;
    }

    public List<TvShow> getResults() {
        return results;
    }
}
