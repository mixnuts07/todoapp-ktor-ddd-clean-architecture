package com.todo.domain

import io.mockk.mockk
import org.junit.Test
import kotlin.test.assertEquals

class TodoTest {
    private val mockId = mockk<Id>()
    private val len_5 = "0123456789"
    @Test
    fun `タイトルの長さが20文字以内であること`() {
        val len_20 = len_5.repeat(4)
        val expected = Title(len_20)
        val actual = Title(len_20).isValidLength()
        assertEquals(expected, actual)
    }
    @Test
    fun `タイトルの長さが20文字以上の時エラーを返す`() {
        val expected = Error("タイトルの長さは20文字以内です")
        val actual = Title(len_5.repeat(5))
//        assertEquals(expected, actual)
    }
//    @Test
//    fun `タイトルの長さが5文字以上であること`() {
//        val expected = Title()
//        assertEquals(expected, true)
//    }
}