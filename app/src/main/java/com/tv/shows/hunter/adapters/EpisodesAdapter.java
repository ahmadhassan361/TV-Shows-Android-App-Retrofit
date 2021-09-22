package com.tv.shows.hunter.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.tv.shows.hunter.activities.R;
import com.tv.shows.hunter.activities.databinding.ItemContainerEpisdoeBinding;
import com.tv.shows.hunter.model.Episode;

import java.util.List;

public class EpisodesAdapter extends RecyclerView.Adapter<EpisodesAdapter.EpisodeViewHolder>
{
    private List<Episode> episodes;
    private LayoutInflater layoutInflater;


    public EpisodesAdapter(List<Episode> episodes) {
        this.episodes = episodes;
    }

    @NonNull
    @Override
    public EpisodeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (layoutInflater == null)
        {
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        ItemContainerEpisdoeBinding itemContainerEpisdoeBinding = DataBindingUtil.inflate(
                layoutInflater, R.layout.item_container_episdoe,parent,false
        );


        return new EpisodeViewHolder(itemContainerEpisdoeBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull EpisodeViewHolder holder, int position) {
holder.bindEpisode(episodes.get(position));
    }

    @Override
    public int getItemCount() {
        return episodes.size();
    }


    static class EpisodeViewHolder extends RecyclerView.ViewHolder
    {
        private ItemContainerEpisdoeBinding itemContainerEpisdoeBinding;

        public EpisodeViewHolder(ItemContainerEpisdoeBinding itemContainerEpisdoeBinding)
        {
            super(itemContainerEpisdoeBinding.getRoot());
            this.itemContainerEpisdoeBinding = itemContainerEpisdoeBinding;
        }
        public void bindEpisode(Episode episode)
        {
            String title = "S";
            String season = episode.getSeason();
            if (season.length() == 1)
            {
                season = "0".concat(season);
            }
            String episodeNumber = episode.getEpisode();
            if (episodeNumber.length() == 1)
            {
                episodeNumber = "0".concat(episodeNumber);
            }
            episodeNumber = "E".concat(episodeNumber);
            title = title.concat(season).concat(episodeNumber);
            itemContainerEpisdoeBinding.setTitle(title);
            itemContainerEpisdoeBinding.setName(episode.getName());
            itemContainerEpisdoeBinding.setAirDate(episode.getAirDate());
        }
    }
}
