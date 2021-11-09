package com.og.privatemessenger.model.entity

data class Message(
    val text: ByteArray,
    val type: Message
) {


    enum class Message {
        KEY,
        TEXT,
        ADDRESS
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as com.og.privatemessenger.model.entity.Message

        if (!text.contentEquals(other.text)) return false

        return true
    }

    override fun hashCode(): Int {
        return text.contentHashCode()
    }
}