package com.tv.shows.hunter.listeners;

import com.tv.shows.hunter.model.TvShow;

public interface WatchlistListeners {
    void onTvShowClicked(TvShow tvShow);
    void removedTvShowFromWatchlist(TvShow tvShow,int position);
}
