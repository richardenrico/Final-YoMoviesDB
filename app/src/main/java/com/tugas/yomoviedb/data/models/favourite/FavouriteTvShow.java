package com.tugas.yomoviedb.data.models.favourite;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;


@Entity(tableName = "tv_show")
public class FavouriteTvShow {
    @PrimaryKey(autoGenerate = true)
    private final int id;
    @ColumnInfo(name = "name")
    private final String name;
    @ColumnInfo(name = "poster_path")
    private final String posterPath;
    @ColumnInfo(name = "rating")
    private final float rate;

    public FavouriteTvShow(int id, String name, String posterPath, float rate) {
        this.id = id;
        this.name = name;
        this.posterPath = posterPath;
        this.rate = rate;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public float getRate() {
        return rate;

    }
}
