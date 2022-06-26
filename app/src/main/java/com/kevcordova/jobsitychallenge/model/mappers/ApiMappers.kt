package com.kevcordova.jobsitychallenge.model.mappers

import com.kevcordova.jobsitychallenge.domain.Episode
import com.kevcordova.jobsitychallenge.domain.Schedule
import com.kevcordova.jobsitychallenge.domain.Show
import com.kevcordova.jobsitychallenge.model.network.EpisodeResponseServer
import com.kevcordova.jobsitychallenge.model.network.ShowResponseServer
import com.kevcordova.jobsitychallenge.model.network.ShowSearchResponseServer

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

fun ShowSearchResponseServer.toShow(): Show = Show(
    id = show?.id ?: -1,
    name = show?.name ?: "",
    summary = show?.summary ?: "",
    genders = show?.genres ?: emptyList(),
    premiered = show?.premiered ?: "",
    ended = show?.ended ?: "",
    imageUrl = show?.image?.medium ?: "",
    schedule = Schedule(
        show?.schedule?.time ?: "",
        show?.schedule?.days?.toList() ?: emptyList()
    )
)