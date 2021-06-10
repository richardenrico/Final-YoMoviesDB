package com.tugas.yomoviedb.data.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.tugas.yomoviedb.Const;
import com.tugas.yomoviedb.ImageSize;


@Entity(tableName = "tv_show")
public class FavouriteTvShow {
    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "name")
    private String name;
    @ColumnInfo(name = "poster_path")
    private String posterPath;
    @ColumnInfo(name = "rating")
    private float rate;

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
