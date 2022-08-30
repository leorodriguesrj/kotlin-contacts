package com.acme.contacts.rest.resources

import com.acme.contacts.domain.*
import java.net.URI

data class ContactResource(
    val key: String? = null,
    val name: String? = null,
    val emails: EmailListResource? = null,
    val phones: PhoneListResource? = null,
    val addresses: AddressListResource? = null
) {
    fun toDomain(): Contact {
        return Contact(
            key = key?.let { DefaultContactKey(it) } ?: NullContactKey,
            name = name?.let { SimpleContactName(it) } ?: NullContactName,
            emails = emails?.toDomain() ?: EmailList(),
            phones = phones?.toDomain() ?: PhoneNumberList(),
            addresses = addresses?.toDomain() ?: AddressList()
        )
    }

    companion object {
        fun fromDomain(contact: Contact): ContactResource {
            return ContactResource(
                key = contact.key.toString(),
                name = contact.name.toString(),
                emails = EmailListResource.fromDomainList(contact.emails),
                phones = PhoneListResource.fromDomainList(contact.phones),
                addresses = AddressListResource.fromDomainList(contact.addresses)
            )
        }

        fun makeUri(id: ContactKey): URI = URI("/contacts/$id")
    }
}