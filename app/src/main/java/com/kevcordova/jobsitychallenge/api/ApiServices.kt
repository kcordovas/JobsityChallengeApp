package com.kevcordova.jobsitychallenge.api

import com.kevcordova.jobsitychallenge.model.network.EpisodeResponseServer
import com.kevcordova.jobsitychallenge.model.network.ShowResponseServer
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ShowService {

    @GET("${ApiConstants.ENDPOINT_SHOW_WITH_SLASH}{${ApiConstants.ID_SHOW}}")
    suspend fun getShow(@Path(ApiConstants.ID_SHOW) idShow: Int) : ShowResponseServer

    @GET(ApiConstants.ENDPOINT_SHOW)
    suspend fun getAllShows(): List<ShowResponseServer>

    @GET("${ApiConstants.ENDPOINT_SHOW}?")
    suspend fun searchByTitle(
        @Query("q") title: String
    ): List<ShowResponseServer>
}

interface EpisodeService {

    @GET("${ApiConstants.ENDPOINT_SHOW_WITH_SLASH}{${ApiConstants.ID_SHOW}}/${ApiConstants.ENDPOINT_EPISODE}")
    suspend fun getEpisodeByShow(@Path(ApiConstants.ID_SHOW) idShow: Int): List<EpisodeResponseServer>

    @GET("${ApiConstants.ENDPOINT_SHOW_WITH_SLASH}{${ApiConstants.ID_SHOW}}/${ApiConstants.ENDPOINT_EPISODE_BY_NUMBER}")
    suspend fun getEpisodeBySeasonAndChapter(
        @Path(ApiConstants.ID_SHOW) idShow: Int,
        @Query("season") seasonNumber: Int,
        @Query("number") chapterNumber: Int
    ): EpisodeResponseServer
}