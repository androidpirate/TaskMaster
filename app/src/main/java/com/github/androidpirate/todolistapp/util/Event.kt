package com.github.androidpirate.todolistapp.util

open class Event<out T>(private val content: T) {
    var hasBeenHandled = false
    private set

    fun getContentIfNoteHandledOrReturnNull(): T? {
        return if (hasBeenHandled) {
            null
        } else {
            hasBeenHandled = true
            content
        }
    }

    fun peekContext(): T = content
}