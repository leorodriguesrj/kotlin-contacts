package com.acme.contacts.domain

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotEquals
import org.junit.jupiter.api.Test

internal class EmailListTest {

    @Test
    fun `two empty collections should be equal`() {
        assertEquals(EmailList(), EmailList())
    }

    @Test
    fun `two collections of equal emails should be equal`() {
        val first = EmailList.mutableEmailListOf(Email(
            label = Label("test"), value = "fake-email"
        ))
        val second = EmailList.mutableEmailListOf(Email(
            label = Label("test"), value ="fake-email"
        ))
        assertEquals(first, second)
    }

    @Test
    fun `two collections of different emails should not be equal`() {
        val first = EmailList.mutableEmailListOf(Email(
            label = Label("one"), value = "fake-email"
        ))
        val second = EmailList.mutableEmailListOf(Email(
            label = Label("two"), value = "fake-email"
        ))
        assertNotEquals(first, second)
    }

}