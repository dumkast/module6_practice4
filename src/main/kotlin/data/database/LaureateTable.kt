package org.example.data.database

import org.jetbrains.exposed.dao.id.IntIdTable

object LaureateTable : IntIdTable("laureates") {
    val externalId = varchar("external_id", 20) // ID лауреата из API (строка)
    val prizeId = integer("prize_id").references(PrizeTable.id) // Ссылка на ID премии

    val fullName = varchar("full_name", 255) // Полное имя лауреата
    val motivation = text("motivation").default("")
    val share = varchar("share", 10)
}