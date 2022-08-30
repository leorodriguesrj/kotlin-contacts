package com.acme.contacts.domain

open class Zip(private val number: String) {
    override fun equals(other: Any?): Boolean {
        return (other is Zip) && other.number == this.number
    }

    override fun hashCode(): Int {
        return number.hashCode()
    }

    override fun toString(): String = number
}