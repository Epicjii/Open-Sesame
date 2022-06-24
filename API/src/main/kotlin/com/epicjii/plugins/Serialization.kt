package com.epicjii.plugins

import com.epicjii.Database.createEntry
import com.epicjii.Database.getUserNamePassword
import com.epicjii.Database.updateEntry
import com.epicjii.serializable.PasswordEntry
import com.epicjii.serializable.Website
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureSerialization() {
    install(ContentNegotiation) {
        json()
    }

    routing {
        get("/listofpasswords") {
            val data = call.receive<Website>()
            call.respond(getUserNamePassword(data.domain))
        }
        get("/newpassword") {
            val data = call.receive<PasswordEntry>()
            call.respond(createEntry(data))
        }
        get("/updatepassword") {
            val data = call.receive<PasswordEntry>()
            call.respond(updateEntry(data))
        }
    }
}
