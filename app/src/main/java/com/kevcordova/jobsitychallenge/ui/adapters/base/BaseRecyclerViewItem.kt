package com.kevcordova.jobsitychallenge.ui.adapters.base

sealed class BaseRecyclerViewItem {

    class Title(
        val id: Int,
        val title: Int
    ) : BaseRecyclerViewItem()

    class EpisodeRecyclerViewItem(
        val id: Int,
        val name: String,
        val summary: String,
        val numberChapter: Int,
        val season: Int,
        val imageUrl: String
    ) : BaseRecyclerViewItem()

    class ShowRecyclerViewItem(
        val id: Int,
        val title: String,
        val imageUrl: String
    ) : BaseRecyclerViewItem()
}
