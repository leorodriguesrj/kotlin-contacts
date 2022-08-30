package com.acme.contacts.rest.resources

import com.acme.contacts.domain.*
import java.net.URI

data class EmailResource(
    val key: String? = null,
    val label: String,
    val value: String
) {

    fun toDomain(): Email = Email(
        key = key?.let { DefaultEmailKey(it) } ?: DefaultEmailKey(),
        label = Label(label),
        value = value
    )

    companion object {
        fun fromDomain(email: Email): EmailResource {
            return EmailResource(
                key = email.key.toString(),
                label = email.label.toString(),
                value = email.value
            )
        }

        fun makeUri(key: ContactKey, emailKey: EmailKey): URI {
            return URI("/contacts/$key/emails/$emailKey")
        }
    }
}
