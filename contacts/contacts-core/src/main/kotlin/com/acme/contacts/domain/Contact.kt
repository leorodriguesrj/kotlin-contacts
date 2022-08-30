package com.acme.contacts.domain

import java.util.*

open class Contact(
    val key : ContactKey = DefaultContactKey(),
    val name: ContactName = NullContactName,
    val emails: EmailList = EmailList(),
    val phones: PhoneNumberList = PhoneNumberList(),
    val addresses: AddressList = AddressList()
) {

    fun addAddress(address : Address): Contact {
        address.confirmIsNotDuplicatedIn(addresses)
        addresses.add(address)
        return this
    }

    fun addPhone(phoneNumber: PhoneNumber): Contact {
        phoneNumber.confirmIsNotDuplicatedIn(phones)
        phones.add(phoneNumber)
        return this
    }

    fun removeAddress(index: Int): Contact {
        addresses.removeAt(index)
        return this
    }

    fun removePhone(index: Int): Contact {
        phones.removeAt(index)
        return this
    }

    fun addEmail(email: Email): EmailKey {
        val newEmail = email.asNewEntity()
        newEmail.confirmIsUniqueIn(emails)
        emails.add(newEmail)
        return newEmail.key
    }

    fun findEmailByKey(emailKey: EmailKey): Optional<Email> {
        return this.emails.findByKey(emailKey)
    }

    fun findAddressByKey(addressKey: AddressKey): Optional<Address> {
        return this.addresses.findByKey(addressKey)
    }

    fun replaceEmailByKeyThen(emailKey: EmailKey, email: Email, action: (Contact) -> Unit): Contact {
        val result = emails.findByKey(emailKey)
        if (result.isEmpty) return this

        val updatedEmail = result.get().edit(email)
        updatedEmail.confirmIsUniqueIn(emails)
        emails.replace(updatedEmail)
        action(this)
        return this
    }

    fun removeEmailByKeyThen(emailKey: EmailKey, action: (Contact) -> Unit): Contact {
        if (emails.removeIf { e -> e.isIdentifiedBy(emailKey) })
            action(this)
        return this
    }

    fun removeAddressByKeyThen(addressKey: AddressKey, action: (Contact) -> Unit): Contact {
        if (addresses.removeIf { a -> a.isIdentifiedBy(addressKey) })
            action(this)
        return this
    }

    fun edit(contact: Contact): Contact {
        return Contact(
            key = key,
            name = contact.name,
            phones = contact.phones,
            emails = contact.emails,
            addresses = contact.addresses
        )
    }

    override fun toString(): String {
        return "Contact(key=$key, name=$name, emails=$emails, phones=$phones, addresses=$addresses)"
    }
}