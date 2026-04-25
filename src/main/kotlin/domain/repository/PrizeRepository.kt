package org.example.domain.repository

import org.example.domain.model.Laureate
import org.example.domain.model.NobelPrize

interface PrizeRepository {
    suspend fun getAllPrizes(): List<NobelPrize>
    suspend fun getPrize(year: String, category: String): NobelPrize?
    suspend fun getLaureates(year: String, category: String): List<Laureate>
    suspend fun addFavoritePrize(userId: Int, prizeId: Int)
    suspend fun removeFavoritePrize(userId: Int, prizeId: Int)
    suspend fun getFavoritePrizes(userId: Int): List<NobelPrize>
}