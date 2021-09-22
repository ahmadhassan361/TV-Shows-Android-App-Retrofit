package com.tv.shows.hunter.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;

import com.tv.shows.hunter.activities.databinding.ActivitySearchBinding;
import com.tv.shows.hunter.adapters.TvShowAdapters;
import com.tv.shows.hunter.listeners.TvShowListener;
import com.tv.shows.hunter.model.TvShow;
import com.tv.shows.hunter.viewmodels.SearchViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class SearchActivity extends AppCompatActivity implements TvShowListener {

    private ActivitySearchBinding activitySearchBinding;
    private SearchViewModel searchViewModel;
    private List<TvShow> tvShows = new ArrayList<>();
    private TvShowAdapters tvShowAdapters;
    private int currentPage = 1;
    private int totalAvailablePage = 1;
    private Timer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activitySearchBinding = DataBindingUtil.setContentView(this,R.layout.activity_search);
        doInitialization();
    }
    private void doInitialization()
    {
        activitySearchBinding.imageBack.setOnClickListener(v -> onBackPressed());
        activitySearchBinding.tvShowsRecyclerView.setHasFixedSize(true);
        searchViewModel = new ViewModelProvider(this).get(SearchViewModel.class);
        tvShowAdapters = new TvShowAdapters(tvShows,this);
        activitySearchBinding.tvShowsRecyclerView.setAdapter(tvShowAdapters);
        activitySearchBinding.inputSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (timer != null)
                {
                    timer.cancel();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

                if (!s.toString().trim().isEmpty())
                {
                    timer = new Timer();
                    timer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            new Handler(Looper.getMainLooper()).post(() -> {

                           currentPage = 1;
                           totalAvailablePage = 1;
                           searchTvShows(s.toString());

                            });
                        }
                    },800);
                }
                else
                {
                    tvShows.clear();
                    tvShowAdapters.notifyDataSetChanged();
                }
            }
        });
        activitySearchBinding.tvShowsRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (!activitySearchBinding.tvShowsRecyclerView.canScrollVertically(1))
                {
                    if (!activitySearchBinding.inputSearch.getText().toString().isEmpty())
                    {
                        if (currentPage < totalAvailablePage)
                        {
                            currentPage += 1;
                            searchTvShows(activitySearchBinding.inputSearch.getText().toString());
                        }
                    }
                }
            }
        });
        activitySearchBinding.inputSearch.requestFocus();

    }
    private void searchTvShows(String query)
    {
        toogleLoading();
        searchViewModel.searchTvShows(query,currentPage).observe(this,tvShowsResponse -> {
            toogleLoading();
            if (tvShowsResponse != null)
            {
                totalAvailablePage = tvShowsResponse.getTotalPages();
                if (tvShowsResponse.getTcShows() != null)
                {
                    int oldCount = tvShows.size();
                    tvShows.addAll(tvShowsResponse.getTcShows());
                    tvShowAdapters.notifyItemRangeInserted(oldCount,tvShows.size());
                }
            }
        });

    }
    private void toogleLoading() {
        if (currentPage == 1) {
            activitySearchBinding.setIsLoading(activitySearchBinding.getIsLoading() == null || !activitySearchBinding.getIsLoading());
        } else {
            activitySearchBinding.setIsLoadingMore(activitySearchBinding.getIsLoadingMore() == null || !activitySearchBinding.getIsLoadingMore());

        }
    }

    @Override
    public void onTvShowClicked(TvShow tvShow) {
        Intent intent = new Intent(getApplicationContext(),TvShowDetailsActivity.class);
        intent.putExtra("tvShow",tvShow);
        startActivity(intent);
    }
}