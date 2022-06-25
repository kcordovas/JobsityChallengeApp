package com.kevcordova.jobsitychallenge.domain

data class Episode(
    val id: Int,
    val name: String,
    val summary: String,
    val numberChapter: Int,
    val season: Int,
    val imageUrl: String
)
