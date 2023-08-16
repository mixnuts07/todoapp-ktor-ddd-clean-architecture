package com.todo.plugins

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.jetbrains.exposed.sql.*

fun Application.configureDatabases() {
    val database = Database.connect(
            url = "jdbc:mysql://localhost:3307/todo",
            user = "admin",
            driver = "org.h2.Driver",
//            driver = "com.mysql.cj.jdbc.Driver",
            password = "admin"
        )
    val todoService = TodoService(database)
    routing {
        // Create user
        post("/todos") {
            val todo = call.receive<ExposedTodo>()
            val id = todoService.create(todo)
            call.respond(HttpStatusCode.Created, id)
        }
        // Read user
        get("/todos/{id}") {
            val id = call.parameters["id"]?.toInt() ?: throw IllegalArgumentException("Invalid ID")
            val todo = todoService.read(id)
            if (todo != null) {
                call.respond(HttpStatusCode.OK, todo)
            } else {
                call.respond(HttpStatusCode.NotFound)
            }
        }
        // Update user
        put("/todos/{id}") {
            val id = call.parameters["id"]?.toInt() ?: throw IllegalArgumentException("Invalid ID")
            val todo = call.receive<ExposedTodo>()
            todoService.update(id, todo)
            call.respond(HttpStatusCode.OK)
        }
        // Delete user
        delete("/todos/{id}") {
            val id = call.parameters["id"]?.toInt() ?: throw IllegalArgumentException("Invalid ID")
            todoService.delete(id)
            call.respond(HttpStatusCode.OK)
        }
    }
}
