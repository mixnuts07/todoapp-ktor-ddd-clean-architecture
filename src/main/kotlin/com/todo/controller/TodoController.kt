package com.todo.controller

import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    routing {
        get("/todo") {
            println("todoを全件取得する")
        }

        get("/todo/1") {
            println("TODOを1件取得する")
        }

        post("/todo") {
            println("TODOを1件作成する")
        }

        put("/todo/1") {
            println("TODOを1件更新する")
        }

        delete("/todo/1") {
            println("TODOを1件削除する")
        }
    }
}
