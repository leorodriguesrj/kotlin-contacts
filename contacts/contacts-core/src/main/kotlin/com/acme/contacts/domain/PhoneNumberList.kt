package com.acme.contacts.domain

open class PhoneNumberList(elements: List<PhoneNumber> = listOf())
    : ArrayList<PhoneNumber>(elements) {

    companion object {
        fun mutablePhoneNumberListOf(vararg phoneNumbers: PhoneNumber): PhoneNumberList {
            val result = PhoneNumberList()
            result.addAll(phoneNumbers)
            return result
        }
    }
}