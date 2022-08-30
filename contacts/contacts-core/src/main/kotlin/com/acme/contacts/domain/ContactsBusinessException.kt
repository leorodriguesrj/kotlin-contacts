package com.acme.contacts.domain

import java.lang.Exception

abstract class ContactsBusinessException(message: String) : Exception(message) {
    abstract fun explain(): BusinessErrorExplanation
}
