package com.kevcordova.jobsitychallenge.usescases

import com.kevcordova.jobsitychallenge.data.ShowRepository

class SearchByNameShowUseCase(
    private val showRepository: ShowRepository
) {
    suspend fun invoke(name: String) = showRepository.searchByName(name)
}