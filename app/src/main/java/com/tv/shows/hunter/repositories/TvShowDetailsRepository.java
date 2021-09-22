package com.tv.shows.hunter.repositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.tv.shows.hunter.network.ApiClient;
import com.tv.shows.hunter.network.ApiService;
import com.tv.shows.hunter.responses.TvShowDetailsResponse;

import io.reactivex.annotations.NonNull;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TvShowDetailsRepository {
    private ApiService apiService;

    public TvShowDetailsRepository() {
        apiService = ApiClient.getRetrofit().create(ApiService.class);
    }

    public LiveData<TvShowDetailsResponse> getTvShowDetails(String tvShowId) {
        MutableLiveData<TvShowDetailsResponse> data = new MutableLiveData<>();
        apiService.getTvShowDetails(tvShowId).enqueue(new Callback<TvShowDetailsResponse>() {
            @Override
            public void onResponse(@NonNull Call<TvShowDetailsResponse> call, @NonNull Response<TvShowDetailsResponse> response) {
                data.setValue(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<TvShowDetailsResponse> call, @NonNull Throwable t) {
                data.setValue(null);
            }
        });
        return data;
    }

}
