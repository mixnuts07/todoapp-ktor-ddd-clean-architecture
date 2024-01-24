package com.todo.usecase

import com.todo.port.TodoPort
import io.mockk.every
import io.mockk.verify
import org.junit.Test

class TodoUseCaseTest {
    private lateinit var todoPort: TodoPort
    @Test
    fun DBに保存されているTODOを全件取得する() {

        verify { todoPort.getTodos() }
    }
}