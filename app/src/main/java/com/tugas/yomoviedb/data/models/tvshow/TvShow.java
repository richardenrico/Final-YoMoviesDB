package com.tugas.yomoviedb.data.models.tvshow;

import com.google.gson.annotations.SerializedName;
import com.tugas.yomoviedb.Const;
import com.tugas.yomoviedb.ImageSize;

public class TvShow {
    @SerializedName("backdrop_path")
    private String backdropPath;
    @SerializedName("first_air_date")
    private String firstAirDate;
    @SerializedName("id")
    private int id;
    @SerializedName("last_air_date")
    private String lastAirDate;
    @SerializedName("name")
    private String name;
    @SerializedName("number_of_episodes")
    private int numberOfEpisode;
    @SerializedName("number_of_seasons")
    private int numberOfSeaon;
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

    public String getBackdropPath(ImageSize size) {
        return Const.IMG_URL + size.getValue() + backdropPath;
    }

    public String getFirstAirDate() {
        return firstAirDate;
    }

    public int getId() {
        return id;
    }

    public String getLastAirDate() {
        return lastAirDate;
    }

    public String getName() {
        return name;
    }

    public int getNumberOfEpisode() {
        return numberOfEpisode;
    }

    public int getNumberOfSeaon() {
        return numberOfSeaon;
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

    public int getVoteCount() {
        return voteCount;
    }
}
