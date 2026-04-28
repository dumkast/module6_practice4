package org.example.data.database

import org.jetbrains.exposed.dao.id.IntIdTable

object LaureateTable : IntIdTable("laureates") {
    val externalId = varchar("external_id", 20)
    val prizeId = integer("prize_id").references(PrizeTable.id)
    val fullName = varchar("full_name", 255)
    val share = varchar("share", 20)
    val motivation = text("motivation").default("")
}