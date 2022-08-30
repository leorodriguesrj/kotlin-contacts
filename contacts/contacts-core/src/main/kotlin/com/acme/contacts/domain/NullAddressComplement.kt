package com.acme.contacts.domain

import com.acme.contacts.domain.AddressComplementType.Other

object NullAddressComplement: AddressComplement(Other, Description("Empty")) {
}