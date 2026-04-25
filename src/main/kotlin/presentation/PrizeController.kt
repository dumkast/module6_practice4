package org.example.presentation

import org.example.domain.usecase.GetLaureatesUseCase
import org.example.domain.usecase.GetPrizeDetailUseCase
import org.example.domain.usecase.GetPrizesUseCase
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import org.example.data.models.dto.*

class PrizeController(
    private val getPrizesUseCase: GetPrizesUseCase,
    private val getPrizeDetailUseCase: GetPrizeDetailUseCase,
    private val getLaureatesUseCase: GetLaureatesUseCase
) {

    suspend fun getAllPrizes(call: ApplicationCall) {
        val prizes = getPrizesUseCase()
        val response = prizes.map { prize ->
            PrizeResponse(
                year = prize.year,
                category = prize.category,
                prizeAmount = prize.prizeAmount,
                laureatesCount = prize.laureates.size
            )
        }
        call.respond(HttpStatusCode.OK, response)
    }

    suspend fun getPrizeDetail(call: ApplicationCall) {
        val year = call.parameters["year"] ?: return call.respond(
            HttpStatusCode.BadRequest,
            ErrorResponse("validation_error", "Год не указан")
        )
        val category = call.parameters["category"] ?: return call.respond(
            HttpStatusCode.BadRequest,
            ErrorResponse("validation_error", "Категория не указана")
        )

        val prize = getPrizeDetailUseCase(year, category)
        if (prize != null) {
            call.respond(
                HttpStatusCode.OK,
                PrizeDetailResponse(
                    year = prize.year,
                    category = prize.category,
                    prizeAmount = prize.prizeAmount,
                    laureates = prize.laureates.map {
                        LaureateResponse(it.id, it.firstName, it.surname, it.motivation, it.share)
                    }
                )
            )
        } else {
            call.respond(
                HttpStatusCode.NotFound,
                ErrorResponse("not_found", "Премия за $year год в категории $category не найдена")
            )
        }
    }

    suspend fun getLaureates(call: ApplicationCall) {
        val year = call.parameters["year"] ?: return call.respond(
            HttpStatusCode.BadRequest,
            ErrorResponse("validation_error", "Год не указан")
        )
        val category = call.parameters["category"] ?: return call.respond(
            HttpStatusCode.BadRequest,
            ErrorResponse("validation_error", "Категория не указана")
        )

        val laureates = getLaureatesUseCase(year, category)
        if (laureates.isNotEmpty()) {
            call.respond(
                HttpStatusCode.OK,
                laureates.map {
                    LaureateResponse(it.id, it.firstName, it.surname, it.motivation, it.share)
                }
            )
        } else {
            call.respond(
                HttpStatusCode.NotFound,
                ErrorResponse("not_found", "Лауреаты не найдены")
            )
        }
    }
}