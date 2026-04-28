package org.example.data.repository

import org.example.data.database.UserTable
import org.example.domain.model.AuthUser
import org.example.domain.model.User
import org.example.domain.repository.AuthRepository
import org.example.security.JwtConfig
import org.example.security.PasswordHasher
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

class AuthRepositoryImpl : AuthRepository {

    override suspend fun authenticate(username: String, password: String): AuthUser? = newSuspendedTransaction {
        val row = UserTable.selectAll().where { UserTable.username eq username }.firstOrNull()
            ?: return@newSuspendedTransaction null

        val hash = row[UserTable.passwordHash]
        if (PasswordHasher.verify(password, hash)) {
            val token = JwtConfig.createToken(username)
            AuthUser(username = username, token = token)
        } else {
            null
        }
    }

    override suspend fun getUserByUsername(username: String): User? = newSuspendedTransaction {
        UserTable.selectAll().where { UserTable.username eq username }
            .firstOrNull()
            ?.let { row ->
                User(
                    id = row[UserTable.id],
                    username = row[UserTable.username],
                    role = row[UserTable.role]
                )
            }
    }
}