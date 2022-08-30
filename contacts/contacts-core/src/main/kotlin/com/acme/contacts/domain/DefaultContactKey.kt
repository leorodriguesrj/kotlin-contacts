package com.acme.contacts.domain

import java.util.UUID

class DefaultContactKey(
    private val value : String = UUID.randomUUID().toString()
) : ContactKey {

    override fun toString(): String = value

    override fun equals(other: Any?): Boolean =
        (other is DefaultContactKey) && value == other.value

    override fun hashCode(): Int {
        return value.hashCode()
    }
}
