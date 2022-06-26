package com.kevcordova.jobsitychallenge.usescases

import com.kevcordova.jobsitychallenge.data.EpisodeRepository
import com.kevcordova.jobsitychallenge.domain.Episode

class GetEpisodeByShowIdUseCase(
    private val episodeRepository: EpisodeRepository
) {
    suspend fun invoke(idShow: Int): List<Episode> = episodeRepository.getEpisodeByShowId(idShow)
}