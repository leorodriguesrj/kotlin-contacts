package com.acme.contacts.domain

class DuplicatedAddressException(private val address: Address)
    : ContactsBusinessException("Duplicated address found.") {
    override fun explain(): BusinessErrorExplanation {
        return BusinessErrorExplanation("Address $address already exists.")
    }

    override fun toString(): String {
        return "DuplicatedAddressException(address=$address)"
    }
}
