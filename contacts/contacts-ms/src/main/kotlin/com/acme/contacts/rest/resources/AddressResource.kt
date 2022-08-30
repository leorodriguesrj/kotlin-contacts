package com.acme.contacts.rest.resources

import com.acme.contacts.domain.Address
import com.acme.contacts.domain.Label
import com.acme.contacts.domain.NullAddressComplement
import com.acme.contacts.domain.Zip

data class AddressResource(
    val zip: String,
    val number: Int,
    val complement: AddressComplementResource? = null
) {
    fun toDomain(): Address {
        return Address(
            zip = Zip(zip),
            number = number,
            complement = complement?.toDomain() ?: NullAddressComplement,
            label = Label("home"),
        )
    }

    companion object {
        fun fromDomain(address: Address): AddressResource {
            return AddressResource(
                zip = address.zip.toString(),
                number = address.number,
                complement = AddressComplementResource.fromDomain(address.complement))
        }
    }
}
