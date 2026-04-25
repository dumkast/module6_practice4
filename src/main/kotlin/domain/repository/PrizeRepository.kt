package org.example.domain.repository

import org.example.domain.model.Laureate
import org.example.domain.model.NobelPrize

interface PrizeRepository {
    fun getAllPrizes(): List<NobelPrize>
    fun getPrize(year: String, category: String): NobelPrize?
    fun getLaureates(year: String, category: String): List<Laureate>
}