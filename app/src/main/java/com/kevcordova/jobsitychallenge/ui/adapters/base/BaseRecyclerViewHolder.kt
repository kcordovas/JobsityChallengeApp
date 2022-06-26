package com.kevcordova.jobsitychallenge.ui.adapters.base

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.example.baseandroidmodulekevcordova.extensions.bindGlideImage
import com.kevcordova.jobsitychallenge.R
import com.kevcordova.jobsitychallenge.databinding.ItemEpisodeListLayoutBinding
import com.kevcordova.jobsitychallenge.databinding.ItemEpisodeSeasonTitleLayoutBinding
import com.kevcordova.jobsitychallenge.databinding.ItemShowLayoutBinding

sealed class BaseRecyclerViewHolder(binding: ViewBinding) : RecyclerView.ViewHolder(binding.root) {

    var itemClickListener: ((view: View, item: BaseRecyclerViewItem, position: Int) -> Unit)? = null

    class ShowViewHolder(private val binding: ItemShowLayoutBinding) :
        BaseRecyclerViewHolder(binding) {
        fun bind(show: BaseRecyclerViewItem.ShowRecyclerViewItem) {
            binding.textTitleShowItem.text = show.title
            binding.imageCoverItemShow.bindGlideImage(
                show.imageUrl,
                R.drawable.ic_round_image_placeholder,
                R.drawable.ic_round_broken_image
            )
            binding.root.setOnClickListener {
                itemClickListener?.invoke(it, show, adapterPosition)
            }
        }
    }

    class EpisodeListViewHolder(private val binding: ItemEpisodeListLayoutBinding) :
        BaseRecyclerViewHolder(binding) {
        fun bind(episode: BaseRecyclerViewItem.EpisodeRecyclerViewItem) {
            binding.imageCoverEpisodeItem.bindGlideImage(
                episode.imageUrl,
                R.drawable.ic_round_image_placeholder,
                R.drawable.ic_round_broken_image
            )
            binding.textTitleEpisodeItem.text = episode.name
            binding.textEpisodeNumberItem.text = binding.textEpisodeNumberItem.context.getString(
                R.string.chapter_number,
                episode.numberChapter
            )
            binding.root.setOnClickListener {
                itemClickListener?.invoke(it, episode, adapterPosition)
            }
        }
    }


    class EpisodeSeasonViewHolder(private val binding: ItemEpisodeSeasonTitleLayoutBinding) :
        BaseRecyclerViewHolder(binding) {
        fun bind(title: BaseRecyclerViewItem.Title) {
            binding.textTitleSeason.text =
                binding.textTitleSeason.context.getString(R.string.season_number, title.title)
        }
    }
}