package com.kevcordova.jobsitychallenge.usescases

import com.kevcordova.jobsitychallenge.data.ShowRepository

class GetAllShowUseCase(
    private val showRepository: ShowRepository
) {
    suspend fun invoke() = showRepository.getAll()
}