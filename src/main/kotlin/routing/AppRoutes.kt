package org.example.routing

import org.example.data.repository.AuthRepositoryImpl
import org.example.data.repository.PrizeRepositoryImpl
import org.example.domain.usecase.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.routing.*
import org.example.presentation.AuthController
import org.example.presentation.PrizeController

fun Application.configureRoutes() {
    val authRepository = AuthRepositoryImpl()
    val prizeRepository = PrizeRepositoryImpl()

    val loginUseCase = LoginUseCase(authRepository)
    val getPrizesUseCase = GetPrizesUseCase(prizeRepository)
    val getPrizeDetailUseCase = GetPrizeDetailUseCase(prizeRepository)
    val getLaureatesUseCase = GetLaureatesUseCase(prizeRepository)

    val authController = AuthController(loginUseCase)
    val prizeController = PrizeController(getPrizesUseCase, getPrizeDetailUseCase, getLaureatesUseCase)

    routing {
        post("/auth/login") {
            authController.login(call)
        }

        authenticate("auth-jwt") {
            get("/prizes") {
                prizeController.getAllPrizes(call)
            }
            get("/prizes/{year}/{category}") {
                prizeController.getPrizeDetail(call)
            }
            get("/prizes/{year}/{category}/laureates") {
                prizeController.getLaureates(call)
            }
        }
    }
}