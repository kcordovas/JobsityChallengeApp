package com.kevcordova.jobsitychallenge.data

import dagger.Module
import dagger.Provides

@Module
class RepositoryModule {

    @Provides
    fun showRepositoryProvider(
        remoteShowDataSource: RemoteShowDataSource
    ) = ShowRepository(remoteShowDataSource)

    @Provides
    fun episodeRepositoryProvider(
        remoteEpisodeDataSource: RemoteEpisodeDataSource
    ) = EpisodeRepository(remoteEpisodeDataSource)
}