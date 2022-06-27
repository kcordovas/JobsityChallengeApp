package com.kevcordova.jobsitychallenge.dependencyinyection

import com.kevcordova.jobsitychallenge.usescases.*
import dagger.Subcomponent

@Subcomponent(modules = [(UseCaseModule::class)])
interface UseCaseComponent {
    val getAllShowUseCase: GetAllShowUseCase
    val searchByNameUseCase : SearchByNameShowUseCase
    val getShowByIdUseCase : GetShowByIdUseCase
    val getEpisodeByShowIdUseCase: GetEpisodeByShowIdUseCase
    val getEpisodeBySeasonAndChapter : GetEpisodeBySeasonAndChapter
}