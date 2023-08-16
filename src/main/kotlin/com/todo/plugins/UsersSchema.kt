package com.todo.plugins

import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import kotlinx.serialization.Serializable
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.*

@Serializable
data class ExposedTodo(val title: String, val description: String)
class TodoService(private val database: Database) {
    object Todo : Table() {
        val id = integer("id").autoIncrement()
        val title = varchar("title", length = 256)
        val description = varchar("description", length = 256)

        override val primaryKey = PrimaryKey(id)
    }

    init {
        transaction(database) {
            SchemaUtils.create(Todo)
        }
    }

    suspend fun <T> dbQuery(block: suspend () -> T): T =
        newSuspendedTransaction(Dispatchers.IO) { block() }

    suspend fun create(user: ExposedTodo): Int = dbQuery {
        Todo.insert {
            it[title] = user.title
            it[description] = user.description
        }[Todo.id]
    }

    suspend fun read(id: Int): ExposedTodo? {
        return dbQuery {
            Todo.select { Todo.id eq id }
                .map { ExposedTodo(it[Todo.title], it[Todo.description]) }
                .singleOrNull()
        }
    }

    suspend fun update(id: Int, user: ExposedTodo) {
        dbQuery {
            Todo.update({ Todo.id eq id }) {
                it[title] = user.title
                it[description] = user.description
            }
        }
    }

    suspend fun delete(id: Int) {
        dbQuery {
            Todo.deleteWhere { Todo.id.eq(id) }
        }
    }
}
