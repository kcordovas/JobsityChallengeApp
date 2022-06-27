package com.kevcordova.jobsitychallenge.api

import com.kevcordova.jobsitychallenge.data.RemoteEpisodeDataSource
import com.kevcordova.jobsitychallenge.data.RemoteShowDataSource
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ApiModule {

    @Provides
    @Singleton
    fun showRequestProvider() = ShowRequest

    @Provides
    @Singleton
    fun episodeRequestProvider() = EpisodeRequest

    @Provides
    fun remoteShowDataSourceProvider(
        showRequest: ShowRequest
    ) : RemoteShowDataSource = ShowRetrofitDataSource(showRequest)

    @Provides
    fun remoteEpisodeDataSourceProvider(
        episodeRequest: EpisodeRequest
    ) : RemoteEpisodeDataSource = EpisodeRetrofitDataSource(episodeRequest)
}