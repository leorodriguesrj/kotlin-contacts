package com.acme.contacts.domain

class DuplicatedPhoneNumberException(private val phoneNumber: PhoneNumber)
    : ContactsBusinessException("A duplicated phone has been found.") {
    override fun explain(): BusinessErrorExplanation {
        return BusinessErrorExplanation("Phone number $phoneNumber already exists.")
    }

    override fun toString(): String {
        return "DuplicatedPhoneNumberException(phoneNumber=$phoneNumber)"
    }
}
