package com.acme.contacts.domain

class Description(private val content : String) {
    override fun equals(other: Any?): Boolean {
        return (other is Description) && other.content == this.content
    }

    override fun toString(): String = content

    override fun hashCode(): Int = content.hashCode()
}
