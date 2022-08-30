package com.acme.contacts.application

import com.acme.contacts.domain.*
import java.util.*

open class ContactOperationsImpl(private val contactRepository: ContactRepository) : ContactOperations {

    override fun findAllContacts(): ContactList {
        return ContactList(contactRepository.findAll())
    }

    override fun findContactByKey(key: ContactKey): Optional<Contact> {
        return contactRepository.findByKey(key)
    }

    override fun createContact(contact: Contact): ContactKey {
        return contactRepository.save(contact)
    }

    override fun updateContact(key: ContactKey, contact: Contact) {
        ifContactByKey(key) { contactRepository.save(it.edit(contact)) }
    }

    override fun deleteContactByKey(key: ContactKey) {
        contactRepository.deleteByKey(key)
    }

    private fun ifContactByKey(key: ContactKey, action: (Contact) -> Unit) {
        val result = findContactByKey(key)
        if (result.isPresent) action.invoke(result.get())
    }
}