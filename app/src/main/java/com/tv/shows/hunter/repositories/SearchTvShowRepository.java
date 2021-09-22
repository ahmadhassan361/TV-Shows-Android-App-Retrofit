package com.tv.shows.hunter.repositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.tv.shows.hunter.network.ApiClient;
import com.tv.shows.hunter.network.ApiService;
import com.tv.shows.hunter.responses.TvShowsResponse;

import io.reactivex.annotations.NonNull;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchTvShowRepository {

    private ApiService apiService;
    public SearchTvShowRepository()
    {
        apiService = ApiClient.getRetrofit().create(ApiService.class);

    }
    public LiveData<TvShowsResponse> searchTvShows(String query,int page)
    {
        MutableLiveData<TvShowsResponse> data = new MutableLiveData<>();
        apiService.searchTvShow(query,page).enqueue(new Callback<TvShowsResponse>() {
            @Override
            public void onResponse(@NonNull  Call<TvShowsResponse> call,@NonNull Response<TvShowsResponse> response) {
                data.setValue(response.body());

            }

            @Override
            public void onFailure(@NonNull Call<TvShowsResponse> call,@NonNull Throwable t) {

                data.setValue(null);
            }
        });
        return data;
    }

}
