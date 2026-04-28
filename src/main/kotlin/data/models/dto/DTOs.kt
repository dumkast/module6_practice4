package org.example.data.models.dto

import kotlinx.serialization.Serializable

@Serializable
data class LoginRequest(
    val username: String,
    val password: String
)

@Serializable
data class LoginResponse(
    val token: String,
    val username: String,
    val expiresIn: Long = 1800
)

@Serializable
data class PrizeResponse(
    val id: Int,
    val year: String,
    val category: String,
    val prizeAmount: Long,
    val dateAwarded: String,
    val laureates: List<LaureateResponse>
)

@Serializable
data class LaureateResponse(
    val id: String,
    val fullName: String,
    val share: String,
    val motivation: String,
)

@Serializable
data class UserResponse(
    val id: Int,
    val username: String,
    val role: String
)

@Serializable
data class ErrorResponse(
    val error: String,
    val message: String
)