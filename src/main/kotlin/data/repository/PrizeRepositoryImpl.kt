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
                        motivation = it[LaureateTable.motivation],
                        share = it[LaureateTable.share]
                    )
                }
            )
        }
    }

    override suspend fun getPrize(year: String, category: String): NobelPrize? = newSuspendedTransaction {
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
                        motivation = it[LaureateTable.motivation],
                        share = it[LaureateTable.share]
                    )
                }
            )
        }
    }

    override suspend fun getLaureates(year: String, category: String): List<Laureate> = newSuspendedTransaction {
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
                motivation = it[LaureateTable.motivation],
                share = it[LaureateTable.share]
            )
        }
    }

    override suspend fun addFavoritePrize(userId: Int, prizeId: Int) {
        newSuspendedTransaction {
            UserPrizeTable.insert {
                it[UserPrizeTable.userId] = userId
                it[UserPrizeTable.prizeId] = prizeId
            }
        }
    }

    override suspend fun removeFavoritePrize(userId: Int, prizeId: Int) {
        newSuspendedTransaction {
            UserPrizeTable.deleteWhere {
                (UserPrizeTable.userId eq userId) and (UserPrizeTable.prizeId eq prizeId)
            }
        }
    }

    override suspend fun getFavoritePrizes(userId: Int): List<NobelPrize> = newSuspendedTransaction {
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
                            motivation = it[LaureateTable.motivation],
                            share = it[LaureateTable.share]
                        )
                    }
                )
            }
    }
}