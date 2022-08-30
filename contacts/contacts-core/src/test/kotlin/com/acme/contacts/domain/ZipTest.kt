package com.acme.contacts.domain

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class ZipTest {

    @Test
    fun `test for equality`() {
        assertTrue(Zip("1234") == Zip("1234"))
    }
}