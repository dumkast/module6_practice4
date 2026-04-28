package org.example.data.database

import org.jetbrains.exposed.sql.Table

object UserPrizeTable : Table("user_prizes") {
    val userId = integer("user_id").references(UserTable.id)
    val prizeId = integer("prize_id").references(PrizeTable.id)
    val addedAt = varchar("added_at", 50)

    override val primaryKey = PrimaryKey(userId, prizeId)
}