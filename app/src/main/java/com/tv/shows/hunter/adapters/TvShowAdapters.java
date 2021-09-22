package com.tv.shows.hunter.adapters;

import android.view.LayoutInflater;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.tv.shows.hunter.activities.R;
import com.tv.shows.hunter.activities.databinding.ItemContainerTvShowsBinding;
import com.tv.shows.hunter.listeners.TvShowListener;
import com.tv.shows.hunter.model.TvShow;

import java.util.List;

public class TvShowAdapters extends RecyclerView.Adapter<TvShowAdapters.TvShowViewHolder> {
    private List<TvShow> tvShows;
    private LayoutInflater layoutInflater;
    private TvShowListener tvShowListener;

    public TvShowAdapters(List<TvShow> tvShows,TvShowListener tvShowListener) {
        this.tvShows = tvShows;
        this.tvShowListener = tvShowListener;
    }

    @NonNull
    @Override
    public TvShowViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.getContext());

        }
        ItemContainerTvShowsBinding tvShowsBinding = DataBindingUtil.inflate(
                layoutInflater, R.layout.item_container_tv_shows, parent, false
        );
        return new TvShowViewHolder(tvShowsBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull TvShowViewHolder holder, int position) {

        holder.bindTvShows(tvShows.get(position));
    }

    @Override
    public int getItemCount() {
        return tvShows.size();
    }

     class TvShowViewHolder extends RecyclerView.ViewHolder {
        private ItemContainerTvShowsBinding itemContainerTvShowsBinding;

        public TvShowViewHolder(ItemContainerTvShowsBinding itemContainerTvShowsBinding) {
            super(itemContainerTvShowsBinding.getRoot());
            this.itemContainerTvShowsBinding = itemContainerTvShowsBinding;
        }

        public void bindTvShows(TvShow tvShow) {
            itemContainerTvShowsBinding.setTvShow(tvShow);
            itemContainerTvShowsBinding.executePendingBindings();
            itemContainerTvShowsBinding.getRoot().setOnClickListener(v -> tvShowListener.onTvShowClicked(tvShow));
        }
    }
}
