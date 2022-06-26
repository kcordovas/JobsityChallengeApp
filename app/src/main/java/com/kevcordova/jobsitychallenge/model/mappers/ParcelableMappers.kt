package com.kevcordova.jobsitychallenge.model.mappers

import com.kevcordova.jobsitychallenge.model.parcelables.EpisodeParcelable
import com.kevcordova.jobsitychallenge.model.parcelables.ShowParcelable
import com.kevcordova.jobsitychallenge.ui.adapters.base.BaseRecyclerViewItem

fun BaseRecyclerViewItem.ShowRecyclerViewItem.toParcelable() = ShowParcelable(
    id,
    title,
    imageUrl
)

fun BaseRecyclerViewItem.EpisodeRecyclerViewItem.toParcelable() = EpisodeParcelable(
    id,
    name,
    numberChapter,
    season,
    imageUrl
)