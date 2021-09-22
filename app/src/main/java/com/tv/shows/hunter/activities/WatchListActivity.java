package com.tv.shows.hunter.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.tv.shows.hunter.activities.databinding.ActivityWatchListBinding;
import com.tv.shows.hunter.adapters.WatchListAdapters;
import com.tv.shows.hunter.listeners.WatchlistListeners;
import com.tv.shows.hunter.model.TvShow;
import com.tv.shows.hunter.utilities.TempDataHolder;
import com.tv.shows.hunter.viewmodels.WatchListViewModel;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class WatchListActivity extends AppCompatActivity implements WatchlistListeners {

    private ActivityWatchListBinding activityWatchListBinding;
    private WatchListViewModel viewModel;
    private WatchListAdapters watchListAdapters;
    private List<TvShow> watchList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityWatchListBinding = DataBindingUtil.setContentView(this,R.layout.activity_watch_list);
        doInitialization();
    }
    private void doInitialization()
    {
        viewModel = new ViewModelProvider(this).get(WatchListViewModel.class);
        activityWatchListBinding.imgaeBack.setOnClickListener(v -> onBackPressed());

        watchList = new ArrayList<>();

        loadWatchList();

    }
    private void loadWatchList()
    {
activityWatchListBinding.setIsLoading(true);
        CompositeDisposable compositeDisposable= new CompositeDisposable();
        compositeDisposable.add(viewModel.loadWatchList().subscribeOn(Schedulers.computation())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(tvShows -> {
            activityWatchListBinding.setIsLoading(false);
            if (watchList.size() > 0 )
            {
                watchList.clear();

            }
            watchList.addAll(tvShows);
            watchListAdapters = new WatchListAdapters(watchList,this);
            activityWatchListBinding.watchListRecyclerView.setAdapter(watchListAdapters);
            activityWatchListBinding.watchListRecyclerView.setVisibility(View.VISIBLE);
            compositeDisposable.dispose();

        }));
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (TempDataHolder.IS_WATCHLIST_UPDATED)
        {
            loadWatchList();
            TempDataHolder.IS_WATCHLIST_UPDATED = false;

        }

    }

    @Override
    public void onTvShowClicked(TvShow tvShow) {
        Intent intent = new Intent(getApplicationContext(),TvShowDetailsActivity.class);
        intent.putExtra("tvShow",tvShow);
        startActivity(intent);

    }

    @Override
    public void removedTvShowFromWatchlist(TvShow tvShow, int position) {

        CompositeDisposable compositeDisposableForDelete = new CompositeDisposable();
        compositeDisposableForDelete.add(viewModel.removeTvShowFromWatchList(tvShow)
        .subscribeOn(Schedulers.computation())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(() -> {
            watchList.remove(position);
            watchListAdapters.notifyItemRemoved(position);
            watchListAdapters.notifyItemRangeChanged(position,watchListAdapters.getItemCount());
            compositeDisposableForDelete.dispose();
        }));
    }
}