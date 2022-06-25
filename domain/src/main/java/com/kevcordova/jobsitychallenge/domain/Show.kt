package com.kevcordova.jobsitychallenge.domain

data class Show(
    val id: Int,
    val name: String,
    val summary: String,
    val genders: List<String>,
    val premiered: String,
    val ended: String,
    val imageUrl: String,
    val schedule: Schedule
)

data class Schedule(
    val time: String,
    val days: List<String>
)
