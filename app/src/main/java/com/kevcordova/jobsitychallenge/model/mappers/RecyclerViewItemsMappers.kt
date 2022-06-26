package com.kevcordova.jobsitychallenge.model.mappers

import com.kevcordova.jobsitychallenge.domain.Episode
import com.kevcordova.jobsitychallenge.domain.Show
import com.kevcordova.jobsitychallenge.ui.adapters.base.BaseRecyclerViewItem

fun Show.toShowRecyclerViewItem() = BaseRecyclerViewItem.ShowRecyclerViewItem(
    id = id,
    title = name,
    imageUrl = imageUrl
)

fun Episode.toEpisodeRecyclerViewItem() = BaseRecyclerViewItem.EpisodeRecyclerViewItem(
    id,
    name,
    numberChapter,
    season,
    imageUrl
)