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
    val getAllPrizesUseCase = GetAllPrizesUseCase(prizeRepository)
    val getPrizeByYearAndCategoryUseCase = GetPrizeByYearAndCategoryUseCase(prizeRepository)
    val getLaureatesByPrizeUseCase = GetLaureatesByPrizeUseCase(prizeRepository)
    val addPrizeToFavoritesUseCase = AddPrizeToFavoritesUseCase(prizeRepository)
    val removePrizeFromFavoritesUseCase = RemovePrizeFromFavoritesUseCase(prizeRepository)
    val getUserFavoritesUseCase = GetUserFavoritesUseCase(prizeRepository)
    val getCurrentUserUseCase = GetCurrentUserUseCase(authRepository)

    val authController = AuthController(loginUseCase)

    routing {
        post("/auth/login") {
            authController.login(call)
        }

        prizeRoutes(
            getAllPrizesUseCase,
            getPrizeByYearAndCategoryUseCase,
            getLaureatesByPrizeUseCase,
            addPrizeToFavoritesUseCase,
            removePrizeFromFavoritesUseCase,
            getUserFavoritesUseCase,
            getCurrentUserUseCase
        )
    }
}