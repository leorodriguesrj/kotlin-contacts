package com.acme.contacts.application

import com.acme.contacts.domain.Contact
import com.acme.contacts.domain.ContactKey
import java.util.Optional

interface ContactRepository {
    fun save(contact: Contact): ContactKey
    fun findByKey(key: ContactKey): Optional<Contact>
    fun findAll(): List<Contact>
    fun deleteByKey(key: ContactKey)
}
