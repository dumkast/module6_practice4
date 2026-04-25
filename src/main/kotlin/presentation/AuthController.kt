package org.example.presentation

import org.example.domain.usecase.LoginUseCase
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import org.example.data.models.dto.ErrorResponse
import org.example.data.models.dto.LoginRequest
import org.example.data.models.dto.LoginResponse

class AuthController(private val loginUseCase: LoginUseCase) {

    suspend fun login(call: ApplicationCall) {
        try {
            val request = call.receive<LoginRequest>()
            val authUser = loginUseCase(request.username, request.password)

            if (authUser != null) {
                call.respond(
                    HttpStatusCode.OK,
                    LoginResponse(token = authUser.token, username = authUser.username)
                )
            } else {
                call.respond(
                    HttpStatusCode.Unauthorized,
                    ErrorResponse("invalid_credentials", "Неверное имя пользователя или пароль")
                )
            }
        } catch (e: IllegalArgumentException) {
            call.respond(
                HttpStatusCode.BadRequest,
                ErrorResponse("validation_error", e.message ?: "Ошибка валидации")
            )
        } catch (e: Exception) {
            call.respond(
                HttpStatusCode.InternalServerError,
                ErrorResponse("server_error", "Внутренняя ошибка сервера")
            )
        }
    }
}