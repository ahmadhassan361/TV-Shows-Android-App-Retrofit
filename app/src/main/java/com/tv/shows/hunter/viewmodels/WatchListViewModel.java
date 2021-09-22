package com.tv.shows.hunter.viewmodels;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;

import com.tv.shows.hunter.database.TvShowDatabse;
import com.tv.shows.hunter.model.TvShow;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.annotations.NonNull;

public class WatchListViewModel extends AndroidViewModel
{
    private TvShowDatabse tvShowDatabse;
    public WatchListViewModel(@NonNull Application application)
    {
        super(application);
        tvShowDatabse = TvShowDatabse.getTvShowDatabse(application);

    }
    public Flowable<List<TvShow>> loadWatchList()
    {
        return tvShowDatabse.tvShowDao().getWacthlist();
    }
    public Completable removeTvShowFromWatchList(TvShow tvShow)
    {
        return tvShowDatabse.tvShowDao().removeFromWatchlist(tvShow);
    }

}
