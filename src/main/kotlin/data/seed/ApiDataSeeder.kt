package org.example.data.seed

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import org.example.data.database.LaureateTable
import org.example.data.database.PrizeTable
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction

@Serializable
data class NobelPrizesApiResponse(
    val nobelPrizes: List<NobelPrizeFromApi>? = null
)

@Serializable
data class NobelPrizeFromApi(
    val awardYear: String? = null,
    val category: CategoryFromApi? = null,
    val prizeAmount: Long? = null,
    val dateAwarded: String? = null,
    val laureates: List<LaureateFromApi>? = null,
    val links: List<LinkFromApi>? = null
)

@Serializable
data class CategoryFromApi(
    val en: String? = null
)

@Serializable
data class LaureateFromApi(
    val id: String? = null,
    val fullName: FullNameFromApi? = null,
    val knownName: KnownNameFromApi? = null,
    val motivation: MotivationFromApi? = null,
    val portion: String? = null,
    val links: List<LinkFromApi>? = null
)

@Serializable
data class FullNameFromApi(
    val en: String? = null
)

@Serializable
data class KnownNameFromApi(
    val en: String? = null
)

@Serializable
data class MotivationFromApi(
    val en: String? = null
)

@Serializable
data class LinkFromApi(
    val rel: String? = null,
    val href: String? = null,
    val action: String? = null,
    val types: String? = null
)

object ApiDataSeeder {
    private val json = Json {
        ignoreUnknownKeys = true
        isLenient = true
    }

    fun seedFromApi() {
        println("=== Starting API data seed ===")
        val isEmpty = transaction { PrizeTable.selectAll().count() == 0L }

        if (!isEmpty) {
            println("Prizes already loaded, skipping")
            return
        }

        val allPrizes = mutableListOf<NobelPrizeFromApi>()

        runBlocking {
            val client = HttpClient(CIO)
            try {
                val url = "https://api.nobelprize.org/2.1/nobelPrizes?offset=0&limit=100"
                val response: HttpResponse = client.get(url)
                val body: String = response.bodyAsText()
                val apiResponse = json.decodeFromString<NobelPrizesApiResponse>(body)
                apiResponse.nobelPrizes?.let { allPrizes.addAll(it) }
            } catch (e: Exception) {
                println("ERROR loading from API: ${e.message}")
            } finally {
                client.close()
            }
        }

        if (allPrizes.isNotEmpty()) {
            transaction {
                allPrizes.forEach { prize ->
                    try {
                        val year = prize.awardYear ?: return@forEach
                        val prizeCategory = prize.category?.en ?: return@forEach

                        val insertedPrize = PrizeTable.insert {
                            it[awardYear] = year
                            it[PrizeTable.category] = prizeCategory
                            it[prizeAmount] = prize.prizeAmount ?: 0L
                            it[dateAwarded] = prize.dateAwarded ?: ""
                        }

                        val prizeIdValue = insertedPrize[PrizeTable.id].value

                        prize.laureates?.forEach { laureate ->
                            LaureateTable.insert {
                                it[LaureateTable.externalId] = laureate.id ?: ""
                                it[LaureateTable.prizeId] = prizeIdValue
                                it[LaureateTable.fullName] = laureate.fullName?.en ?: laureate.knownName?.en ?: "Unknown"
                                it[LaureateTable.share] = laureate.portion ?: "1"
                                it[LaureateTable.motivation] = laureate.motivation?.en ?: ""
                            }
                        }
                    } catch (e: Exception) {
                        println("ERROR saving prize: ${e.message}")
                    }
                }
            }
            println("Seeding complete.")
        }
    }
}