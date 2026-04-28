package org.example.presentation

import org.example.data.models.dto.*
import org.example.domain.usecase.*
import io.ktor.http.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.prizeRoutes(
    getAllPrizesUseCase: GetAllPrizesUseCase,
    getPrizeByYearAndCategoryUseCase: GetPrizeByYearAndCategoryUseCase,
    getLaureatesByPrizeUseCase: GetLaureatesByPrizeUseCase,
    addPrizeToFavoritesUseCase: AddPrizeToFavoritesUseCase,
    removePrizeFromFavoritesUseCase: RemovePrizeFromFavoritesUseCase,
    getUserFavoritesUseCase: GetUserFavoritesUseCase,
    getCurrentUserUseCase: GetCurrentUserUseCase
) {
    authenticate("auth-jwt") {
        get("/prizes") {
            val prizes = getAllPrizesUseCase()
            call.respond(prizes.map { prize ->
                PrizeResponse(
                    id = prize.id,
                    year = prize.year,
                    category = prize.category,
                    prizeAmount = prize.prizeAmount,
                    dateAwarded = prize.dateAwarded,
                    laureates = prize.laureates.map { laureate ->
                        LaureateResponse(
                            id = laureate.id,
                            fullName = laureate.fullName,
                            share = laureate.share,
                            motivation = laureate.motivation,
                        )
                    }
                )
            })
        }

        get("/prizes/{year}/{category}") {
            val year = call.parameters["year"]!!
            val category = call.parameters["category"]!!
            val prize = getPrizeByYearAndCategoryUseCase(year, category)
            if (prize != null) {
                call.respond(PrizeResponse(
                    id = prize.id,
                    year = prize.year,
                    category = prize.category,
                    prizeAmount = prize.prizeAmount,
                    dateAwarded = prize.dateAwarded,
                    laureates = prize.laureates.map {
                        LaureateResponse(
                            id = it.id,
                            fullName = it.fullName,
                            share = it.share,
                            motivation = it.motivation,
                        )
                    }
                ))
            } else {
                call.respond(HttpStatusCode.NotFound, ErrorResponse("not_found", "Prize not found"))
            }
        }

        get("/prizes/{year}/{category}/laureates") {
            val year = call.parameters["year"]!!
            val category = call.parameters["category"]!!
            call.respond(getLaureatesByPrizeUseCase(year, category).map {
                LaureateResponse(
                    id = it.id,
                    fullName = it.fullName,
                    share = it.share,
                    motivation = it.motivation,
                )
            })
        }

        get("/users/me") {
            val principal = call.principal<JWTPrincipal>()!!
            val username = principal.payload.subject!!
            val user = getCurrentUserUseCase(username)
            if (user != null) {
                call.respond(UserResponse(user.id, user.username, user.role))
            } else {
                call.respond(HttpStatusCode.NotFound, ErrorResponse("not_found", "User not found"))
            }
        }

        get("/users/me/prizes") {
            val principal = call.principal<JWTPrincipal>()!!
            val username = principal.payload.subject!!
            val user = getCurrentUserUseCase(username)
            if (user != null) {
                val prizes = getUserFavoritesUseCase(user.id)
                call.respond(prizes.map { prize ->
                    PrizeResponse(
                        id = prize.id,
                        year = prize.year,
                        category = prize.category,
                        prizeAmount = prize.prizeAmount,
                        dateAwarded = prize.dateAwarded,
                        laureates = prize.laureates.map { laureate ->
                            LaureateResponse(
                                id = laureate.id,
                                fullName = laureate.fullName,
                                share = laureate.share,
                                motivation = laureate.motivation,
                            )
                        }
                    )
                })
            } else {
                call.respond(HttpStatusCode.NotFound, ErrorResponse("not_found", "User not found"))
            }
        }

        post("/users/me/prizes/{prizeId}") {
            val principal = call.principal<JWTPrincipal>()!!
            val username = principal.payload.subject!!
            val user = getCurrentUserUseCase(username)
            if (user != null) {
                val prizeId = call.parameters["prizeId"]!!.toInt()
                addPrizeToFavoritesUseCase(user.id, prizeId)
                call.respond(HttpStatusCode.Created, mapOf("message" to "Prize added to favorites"))
            } else {
                call.respond(HttpStatusCode.NotFound, ErrorResponse("not_found", "User not found"))
            }
        }

        delete("/users/me/prizes/{prizeId}") {
            val principal = call.principal<JWTPrincipal>()!!
            val username = principal.payload.subject!!
            val user = getCurrentUserUseCase(username)
            if (user != null) {
                val prizeId = call.parameters["prizeId"]!!.toInt()
                removePrizeFromFavoritesUseCase(user.id, prizeId)
                call.respond(mapOf("message" to "Prize removed from favorites"))
            } else {
                call.respond(HttpStatusCode.NotFound, ErrorResponse("not_found", "User not found"))
            }
        }
    }
}