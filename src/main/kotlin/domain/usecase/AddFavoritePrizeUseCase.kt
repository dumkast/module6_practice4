package org.example.domain.usecase

import org.example.domain.repository.PrizeRepository

class AddFavoritePrizeUseCase(private val prizeRepository: PrizeRepository) {
    suspend operator fun invoke(userId: Int, prizeId: Int) {
        prizeRepository.addFavoritePrize(userId, prizeId)
    }
}