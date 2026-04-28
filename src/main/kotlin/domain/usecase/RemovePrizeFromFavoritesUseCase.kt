package org.example.domain.usecase

import org.example.domain.repository.PrizeRepository

class RemovePrizeFromFavoritesUseCase(private val prizeRepository: PrizeRepository) {
    suspend operator fun invoke(userId: Int, prizeId: Int) {
        prizeRepository.removeFromFavorites(userId, prizeId)
    }
}