package com.kevcordova.jobsitychallenge.ui.adapters.base

import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.example.baseandroidmodulekevcordova.extensions.bindGlideImage
import com.kevcordova.jobsitychallenge.R
import com.kevcordova.jobsitychallenge.databinding.ItemEpisodeListLayoutBinding
import com.kevcordova.jobsitychallenge.databinding.ItemEpisodeSeasonTitleLayoutBinding
import com.kevcordova.jobsitychallenge.databinding.ItemShowLayoutBinding
import com.kevcordova.jobsitychallenge.domain.Show

sealed class BaseRecyclerViewHolder(binding: ViewBinding) : RecyclerView.ViewHolder(binding.root) {

    class ShowViewHolder(private val binding: ItemShowLayoutBinding) : BaseRecyclerViewHolder(binding) {
        fun bind(show: Show) {
            binding.textTitleShowItem.text = show.name
            binding.imageCoverItemShow.bindGlideImage(
                show.imageUrl,
                R.drawable.ic_round_image_placeholder,
                R.drawable.ic_round_broken_image
            )
        }
    }

    class EpisodeListViewHolder(private val binding: ItemEpisodeListLayoutBinding) : BaseRecyclerViewHolder(binding) {
        fun bind(episode: BaseRecyclerViewItem.EpisodeRecyclerViewItem) {
            binding.imageCoverEpisodeItem.bindGlideImage(episode.imageUrl,
                R.drawable.ic_round_image_placeholder,
                R.drawable.ic_round_broken_image
            )
            binding.textTitleEpisodeItem.text = episode.name
            binding.textEpisodeNumberItem.text = binding.textEpisodeNumberItem.context.getString(R.string.chapter_number, episode.numberChapter)
        }
    }


    class EpisodeSeasonViewHolder(private val binding: ItemEpisodeSeasonTitleLayoutBinding) : BaseRecyclerViewHolder(binding) {
        fun bind(title: BaseRecyclerViewItem.Title) {
            binding.textTitleSeason.text = binding.textTitleSeason.context.getString(R.string.season_number, title.title)
        }
    }
}