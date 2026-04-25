package org.example.security

import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import java.util.*

object JwtConfig {
    private const val SECRET = "nobel-prize-api-secret-key-2024-min-32-chars!!"
    private const val ISSUER = "nobel-api"
    private const val VALIDITY_MS = 30 * 60 * 1000L

    val algorithm: Algorithm = Algorithm.HMAC512(SECRET)

    fun createToken(username: String): String {
        return JWT.create()
            .withSubject(username)
            .withIssuer(ISSUER)
            .withExpiresAt(Date(System.currentTimeMillis() + VALIDITY_MS))
            .sign(algorithm)
    }

    val verifier: JWTVerifier = JWT.require(algorithm)
        .withIssuer(ISSUER)
        .build()
}