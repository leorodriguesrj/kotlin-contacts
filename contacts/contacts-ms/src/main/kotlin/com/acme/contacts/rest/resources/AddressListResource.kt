package com.acme.contacts.rest.resources

import com.acme.contacts.domain.AddressList

class AddressListResource(addressResources: List<AddressResource>)
    : ArrayList<AddressResource>(addressResources) {

    fun toDomain(): AddressList = AddressList(this.map { it.toDomain() })

    companion object {
        fun fromDomainList(addresses: AddressList): AddressListResource {
            return AddressListResource(addresses.map { AddressResource.fromDomain(it) })
        }
    }
}
