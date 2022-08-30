package com.acme.contacts.rest.resources

import com.acme.contacts.domain.PhoneNumberList

class PhoneListResource(phoneNumberResources: List<PhoneNumberResource>)
    : ArrayList<PhoneNumberResource>(phoneNumberResources) {

    fun toDomain(): PhoneNumberList = PhoneNumberList(this.map { it.toDomain() })

    companion object {
        fun fromDomainList(phones: PhoneNumberList): PhoneListResource {
            return PhoneListResource(phones.map { PhoneNumberResource.fromDomain(it) })
        }
    }
}
