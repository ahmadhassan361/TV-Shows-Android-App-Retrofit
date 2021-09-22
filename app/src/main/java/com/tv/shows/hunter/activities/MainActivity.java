package com.tv.shows.hunter.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;


import com.tv.shows.hunter.activities.databinding.ActivityMainBinding;
import com.tv.shows.hunter.adapters.TvShowAdapters;
import com.tv.shows.hunter.listeners.TvShowListener;
import com.tv.shows.hunter.model.TvShow;
import com.tv.shows.hunter.viewmodels.MostPopularTVShowsViewModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements TvShowListener {
    private ActivityMainBinding activityMainBinding;
    private MostPopularTVShowsViewModel viewModel;
    private final List<TvShow> tvShowList = new ArrayList<>();
    private TvShowAdapters tvShowAdapters;
    private int currentPage = 1;
    private int totalAvailablePages = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        doInitialization();

    }

    public void doInitialization() {
        activityMainBinding.tvShowsRecyclerView.setHasFixedSize(true);
        viewModel = new ViewModelProvider(this).get(MostPopularTVShowsViewModel.class);
        tvShowAdapters = new TvShowAdapters(tvShowList,this);
        activityMainBinding.tvShowsRecyclerView.setAdapter(tvShowAdapters);
        activityMainBinding.tvShowsRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (!activityMainBinding.tvShowsRecyclerView.canScrollVertically(1))
                {
                    if (currentPage <= totalAvailablePages)
                    {
                        currentPage +=1;
                        getMostPopularTvShows();
                    }
                }
            }
        });
        activityMainBinding.imageSearch.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(),SearchActivity.class)));
        activityMainBinding.imageWatchList.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(),WatchListActivity.class)));
        getMostPopularTvShows();
    }

    private void getMostPopularTvShows() {
        toogleLoading();
        viewModel.getMostPopularTvSHows(currentPage).observe(this, mostPopularTvShowsResponse -> {
            activityMainBinding.setIsLoading(false);
            if (mostPopularTvShowsResponse != null) {
                totalAvailablePages = mostPopularTvShowsResponse.getTotalPages();
                if (mostPopularTvShowsResponse.getTcShows() != null) {
                    int oldCount = tvShowList.size() ;
                    tvShowList.addAll(mostPopularTvShowsResponse.getTcShows());
                    tvShowAdapters.notifyItemRangeInserted(oldCount,tvShowList.size() );
                }
            }

        });
    }

    private void toogleLoading() {
        if (currentPage == 1) {
            activityMainBinding.setIsLoading(activityMainBinding.getIsLoading() == null || !activityMainBinding.getIsLoading());
        } else {
            activityMainBinding.setIsLoadingMore(activityMainBinding.getIsLoadingMore() == null || !activityMainBinding.getIsLoadingMore());

        }
    }

    @Override
    public void onTvShowClicked(TvShow tvShow) {
        Intent intent = new Intent(getApplicationContext(),TvShowDetailsActivity.class);
       intent.putExtra("tvShow",tvShow);
        startActivity(intent);
    }
}