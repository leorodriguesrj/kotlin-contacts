package com.acme.contacts.domain

import java.util.UUID.randomUUID

class DefaultEmailKey (
    private val value: String = randomUUID().toString()
) : EmailKey {
    override fun toString(): String = value

    override fun equals(other: Any?): Boolean =
        (other is DefaultEmailKey) && value == other.value

    override fun hashCode(): Int {
        return value.hashCode()
    }
}