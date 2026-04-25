plugins {
    kotlin("jvm") version "2.1.0"
    kotlin("plugin.serialization") version "2.1.0"
    id("io.ktor.plugin") version "3.1.0"
    application
}

group = "org.example"
version = "1.0-SNAPSHOT"

application {
    mainClass.set("org.example.ApplicationKt")
}

repositories {
    mavenCentral()
}

dependencies {
    // Ktor
    implementation("io.ktor:ktor-server-core:3.1.0")
    implementation("io.ktor:ktor-server-netty:3.1.0")
    implementation("io.ktor:ktor-server-content-negotiation:3.1.0")
    implementation("io.ktor:ktor-serialization-kotlinx-json:3.1.0")
    implementation("io.ktor:ktor-server-auth:3.1.0")
    implementation("io.ktor:ktor-server-auth-jwt:3.1.0")
    implementation("io.ktor:ktor-server-call-logging:3.1.0")
    implementation("io.ktor:ktor-server-status-pages:3.1.0")

    // Exposed + PostgreSQL
    implementation("org.jetbrains.exposed:exposed-core:0.55.0")
    implementation("org.jetbrains.exposed:exposed-dao:0.55.0")
    implementation("org.jetbrains.exposed:exposed-jdbc:0.55.0")
    implementation("org.postgresql:postgresql:42.7.4")

    // HikariCP
    implementation("com.zaxxer:HikariCP:6.0.0")

    // JWT
    implementation("com.auth0:java-jwt:4.4.0")

    // BCrypt
    implementation("at.favre.lib:bcrypt:0.10.2")

    // Logback
    implementation("ch.qos.logback:logback-classic:1.5.6")

    implementation("io.ktor:ktor-client-core:3.1.0")
    implementation("io.ktor:ktor-client-cio:3.1.0")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.8.0")
}

kotlin {
    jvmToolchain(21)
}