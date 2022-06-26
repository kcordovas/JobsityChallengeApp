package com.kevcordova.jobsitychallenge.usescases

import com.kevcordova.jobsitychallenge.data.EpisodeRepository

class GetEpisodeBySeasonAndChapter(
    private val episodeRepository: EpisodeRepository
) {
    suspend fun invoke(idShow: Int, seasonNumber: Int, chapterNumber: Int) = episodeRepository.getEpisodeBySeasonAndChapter(idShow, seasonNumber, chapterNumber)
}