package com.acme.contacts.rest

import com.acme.contacts.application.ContactOperations
import com.acme.contacts.domain.DefaultContactKey
import com.acme.contacts.rest.resources.ContactListEnvelope
import com.acme.contacts.rest.resources.ContactResource
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/contacts")
class ContactController(private val contactOperations: ContactOperations) {
    @GetMapping
    fun getAllContacts(): ContactListEnvelope {
        return ContactListEnvelope.fromDomainList(contactOperations.findAllContacts())
    }

    @GetMapping("/{key}")
    fun getContactByKey(@PathVariable key: DefaultContactKey): ResponseEntity<ContactResource> {
        val result = contactOperations.findContactByKey(key)
        if (result.isEmpty)
            return ResponseEntity.notFound().build()
        return ResponseEntity.ok(ContactResource.fromDomain(result.get()))
    }

    @PutMapping("/{key}")
    fun updateContactByKey(
        @PathVariable key: DefaultContactKey,
        @RequestBody resource: ContactResource): ResponseEntity<Void> {
        contactOperations.updateContact(key, resource.toDomain())
        return ResponseEntity.noContent().build()
    }

    @PostMapping
    fun createContact(@RequestBody resource: ContactResource): ResponseEntity<Void> {
        val id = contactOperations.createContact(resource.toDomain())
        return ResponseEntity.status(300).location(ContactResource.makeUri(id)).build()
    }

    @DeleteMapping("/{key}")
    fun deleteContactByKey(@PathVariable key: DefaultContactKey): ResponseEntity<Void> {
        contactOperations.deleteContactByKey(key)
        return ResponseEntity.noContent().build()
    }
}