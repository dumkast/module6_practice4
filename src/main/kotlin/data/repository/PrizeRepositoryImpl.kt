package org.example.data.repository

import org.example.data.database.LaureateTable
import org.example.data.database.PrizeTable
import org.example.data.database.UserPrizeTable
import org.example.domain.model.Laureate
import org.example.domain.model.NobelPrize
import org.example.domain.repository.PrizeRepository
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import java.time.LocalDateTime

class PrizeRepositoryImpl : PrizeRepository {

    override suspend fun getAllPrizes(): List<NobelPrize> = newSuspendedTransaction {
        PrizeTable.selectAll().map { row ->
            val prizeId = row[PrizeTable.id].value
            NobelPrize(
                id = prizeId,
                year = row[PrizeTable.awardYear],
                category = row[PrizeTable.category],
                prizeAmount = row[PrizeTable.prizeAmount],
                dateAwarded = row[PrizeTable.dateAwarded],
                laureates = LaureateTable.selectAll().where {
                    LaureateTable.prizeId eq prizeId
                }.map {
                    Laureate(
                        id = it[LaureateTable.externalId],
                        fullName = it[LaureateTable.fullName],
                        share = it[LaureateTable.share],
                        motivation = it[LaureateTable.motivation],
                    )
                }
            )
        }
    }

    override suspend fun findPrize(year: String, category: String): NobelPrize? = newSuspendedTransaction {
        PrizeTable.selectAll().where {
            (PrizeTable.awardYear eq year) and (PrizeTable.category eq category)
        }.firstOrNull()?.let { row ->
            val prizeId = row[PrizeTable.id].value
            NobelPrize(
                id = prizeId,
                year = row[PrizeTable.awardYear],
                category = row[PrizeTable.category],
                prizeAmount = row[PrizeTable.prizeAmount],
                dateAwarded = row[PrizeTable.dateAwarded],
                laureates = LaureateTable.selectAll().where {
                    LaureateTable.prizeId eq prizeId
                }.map {
                    Laureate(
                        id = it[LaureateTable.externalId],
                        fullName = it[LaureateTable.fullName],
                        share = it[LaureateTable.share],
                        motivation = it[LaureateTable.motivation],
                    )
                }
            )
        }
    }

    override suspend fun getLaureatesByPrize(year: String, category: String): List<Laureate> = newSuspendedTransaction {
        val prizeRow = PrizeTable.selectAll().where {
            (PrizeTable.awardYear eq year) and (PrizeTable.category eq category)
        }.firstOrNull() ?: return@newSuspendedTransaction emptyList()

        val prizeId = prizeRow[PrizeTable.id].value
        LaureateTable.selectAll().where {
            LaureateTable.prizeId eq prizeId
        }.map {
            Laureate(
                id = it[LaureateTable.externalId],
                fullName = it[LaureateTable.fullName],
                share = it[LaureateTable.share],
                motivation = it[LaureateTable.motivation],
            )
        }
    }

    override suspend fun addToFavorites(userId: Int, prizeId: Int) {
        newSuspendedTransaction {
            UserPrizeTable.insert {
                it[UserPrizeTable.userId] = userId
                it[UserPrizeTable.prizeId] = prizeId
                it[UserPrizeTable.addedAt] = java.time.LocalDateTime.now().toString().replace('T', ' ')
            }
        }
    }

    override suspend fun removeFromFavorites(userId: Int, prizeId: Int) {
        newSuspendedTransaction {
            UserPrizeTable.deleteWhere {
                (UserPrizeTable.userId eq userId) and (UserPrizeTable.prizeId eq prizeId)
            }
        }
    }

    override suspend fun getFavorites(userId: Int): List<NobelPrize> = newSuspendedTransaction {
        (PrizeTable innerJoin UserPrizeTable)
            .selectAll()
            .where { UserPrizeTable.userId eq userId }
            .map { row ->
                val prizeId = row[PrizeTable.id].value
                NobelPrize(
                    id = prizeId,
                    year = row[PrizeTable.awardYear],
                    category = row[PrizeTable.category],
                    prizeAmount = row[PrizeTable.prizeAmount],
                    dateAwarded = row[PrizeTable.dateAwarded],
                    laureates = LaureateTable.selectAll().where {
                        LaureateTable.prizeId eq prizeId
                    }.map {
                        Laureate(
                            id = it[LaureateTable.externalId],
                            fullName = it[LaureateTable.fullName],
                            share = it[LaureateTable.share],
                            motivation = it[LaureateTable.motivation],
                        )
                    }
                )
            }
    }
}