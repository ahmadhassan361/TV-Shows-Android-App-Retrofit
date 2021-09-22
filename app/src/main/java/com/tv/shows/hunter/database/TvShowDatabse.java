package com.tv.shows.hunter.database;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.tv.shows.hunter.dao.TvShowDao;
import com.tv.shows.hunter.model.TvShow;

@Database(entities = TvShow.class,version = 1,exportSchema = false)
public abstract class TvShowDatabse extends RoomDatabase {

    private static TvShowDatabse tvShowDatabse;
    public static  synchronized TvShowDatabse getTvShowDatabse(Context context)
    {
        if (tvShowDatabse == null)
        {
            tvShowDatabse = Room.databaseBuilder(context,TvShowDatabse.class,"tv_shows_db"
            ).build();
        }
        return tvShowDatabse;

    }
    public abstract TvShowDao tvShowDao();
    

}
