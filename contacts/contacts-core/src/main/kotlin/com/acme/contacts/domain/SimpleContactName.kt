package com.acme.contacts.domain

class SimpleContactName(private val value : String) : ContactName {

    override fun toString(): String = value

    override fun equals(other: Any?): Boolean {
        return (other is SimpleContactName)
                && value == other.value
    }

    override fun hashCode(): Int {
        return value.hashCode()
    }
}