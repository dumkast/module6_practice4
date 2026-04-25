package org.example.domain.usecase

import org.example.domain.model.Laureate
import org.example.domain.repository.PrizeRepository

class GetLaureatesUseCase(private val prizeRepository: PrizeRepository) {
    operator fun invoke(year: String, category: String): List<Laureate> {
        require(year.isNotBlank()) { "Год не может быть пустым" }
        require(category.isNotBlank()) { "Категория не может быть пустой" }
        return prizeRepository.getLaureates(year, category)
    }
}