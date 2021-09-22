package com.tv.shows.hunter.responses;

import com.google.gson.annotations.SerializedName;
import com.tv.shows.hunter.model.TvShow;

import java.util.List;

public class TvShowsResponse
{
    @SerializedName("page")
    private int page;
    @SerializedName("pages")
    private int totalPages;
    @SerializedName("tv_shows")
    private List<TvShow> tcShows;


    public int getPage() {
        return page;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public List<TvShow> getTcShows() {
        return tcShows;
    }
}
