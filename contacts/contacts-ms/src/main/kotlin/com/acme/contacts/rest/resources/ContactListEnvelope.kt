package com.acme.contacts.rest.resources

import com.acme.contacts.domain.Contact

data class ContactListEnvelope(val items : List<ContactResource>) {
    companion object {
        fun fromDomainList(contacts: List<Contact>): ContactListEnvelope {
            return ContactListEnvelope(contacts.map { ContactResource.fromDomain(it) })
        }
    }
}
