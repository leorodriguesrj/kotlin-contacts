package com.acme.contacts.rest.resources

import com.acme.contacts.domain.Label
import com.acme.contacts.domain.PhoneAreaCode
import com.acme.contacts.domain.PhoneNumber

data class PhoneNumberResource(
    val label: String,
    val areaCode: String,
    val number: String
) {
    fun toDomain(): PhoneNumber {
        return PhoneNumber(
            label = Label(label),
            areaCode = PhoneAreaCode.valueOf(areaCode),
            number = this.number
        )
    }

    companion object {
        fun fromDomain(phoneNumber: PhoneNumber): PhoneNumberResource {
            return PhoneNumberResource(
                label = phoneNumber.label.toString(),
                areaCode = phoneNumber.areaCode.toString(),
                number = phoneNumber.number)
        }
    }
}
