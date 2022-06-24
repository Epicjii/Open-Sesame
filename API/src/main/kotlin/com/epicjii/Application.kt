package com.epicjii

import com.epicjii.Database.createDatabase
import com.epicjii.plugins.configureSerialization
import io.ktor.server.engine.*
import io.ktor.server.netty.*

fun main() {
    createDatabase()
    embeddedServer(Netty, port = 6900, host = "0.0.0.0") {
        configureSerialization()
    }.start(wait = true)
}

