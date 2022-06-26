package com.kevcordova.jobsitychallenge.usescases

import com.kevcordova.jobsitychallenge.data.EpisodeRepository

class GetEpisodeByShowIdUseCase(
    private val episodeRepository: EpisodeRepository
) {
    suspend fun invoke(idShow: Int): List<Any> = episodeRepository.getEpisodeByShowId(idShow)
}