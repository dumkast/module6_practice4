package org.example.domain.usecase

import org.example.domain.model.NobelPrize
import org.example.domain.repository.PrizeRepository

class GetUserFavoritesUseCase(private val prizeRepository: PrizeRepository) {
    suspend operator fun invoke(userId: Int): List<NobelPrize> {
        return prizeRepository.getFavorites(userId)
    }
}