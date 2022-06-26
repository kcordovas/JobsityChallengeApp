package com.kevcordova.jobsitychallenge.model.parcelables

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ShowParcelable(
    val id: Int,
    val title: String,
    val imageUrl: String
) : Parcelable
