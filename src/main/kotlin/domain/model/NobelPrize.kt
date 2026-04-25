package org.example.domain.model

data class NobelPrize(
    val id: Int,
    val year: String,
    val category: String,
    val prizeAmount: Long,
    val dateAwarded: String,
    val laureates: List<Laureate>
)