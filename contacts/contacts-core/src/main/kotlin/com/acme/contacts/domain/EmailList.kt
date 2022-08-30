package com.acme.contacts.domain

import java.util.*
import kotlin.collections.ArrayList

open class EmailList(emails: List<Email> = listOf())
    : ArrayList<Email>(emails) {
    fun replace(email: Email) {
        this.replaceAll { if (it.isSameEntityAs(email)) email else it }
    }

    fun findByKey(emailKey: EmailKey): Optional<Email> {
        return Optional.ofNullable(find { it.isIdentifiedBy(emailKey) })
    }

    companion object {
        fun mutableEmailListOf(vararg emails: Email): EmailList {
            val result = EmailList()
            result.addAll(emails)
            return result
        }
    }
}