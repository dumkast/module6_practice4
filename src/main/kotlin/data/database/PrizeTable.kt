package org.example.data.database

import org.jetbrains.exposed.dao.id.IntIdTable

object PrizeTable : IntIdTable("prizes") {
    val awardYear = varchar("award_year", 10)
    val category = varchar("category", 100)
    val prizeAmount = long("prize_amount")
    val dateAwarded = varchar("date_awarded", 20)
}