package com.acme.contacts.domain

import java.util.*
import kotlin.collections.ArrayList

open class AddressList(elements: List<Address> = listOf())
    : ArrayList<Address>(elements) {
    fun findByKey(addressKey: AddressKey): Optional<Address> {
        return Optional.ofNullable(find { it.isIdentifiedBy(addressKey) })
    }

    companion object {
        fun mutableAddressListOf(vararg addresses: Address): AddressList {
            val result = AddressList()
            result.addAll(addresses)
            return result
        }
    }
}
