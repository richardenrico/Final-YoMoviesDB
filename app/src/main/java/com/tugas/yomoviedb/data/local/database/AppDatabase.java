package com.tugas.yomoviedb.data.local.database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Database;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;

import com.tugas.yomoviedb.data.local.database.dao.FavouriteDao;
import com.tugas.yomoviedb.data.models.FavouriteMovie;
import com.tugas.yomoviedb.data.models.FavouriteTvShow;

@Database(entities = {FavouriteMovie.class, FavouriteTvShow.class}, version = 2, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract FavouriteDao favouriteDao();

    private static AppDatabase instance;

    public synchronized static AppDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "Yo!Movie Database")
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
        }

        return instance;
    }
}
