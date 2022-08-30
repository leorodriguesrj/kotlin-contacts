package com.acme.contacts.rest.resources

import com.acme.contacts.domain.AddressComplement
import com.acme.contacts.domain.AddressComplementType
import com.acme.contacts.domain.Description

data class AddressComplementResource(
    val type: String,
    val description: String
) {
    fun toDomain(): AddressComplement {
        return AddressComplement(
            type = AddressComplementType.valueOf(type),
            description = Description(description)
        )
    }

    companion object {
        fun fromDomain(complement: AddressComplement): AddressComplementResource {
            return AddressComplementResource(
                complement.type.toString(),
                complement.description.toString())
        }
    }
}
