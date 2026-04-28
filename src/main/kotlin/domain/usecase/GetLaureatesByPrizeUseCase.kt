package org.example.domain.usecase

import org.example.domain.model.Laureate
import org.example.domain.repository.PrizeRepository

class GetLaureatesByPrizeUseCase(private val prizeRepository: PrizeRepository) {
    suspend operator fun invoke(year: String, category: String): List<Laureate> {
        require(year.isNotBlank()) { "Год не может быть пустым" }
        require(category.isNotBlank()) { "Категория не может быть пустой" }
        return prizeRepository.getLaureatesByPrize(year, category)
    }
}