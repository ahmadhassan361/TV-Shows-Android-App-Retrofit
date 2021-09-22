package com.tv.shows.hunter.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.tv.shows.hunter.repositories.MostPopularTvShowsRepository;
import com.tv.shows.hunter.responses.TvShowsResponse;

public class MostPopularTVShowsViewModel extends ViewModel
{

    public MostPopularTvShowsRepository mostPopularTvShowsRepository;
    public MostPopularTVShowsViewModel()
    {
        mostPopularTvShowsRepository = new MostPopularTvShowsRepository();

    }
    public LiveData<TvShowsResponse>  getMostPopularTvSHows(int page)
    {
        return mostPopularTvShowsRepository.getMostPopularTvShows(page);
    }

}
