package org.example.presentation

import org.example.data.models.dto.*
import org.example.domain.usecase.*
import io.ktor.http.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.prizeRoutes(
    getPrizesUseCase: GetPrizesUseCase,
    getPrizeDetailUseCase: GetPrizeDetailUseCase,
    getLaureatesUseCase: GetLaureatesUseCase,
    addFavoritePrizeUseCase: AddFavoritePrizeUseCase,
    removeFavoritePrizeUseCase: RemoveFavoritePrizeUseCase,
    getFavoritePrizesUseCase: GetFavoritePrizesUseCase,
    getUserProfileUseCase: GetUserProfileUseCase
) {
    authenticate("auth-jwt") {
        get("/prizes") {
            val prizes = getPrizesUseCase()
            call.respond(prizes.map { prize ->
                PrizeDetailResponse(
                    id = prize.id,
                    year = prize.year,
                    category = prize.category,
                    prizeAmount = prize.prizeAmount,
                    dateAwarded = prize.dateAwarded,
                    laureates = prize.laureates.map { laureate ->
                        LaureateResponse(
                            id = laureate.id,
                            fullName = laureate.fullName,
                            motivation = laureate.motivation,
                            share = laureate.share
                        )
                    }
                )
            })
        }

        get("/prizes/{year}/{category}") {
            val year = call.parameters["year"]!!
            val category = call.parameters["category"]!!
            val prize = getPrizeDetailUseCase(year, category)
            if (prize != null) {
                call.respond(PrizeDetailResponse(
                    id = prize.id,
                    year = prize.year,
                    category = prize.category,
                    prizeAmount = prize.prizeAmount,
                    dateAwarded = prize.dateAwarded,
                    laureates = prize.laureates.map {
                        LaureateResponse(
                            id = it.id,
                            fullName = it.fullName,
                            motivation = it.motivation,
                            share = it.share
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
            call.respond(getLaureatesUseCase(year, category).map {
                LaureateResponse(
                    id = it.id,
                    fullName = it.fullName,
                    motivation = it.motivation,
                    share = it.share
                )
            })
        }

        get("/users/me") {
            val principal = call.principal<JWTPrincipal>()!!
            val username = principal.payload.subject!!
            val user = getUserProfileUseCase(username)
            if (user != null) {
                call.respond(UserProfileResponse(user.id, user.username, user.role))
            } else {
                call.respond(HttpStatusCode.NotFound, ErrorResponse("not_found", "User not found"))
            }
        }

        get("/users/me/prizes") {
            val principal = call.principal<JWTPrincipal>()!!
            val username = principal.payload.subject!!
            val user = getUserProfileUseCase(username)
            if (user != null) {
                val prizes = getFavoritePrizesUseCase(user.id)
                call.respond(prizes.map { prize ->
                    PrizeDetailResponse(
                        id = prize.id,
                        year = prize.year,
                        category = prize.category,
                        prizeAmount = prize.prizeAmount,
                        dateAwarded = prize.dateAwarded,
                        laureates = prize.laureates.map { laureate ->
                            LaureateResponse(
                                id = laureate.id,
                                fullName = laureate.fullName,
                                motivation = laureate.motivation,
                                share = laureate.share
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
            val user = getUserProfileUseCase(username)
            if (user != null) {
                val prizeId = call.parameters["prizeId"]!!.toInt()
                addFavoritePrizeUseCase(user.id, prizeId)
                call.respond(HttpStatusCode.Created, mapOf("message" to "Prize added to favorites"))
            } else {
                call.respond(HttpStatusCode.NotFound, ErrorResponse("not_found", "User not found"))
            }
        }

        delete("/users/me/prizes/{prizeId}") {
            val principal = call.principal<JWTPrincipal>()!!
            val username = principal.payload.subject!!
            val user = getUserProfileUseCase(username)
            if (user != null) {
                val prizeId = call.parameters["prizeId"]!!.toInt()
                removeFavoritePrizeUseCase(user.id, prizeId)
                call.respond(mapOf("message" to "Prize removed from favorites"))
            } else {
                call.respond(HttpStatusCode.NotFound, ErrorResponse("not_found", "User not found"))
            }
        }
    }
}