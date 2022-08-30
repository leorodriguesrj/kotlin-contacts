package com.acme.contacts.domain

class BusinessErrorExplanation(private val message : String) {
    override fun toString(): String {
        return "BusinessErrorExplanation(message='$message')"
    }
}
