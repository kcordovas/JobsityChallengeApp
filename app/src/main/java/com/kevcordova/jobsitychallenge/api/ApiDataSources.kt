package com.kevcordova.jobsitychallenge.api

import com.kevcordova.jobsitychallenge.data.RemoteEpisodeDataSource
import com.kevcordova.jobsitychallenge.data.RemoteShowDataSource
import com.kevcordova.jobsitychallenge.domain.Episode
import com.kevcordova.jobsitychallenge.domain.Show
import com.kevcordova.jobsitychallenge.model.mappers.toEpisode
import com.kevcordova.jobsitychallenge.model.mappers.toShow

class ShowRetrofitDataSource(
    showRequest: ShowRequest
) : RemoteShowDataSource {
    private val showService = showRequest.getService<ShowService>()

    override suspend fun getShow(idShow: Int): Show = showService.getShow(idShow).toShow()

    override suspend fun getAllShows(): List<Show> =
        showService.getAllShows().map {
            it.toShow()
        }

    override suspend fun searchShowByName(title: String): List<Show> =
        showService.searchByTitle(title).map {
            it.toShow()
        }
}

class EpisodeRetrofitDataSource(
    episodeRequest: EpisodeRequest
) : RemoteEpisodeDataSource {

    private val episodeService = episodeRequest.getService<EpisodeService>()
    override suspend fun getEpisodesByShow(idShow: Int): List<Episode> =
        episodeService.getEpisodeByShow(idShow).map {
            it.toEpisode()
        }

    override suspend fun getEpisodeBySeasonAndChapter(
        idShow: Int,
        seasonNumber: Int,
        chapterNumber: Int
    ): Episode = episodeService.getEpisodeBySeasonAndChapter(idShow, seasonNumber, chapterNumber).toEpisode()

}