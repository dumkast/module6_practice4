package org.example.data.repository

import org.example.data.source.NobelPrizeDataSource
import org.example.domain.model.Laureate
import org.example.domain.model.NobelPrize
import org.example.domain.repository.PrizeRepository

class PrizeRepositoryImpl : PrizeRepository {

    override fun getAllPrizes(): List<NobelPrize> {
        return NobelPrizeDataSource.prizes
    }

    override fun getPrize(year: String, category: String): NobelPrize? {
        return NobelPrizeDataSource.prizes.find {
            it.year == year && it.category == category
        }
    }

    override fun getLaureates(year: String, category: String): List<Laureate> {
        return getPrize(year, category)?.laureates ?: emptyList()
    }
}