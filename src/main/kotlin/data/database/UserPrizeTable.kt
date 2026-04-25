package org.example.data.database

import org.jetbrains.exposed.sql.Table

object UserPrizeTable : Table("user_prizes") {
    val userId = integer("user_id").references(UserTable.id)
    val prizeId = integer("prize_id").references(PrizeTable.id)

    override val primaryKey = PrimaryKey(userId, prizeId)
}