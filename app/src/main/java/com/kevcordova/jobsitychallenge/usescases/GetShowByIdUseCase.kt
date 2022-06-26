package com.kevcordova.jobsitychallenge.usescases

import com.kevcordova.jobsitychallenge.data.ShowRepository

class GetShowByIdUseCase(
    private val showRepository: ShowRepository
) {
    suspend fun invoke(idShow: Int) = showRepository.getShowById(idShow)
}