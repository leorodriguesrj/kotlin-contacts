package com.acme.contacts.domain

import com.acme.contacts.domain.AddressComplementType.Apartment
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class AddressComplementTest {
    @Test
    fun `test for equality`() {
        assertTrue(AddressComplement(Apartment, Description("105")) == AddressComplement(Apartment, Description("105")))
    }

}