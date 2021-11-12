package com.og.privatemessenger.model.entity

import java.util.*

data class Message(
    val text: ByteArray,
    val type: Int,
    val date: Date
) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Message

        if (!text.contentEquals(other.text)) return false
        if (type != other.type) return false

        return true
    }

    override fun hashCode(): Int {
        var result = text.contentHashCode()
        result = 31 * result + type
        return result
    }

    companion object {
        const val MESSAGE_KEY = 0
        const val MESSAGE_SEND = 1
        const val MESSAGE_READ = 2
        const val MESSAGE_TOAST = 3
        const val MESSAGE_WRITTEN = 4
    }

}