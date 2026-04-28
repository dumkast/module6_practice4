package org.example.domain.repository

import org.example.domain.model.Laureate
import org.example.domain.model.NobelPrize

interface PrizeRepository {
    suspend fun getAllPrizes(): List<NobelPrize>
    suspend fun findPrize(year: String, category: String): NobelPrize?
    suspend fun getLaureatesByPrize(year: String, category: String): List<Laureate>
    suspend fun addToFavorites(userId: Int, prizeId: Int)
    suspend fun removeFromFavorites(userId: Int, prizeId: Int)
    suspend fun getFavorites(userId: Int): List<NobelPrize>
}