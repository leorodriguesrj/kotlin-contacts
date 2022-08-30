package com.acme.contacts.domain

import java.util.UUID.randomUUID

class DefaultAddressKey(
    private val value: String = randomUUID().toString()
) : AddressKey {
    override fun toString(): String = value

    override fun equals(other: Any?): Boolean = (other is DefaultAddressKey)
            && value == other.value

    override fun hashCode(): Int = value.hashCode()
}
