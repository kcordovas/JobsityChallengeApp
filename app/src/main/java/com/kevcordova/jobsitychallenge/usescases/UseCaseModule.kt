package com.kevcordova.jobsitychallenge.usescases

import com.kevcordova.jobsitychallenge.data.EpisodeRepository
import com.kevcordova.jobsitychallenge.data.ShowRepository
import dagger.Module
import dagger.Provides

@Module
class UseCaseModule {
    // Provider to Show
    @Provides
    fun getAllShowUseCaseProvider(showRepository: ShowRepository) =
        GetAllShowUseCase(showRepository)

    @Provides
    fun getShowByIdUseCaseProvider(showRepository: ShowRepository) =
        GetShowByIdUseCase(showRepository)

    @Provides
    fun searchByNameUseCaseProvider(showRepository: ShowRepository) =
        SearchByNameShowUseCase(showRepository)

    // Providers to Episode
    @Provides
    fun getEpisodeByShowIdUseCaseProvider(episodeRepository: EpisodeRepository) =
        GetEpisodeByShowIdUseCase(episodeRepository)

    @Provides
    fun getEpisodeBySeasonAndChapterUseCaseProvider(episodeRepository: EpisodeRepository) =
        GetEpisodeBySeasonAndChapter(episodeRepository)
}