package com.acme.contacts.domain

class Label(private val text : String) {
    override fun equals(other: Any?): Boolean {
        return (other is Label) && other.text == text
    }

    override fun hashCode(): Int {
        return super.hashCode()
    }

    override fun toString(): String = text
}
