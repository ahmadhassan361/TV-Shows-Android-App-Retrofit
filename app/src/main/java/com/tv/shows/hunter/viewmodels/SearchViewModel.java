package com.tv.shows.hunter.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.tv.shows.hunter.repositories.SearchTvShowRepository;
import com.tv.shows.hunter.responses.TvShowsResponse;

public class SearchViewModel extends ViewModel {
    private SearchTvShowRepository searchTvShowRepository;

    public SearchViewModel()
    {
        searchTvShowRepository = new SearchTvShowRepository();

    }
    public LiveData<TvShowsResponse> searchTvShows(String query,int page)
    {
        return searchTvShowRepository.searchTvShows(query,page);
        
    }
}
