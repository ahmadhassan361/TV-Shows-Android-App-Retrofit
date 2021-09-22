package com.tv.shows.hunter.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.tv.shows.hunter.model.TvShow;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;

@Dao
public interface TvShowDao {
    @Query("SELECT * FROM tvShows")
    Flowable<List<TvShow>> getWacthlist();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable addToWatchList (TvShow tvShow);

    @Delete
    Completable removeFromWatchlist(TvShow tvShow);
    @Query("SELECT * FROM tvShows WHERE id = :tvShowId")
    Flowable<TvShow> getTvShowFromWatchlist(String tvShowId);
}
