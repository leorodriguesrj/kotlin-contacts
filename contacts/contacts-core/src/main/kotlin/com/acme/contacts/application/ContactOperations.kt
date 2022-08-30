package com.acme.contacts.application

import com.acme.contacts.domain.Contact
import com.acme.contacts.domain.ContactKey
import com.acme.contacts.domain.ContactList
import java.util.*

interface ContactOperations {
    fun findAllContacts(): ContactList
    fun findContactByKey(key: ContactKey): Optional<Contact>
    fun createContact(contact: Contact): ContactKey
    fun updateContact(key: ContactKey, contact: Contact)
    fun deleteContactByKey(key: ContactKey)
}