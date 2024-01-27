package com.todo.domain

data class Todo(val id: Id, val title: Title) {

}

data class Id(val value: String) {
}

data class Title(val value: String) {
    fun isValidLength(): Title {
        if(this.value.length <= 20 ) {
            return Title(this.value)
        }
        throw Error("")
    }
}