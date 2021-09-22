package com.tv.shows.hunter.responses;

import com.google.gson.annotations.SerializedName;
import com.tv.shows.hunter.model.TvShowDetails;

public class TvShowDetailsResponse
{
    @SerializedName("tvShow")
    private TvShowDetails tvShowDetails;

    public TvShowDetails getTvShowDetails() {
        return tvShowDetails;
    }
}
