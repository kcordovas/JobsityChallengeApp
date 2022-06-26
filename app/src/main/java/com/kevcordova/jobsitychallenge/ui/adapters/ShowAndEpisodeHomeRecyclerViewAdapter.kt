package com.kevcordova.jobsitychallenge.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kevcordova.jobsitychallenge.R
import com.kevcordova.jobsitychallenge.databinding.ItemEpisodeListLayoutBinding
import com.kevcordova.jobsitychallenge.databinding.ItemEpisodeSeasonTitleLayoutBinding
import com.kevcordova.jobsitychallenge.databinding.ItemShowLayoutBinding
import com.kevcordova.jobsitychallenge.ui.adapters.base.BaseRecyclerViewHolder
import com.kevcordova.jobsitychallenge.ui.adapters.base.BaseRecyclerViewItem
import java.lang.IllegalArgumentException

class ShowAndEpisodeHomeRecyclerViewAdapter : RecyclerView.Adapter<BaseRecyclerViewHolder>() {
    var items = listOf<BaseRecyclerViewItem>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    var itemClickListener: ((view: View, item: BaseRecyclerViewItem, position: Int) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseRecyclerViewHolder {
        return when (viewType) {
            R.layout.item_episode_list_layout -> BaseRecyclerViewHolder.EpisodeListViewHolder(
                ItemEpisodeListLayoutBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
            R.layout.item_episode_season_title_layout -> BaseRecyclerViewHolder.EpisodeSeasonViewHolder(
                ItemEpisodeSeasonTitleLayoutBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
            R.layout.item_show_layout -> BaseRecyclerViewHolder.ShowViewHolder(
                ItemShowLayoutBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
            else -> throw IllegalArgumentException("Invalid ViewType provided")
        }
    }

    override fun onBindViewHolder(holder: BaseRecyclerViewHolder, position: Int) {
        holder.itemClickListener = itemClickListener
        when (holder) {
            is BaseRecyclerViewHolder.ShowViewHolder -> holder.bind(items[position] as BaseRecyclerViewItem.ShowRecyclerViewItem)
            is BaseRecyclerViewHolder.EpisodeListViewHolder -> holder.bind(items[position] as BaseRecyclerViewItem.EpisodeRecyclerViewItem)
            is BaseRecyclerViewHolder.EpisodeSeasonViewHolder -> holder.bind(items[position] as BaseRecyclerViewItem.Title)
        }
    }

    override fun getItemCount(): Int = items.size

    override fun getItemViewType(position: Int): Int =
        when (items[position]) {
            is BaseRecyclerViewItem.EpisodeRecyclerViewItem -> R.layout.item_episode_list_layout
            is BaseRecyclerViewItem.ShowRecyclerViewItem -> R.layout.item_show_layout
            is BaseRecyclerViewItem.Title -> R.layout.item_episode_season_title_layout
        }
}