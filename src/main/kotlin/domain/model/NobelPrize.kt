package org.example.domain.model

data class NobelPrize(
    val year: String,
    val category: String,
    val prizeAmount: Long,
    val laureates: List<Laureate>
)