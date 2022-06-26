package com.kevcordova.jobsitychallenge.model.mappers

import com.kevcordova.jobsitychallenge.model.parcelables.ShowParcelable
import com.kevcordova.jobsitychallenge.ui.adapters.base.BaseRecyclerViewItem

fun BaseRecyclerViewItem.ShowRecyclerViewItem.toParcelable() = ShowParcelable(
    id,
    title,
    imageUrl
)