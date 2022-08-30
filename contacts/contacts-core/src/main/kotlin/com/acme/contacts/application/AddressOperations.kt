package com.acme.contacts.application

import com.acme.contacts.domain.*
import java.util.*

class AddressOperations(
    private val contactOperations: ContactOperations,
    private val contactRepository: ContactRepository
) {
    fun findSingleAddressOnContactByKeys(contactKey: ContactKey, addressKey: AddressKey): Optional<Address> {
        return notFoundOrContactByKey(contactKey) { c -> c.findAddressByKey(addressKey) }
    }

    fun findAllAddressesOfContactByKey(contactKey: ContactKey): Optional<AddressList> {
        return notFoundOrContactByKey(contactKey) { Optional.of(it.addresses) }
    }

    fun removeEmailFromContactByKeys(contactKey: ContactKey, addressKey: AddressKey): Optional<AddressKey> {
        return notFoundOrContactByKey(contactKey) { c ->
            c.removeAddressByKeyThen(addressKey) { contactRepository.save(it) }
            return@notFoundOrContactByKey Optional.of(addressKey)
        }
    }

    private fun <T : Any> notFoundOrContactByKey(key: ContactKey, action: (Contact) -> Optional<T>): Optional<T> {
        return notFoundOrElse(contactOperations.findContactByKey(key), action)
    }

    private fun <T, U : Any> notFoundOrElse(result: Optional<T>, action: (T) -> Optional<U>): Optional<U> {
        return if (result.isPresent) action.invoke(result.get()) else Optional.empty<U>()
    }
}