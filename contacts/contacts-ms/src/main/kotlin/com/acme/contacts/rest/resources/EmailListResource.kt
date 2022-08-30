package com.acme.contacts.rest.resources

import com.acme.contacts.domain.EmailList

class EmailListResource(emails: List<EmailResource>)
    : ArrayList<EmailResource>(emails) {

    fun toDomain(): EmailList = EmailList(this.map { it.toDomain() })

    companion object {
        fun fromDomainList(emails: EmailList): EmailListResource {
            return EmailListResource(emails.map { EmailResource.fromDomain(it) })
        }
    }

}
