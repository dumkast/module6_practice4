package org.example.domain.usecase

import org.example.domain.model.NobelPrize
import org.example.domain.repository.PrizeRepository

class GetAllPrizesUseCase(private val prizeRepository: PrizeRepository) {
    suspend operator fun invoke(): List<NobelPrize> {
        return prizeRepository.getAllPrizes()
    }
}