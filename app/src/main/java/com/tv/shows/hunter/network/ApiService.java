package com.tv.shows.hunter.network;

import com.tv.shows.hunter.model.TvShow;
import com.tv.shows.hunter.responses.TvShowDetailsResponse;
import com.tv.shows.hunter.responses.TvShowsResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService
{
    @GET("most-popular")
    Call<TvShowsResponse> getMostPopularTvShow(@Query("page") int page);
    @GET("show-details")
    Call<TvShowDetailsResponse> getTvShowDetails(@Query("q") String  tvShowId);
    @GET("search")
    Call<TvShowsResponse> searchTvShow(@Query("q") String query,@Query("page") int page);

}
