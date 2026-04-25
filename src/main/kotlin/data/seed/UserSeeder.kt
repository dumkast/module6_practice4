package org.example.data.seed

import org.example.data.database.UserTable
import org.example.security.PasswordHasher
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction

object UserSeeder {
    fun seed() {
        transaction {
            if (UserTable.selectAll().empty()) {
                UserTable.insert {
                    it[username] = "admin"
                    it[passwordHash] = PasswordHasher.hash("admin123")
                    it[role] = "admin"
                }
                UserTable.insert {
                    it[username] = "user"
                    it[passwordHash] = PasswordHasher.hash("user123")
                    it[role] = "user"
                }
            }
        }
    }
}