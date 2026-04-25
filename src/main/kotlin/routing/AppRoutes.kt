package org.example.routing

import org.example.data.repository.AuthRepositoryImpl
import org.example.data.repository.PrizeRepositoryImpl
import org.example.domain.usecase.*
import io.ktor.server.application.*
import io.ktor.server.routing.*
import org.example.presentation.AuthController
import org.example.presentation.prizeRoutes

fun Application.configureRoutes() {
    val authRepository = AuthRepositoryImpl()
    val prizeRepository = PrizeRepositoryImpl()

    val loginUseCase = LoginUseCase(authRepository)
    val getPrizesUseCase = GetPrizesUseCase(prizeRepository)
    val getPrizeDetailUseCase = GetPrizeDetailUseCase(prizeRepository)
    val getLaureatesUseCase = GetLaureatesUseCase(prizeRepository)
    val addFavoritePrizeUseCase = AddFavoritePrizeUseCase(prizeRepository)
    val removeFavoritePrizeUseCase = RemoveFavoritePrizeUseCase(prizeRepository)
    val getFavoritePrizesUseCase = GetFavoritePrizesUseCase(prizeRepository)
    val getUserProfileUseCase = GetUserProfileUseCase(authRepository)

    val authController = AuthController(loginUseCase)

    routing {
        post("/auth/login") {
            authController.login(call)
        }

        prizeRoutes(
            getPrizesUseCase, getPrizeDetailUseCase, getLaureatesUseCase,
            addFavoritePrizeUseCase, removeFavoritePrizeUseCase,
            getFavoritePrizesUseCase, getUserProfileUseCase
        )
    }
}