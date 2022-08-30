package com.acme.contacts.rest

import com.acme.contacts.application.EmailOperations
import com.acme.contacts.domain.DefaultContactKey
import com.acme.contacts.domain.DefaultEmailKey
import com.acme.contacts.rest.resources.EmailListEnvelope
import com.acme.contacts.rest.resources.EmailListResource
import com.acme.contacts.rest.resources.EmailResource
import org.springframework.http.HttpStatus.MOVED_PERMANENTLY
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/contacts/{contactKey}/emails")
class EmailController(
    private val emailOperations: EmailOperations
) {

    @GetMapping
    fun getAllEmails(@PathVariable contactKey: DefaultContactKey)
        : ResponseEntity<EmailListEnvelope> {

        val result = emailOperations.findAllEmailsOfContactByKey(contactKey)
        if (result.isEmpty) return ResponseEntity.notFound().build()

        return ResponseEntity.ok(EmailListEnvelope(EmailListResource.fromDomainList(result.get())))
    }

    @GetMapping("/{emailKey}")
    fun getEmailByKey(
        @PathVariable contactKey: DefaultContactKey,
        @PathVariable emailKey: DefaultEmailKey
    ): ResponseEntity<EmailResource> {

        val result = emailOperations.findSingleEmailOnContactByKeys(contactKey, emailKey)
        if (result.isEmpty) return ResponseEntity.notFound().build()

        return ResponseEntity.ok(EmailResource.fromDomain(result.get()))
    }

    @PostMapping
    fun createNewEmail(
        @PathVariable contactKey: DefaultContactKey,
        @RequestBody resource: EmailResource
    ): ResponseEntity<Void> {

        val result = emailOperations.addEmailToContactByKey(contactKey, resource.toDomain())
        if (result.isEmpty) return ResponseEntity.notFound().build()

        return ResponseEntity.status(MOVED_PERMANENTLY)
            .location(EmailResource.makeUri(contactKey, result.get())).build()
    }

    @PutMapping("/{emailKey}")
    fun updateEmailByKey(
        @PathVariable contactKey: DefaultContactKey,
        @PathVariable emailKey: DefaultEmailKey,
        @RequestBody resource: EmailResource
    ): ResponseEntity<Void> {

        val result = emailOperations.replaceEmailOnContactByKeys(contactKey, emailKey, resource.toDomain())
        if (result.isEmpty) return ResponseEntity.notFound().build()

        return ResponseEntity.noContent().build()
    }

    @DeleteMapping("/{emailKey}")
    fun deleteEmailByKey(
        @PathVariable contactKey: DefaultContactKey,
        @PathVariable emailKey: DefaultEmailKey
    ): ResponseEntity<Void> {

        emailOperations.removeEmailFromContactByKeys(contactKey, emailKey)

        return ResponseEntity.noContent().build()
    }
}