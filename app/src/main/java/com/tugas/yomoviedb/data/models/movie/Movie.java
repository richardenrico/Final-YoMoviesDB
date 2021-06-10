package com.tugas.yomoviedb.data.models.movie;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.tugas.yomoviedb.Const;
import com.tugas.yomoviedb.ImageSize;
import com.tugas.yomoviedb.data.models.Genre;

import java.util.List;

public class Movie {
    @SerializedName("backdrop_path")
    private String backdropPath;
    @SerializedName("release_date")
    private String releaseDate;
    @SerializedName("id")
    private int id;
    @SerializedName("original_title")
    private String name;
    @SerializedName("overview")
    private String overview;
    @SerializedName("popularity")
    private float popularity;
    @SerializedName("poster_path")
    private String posterPath;
    @SerializedName("vote_average")
    private float voteAverage;
    @SerializedName("vote_count")
    private int voteCount;

    public List<Genre> getGenres() {
        return genres;
    }

    @SerializedName("genres")
    @Expose
    private List<Genre> genres;

    public String getBackdropPath(ImageSize size) {
        return Const.IMG_URL + size.getValue() + backdropPath;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getOverview() {
        return overview;
    }

    public float getPopularity() {
        return popularity;
    }

    public String getPosterPath(ImageSize size) {
        return Const.IMG_URL + size.getValue() + posterPath;
    }

    public float getVoteAverage() {
        return voteAverage;
    }

    public float getRating() {
        return voteAverage / 2;
    }

    public int getVoteCount() {
        return voteCount;
    }
}
