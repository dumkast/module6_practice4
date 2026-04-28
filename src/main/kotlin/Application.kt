package org.example

import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import org.example.data.database.DatabaseFactory
import org.example.data.seed.ApiDataSeeder
import org.example.data.seed.UserSeeder
import org.example.plugins.configurePlugins
import org.example.routing.configureRoutes

fun main() {
    DatabaseFactory.init()
    UserSeeder.seed()
    try {
        ApiDataSeeder.seedFromApi()
    } catch (e: Exception) {
        println("FATAL ERROR in seedFromApi: ${e.message}")
        e.printStackTrace()
    }
    embeddedServer(Netty, port = 8080, module = Application::module).start(wait = true)
}

fun Application.module() {
    configurePlugins()
    configureRoutes()
}