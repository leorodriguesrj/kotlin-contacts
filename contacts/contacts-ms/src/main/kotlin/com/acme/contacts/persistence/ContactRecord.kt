package com.acme.contacts.persistence

import com.acme.contacts.domain.*
import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document("contacts")
class ContactRecord(
    @Id var key: ObjectId? = null,
    val name: ContactName,
    val emails: EmailList,
    val addresses: AddressList,
    val phones: PhoneNumberList
) {
    fun toDomain(): Contact = Contact(
        key = key?.let { DefaultContactKey(it.toString()) } ?: NullContactKey,
        name = name,
        emails = emails,
        addresses = addresses,
        phones = phones
    )

    companion object {
        fun fromDomain(contact: Contact): ContactRecord {
            return when {
                contact.key is NullContactKey -> ContactRecord(
                    name = contact.name,
                    emails = contact.emails,
                    addresses = contact.addresses,
                    phones = contact.phones
                )

                else -> ContactRecord(
                    key = ObjectId(contact.key.toString()),
                    name = contact.name,
                    emails = contact.emails,
                    addresses = contact.addresses,
                    phones = contact.phones
                )
            }
        }
    }
}