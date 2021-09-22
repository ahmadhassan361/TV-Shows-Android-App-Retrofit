package com.tv.shows.hunter.viewmodels;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.tv.shows.hunter.database.TvShowDatabse;
import com.tv.shows.hunter.model.TvShow;
import com.tv.shows.hunter.repositories.TvShowDetailsRepository;
import com.tv.shows.hunter.responses.TvShowDetailsResponse;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.annotations.NonNull;

public class TvShowDetailsViewModel extends AndroidViewModel
{
    private TvShowDetailsRepository tvShowDetailsRepository;
    private TvShowDatabse tvShowDatabse;
    public  TvShowDetailsViewModel(@NonNull Application application)
    {
        super(application);
        tvShowDetailsRepository = new TvShowDetailsRepository();
        tvShowDatabse = TvShowDatabse.getTvShowDatabse(application);
    }
    public LiveData<TvShowDetailsResponse> getTvShowDetails(String tvShowId)
    {
        return  tvShowDetailsRepository.getTvShowDetails(tvShowId);
    }
    public Completable addToWatchList(TvShow tvShow)
    {
        return tvShowDatabse.tvShowDao().addToWatchList(tvShow);
    }

    public Flowable<TvShow> getTvShowFromWatchlist(String tvShowId)
    {
        return tvShowDatabse.tvShowDao().getTvShowFromWatchlist(tvShowId);
    }
    public Completable removeTvShowFromWatchlist(TvShow tvShow)
    {
        return tvShowDatabse.tvShowDao().removeFromWatchlist(tvShow);
    }
}
