package org.example.data.database

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction

object DatabaseFactory {
    fun init() {
        val config = HikariConfig().apply {
            jdbcUrl = "jdbc:postgresql://ep-cold-shape-am1w000c.c-5.us-east-1.aws.neon.tech/neondb?sslmode=require"
            driverClassName = "org.postgresql.Driver"
            username = "neondb_owner"
            password = "npg_z8cUftvNdJ4B"
            maximumPoolSize = 10
            minimumIdle = 2
            idleTimeout = 300000
            maxLifetime = 1800000
            connectionTimeout = 30000
        }
        val dataSource = HikariDataSource(config)
        Database.connect(dataSource)

        transaction {
            SchemaUtils.createMissingTablesAndColumns(
                UserTable,
                PrizeTable,
                LaureateTable,
                UserPrizeTable
            )
        }
    }
}