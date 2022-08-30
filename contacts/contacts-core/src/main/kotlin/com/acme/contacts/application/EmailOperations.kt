package com.acme.contacts.application

import com.acme.contacts.domain.*
import java.util.*

class EmailOperations(
    private val contactOperations: ContactOperations,
    private val contactRepository: ContactRepository
) {
    fun findAllEmailsOfContactByKey(key: ContactKey): Optional<EmailList> {
        return notFoundOrContactByKey(key) { Optional.of(it.emails) }
    }

    fun findSingleEmailOnContactByKeys(key: ContactKey, emailKey: EmailKey): Optional<Email> {
        return notFoundOrContactByKey(key) { c -> c.findEmailByKey(emailKey) }
    }

    fun addEmailToContactByKey(
        key: ContactKey, email: Email
    ): Optional<EmailKey> {
        return notFoundOrContactByKey(key) {
            val emailKey = it.addEmail(email)
            contactRepository.save(it);
            return@notFoundOrContactByKey Optional.of(emailKey)
        }
    }

    fun replaceEmailOnContactByKeys(
        contactKey: ContactKey, emailKey: EmailKey, email: Email
    ): Optional<EmailKey> {
        return notFoundOrContactByKey(contactKey) { c ->
            c.replaceEmailByKeyThen(emailKey, email) { contactRepository.save(it) }
            return@notFoundOrContactByKey Optional.of(emailKey)
        }
    }

    fun removeEmailFromContactByKeys(key: ContactKey, emailKey: EmailKey): Optional<EmailKey> {
        return notFoundOrContactByKey(key) { c ->
            c.removeEmailByKeyThen(emailKey) { contactRepository.save(it) }
            return@notFoundOrContactByKey Optional.of(emailKey)
        }
    }

    private fun <E, T : Any> ensuringElementAt(index: Int, list: List<E>, action: (E) -> Optional<T>): Optional<T> {
        return if (list.size > index) action.invoke(list[index]) else Optional.empty()
    }

    private fun <T : Any> notFoundOrContactByKey(key: ContactKey, action: (Contact) -> Optional<T>): Optional<T> {
        return notFoundOrElse(contactOperations.findContactByKey(key), action)
    }

    private fun <T, U : Any> notFoundOrElse(result: Optional<T>, action: (T) -> Optional<U>): Optional<U> {
        return if (result.isPresent) action.invoke(result.get()) else Optional.empty<U>()
    }
}