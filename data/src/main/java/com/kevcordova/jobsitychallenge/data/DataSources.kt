package com.kevcordova.jobsitychallenge.data

import com.kevcordova.jobsitychallenge.domain.Episode
import com.kevcordova.jobsitychallenge.domain.Show

interface RemoteShowDataSource {
    suspend fun getShow(idShow: Int): Show
    suspend fun getAllShows(): List<Show>
    suspend fun searchShowByName(title: String): List<Show>
}

interface RemoteEpisodeDataSource {
    suspend fun getEpisodesByShow(idShow: Int): List<Episode>
    suspend fun getEpisodeBySeasonAndChapter(idShow: Int, seasonNumber: Int, chapterNumber: Int): Episode
}