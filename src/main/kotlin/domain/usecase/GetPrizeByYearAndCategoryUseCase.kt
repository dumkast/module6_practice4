package org.example.domain.usecase

import org.example.domain.model.NobelPrize
import org.example.domain.repository.PrizeRepository

class GetPrizeByYearAndCategoryUseCase(private val prizeRepository: PrizeRepository) {
    suspend operator fun invoke(year: String, category: String): NobelPrize? {
        require(year.isNotBlank()) { "Год не может быть пустым" }
        require(category.isNotBlank()) { "Категория не может быть пустой" }
        return prizeRepository.findPrize(year, category)
    }
}