package com.acme.contacts.domain

class DuplicatedEmailException(private val email: Email)
    : ContactsBusinessException("A duplicated email was found.") {
    override fun explain(): BusinessErrorExplanation {
        return BusinessErrorExplanation("The email $email already exists.")
    }

    override fun toString(): String {
        return "DuplicatedEmailException(email=$email)"
    }
}
