package com.kevcordova.jobsitychallenge

import android.app.Application
import com.kevcordova.jobsitychallenge.api.EpisodeRequest
import com.kevcordova.jobsitychallenge.api.EpisodeRetrofitDataSource
import com.kevcordova.jobsitychallenge.api.ShowRequest
import com.kevcordova.jobsitychallenge.api.ShowRetrofitDataSource
import com.kevcordova.jobsitychallenge.data.EpisodeRepository
import com.kevcordova.jobsitychallenge.data.RemoteEpisodeDataSource
import com.kevcordova.jobsitychallenge.data.RemoteShowDataSource
import com.kevcordova.jobsitychallenge.data.ShowRepository

class JobsityChallengeApplication : Application() {

    private val remoteShowDataSource: RemoteShowDataSource by lazy {
        ShowRetrofitDataSource(ShowRequest)
    }
    private val remoteEpisodeDataSource: RemoteEpisodeDataSource by lazy {
        EpisodeRetrofitDataSource(EpisodeRequest)
    }

    val showRepository : ShowRepository by lazy {
        ShowRepository(remoteShowDataSource)
    }
    val episodeRepository : EpisodeRepository by lazy {
        EpisodeRepository(remoteEpisodeDataSource)
    }
}