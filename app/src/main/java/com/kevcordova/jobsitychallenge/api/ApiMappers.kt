package com.kevcordova.jobsitychallenge.api

import com.kevcordova.jobsitychallenge.domain.Episode
import com.kevcordova.jobsitychallenge.domain.Schedule
import com.kevcordova.jobsitychallenge.domain.Show

fun ShowResponseServer.toShow(): Show = Show(
    id = id ?: -1,
    name = name ?: "",
    summary = summary ?: "",
    genders = genres,
    premiered = premiered ?: "",
    ended = ended ?: "",
    imageUrl = image?.medium ?: "",
    schedule = Schedule(
        schedule?.time ?: "",
        schedule?.days?.toList() ?: emptyList()
    )
)

fun EpisodeResponseServer.toEpisode(): Episode = Episode(
    id = id ?: -1,
    name = name ?: "",
    summary = summary ?: "",
    numberChapter = number ?: -1,
    season = season ?: -1,
    imageUrl = image?.medium ?: ""
)