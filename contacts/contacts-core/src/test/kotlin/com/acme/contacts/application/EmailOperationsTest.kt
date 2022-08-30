package com.acme.contacts.application

import com.acme.contacts.domain.*
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.mockito.kotlin.*
import java.util.*

@DisplayName("com.acme.contacts.application.EmailOperations")
internal class EmailOperationsTest {

    @Nested
    @DisplayName("findSingleEmailOnContactByKeys")
    inner class FindSingleEmailOnContactByKeys {
        @Test
        fun `should successfully retrieve the email`() {
            val repository = mock<ContactRepository>()
            val contactOperations = mock<ContactOperations>()
            val contactKey = DefaultContactKey()
            val emailKey = DefaultEmailKey()

            val theEmail = Email(
                key = emailKey,
                label = Label("new"),
                value = "jack@thecornfield.org"
            )

            val emailList = EmailList.mutableEmailListOf(theEmail)

            val contact = Contact(
                key = contactKey,
                emails = emailList
            )

            val operations = EmailOperations(contactOperations, repository)

            whenever(contactOperations.findContactByKey(contactKey))
                .thenReturn(Optional.of(contact))

            val result = operations.findSingleEmailOnContactByKeys(contactKey, emailKey)

            assertTrue(result.isPresent)
            assertEquals(theEmail, result.get())

            verify(contactOperations, times(1))
                .findContactByKey(contactKey)
        }

        @Test
        fun `should report that the contact does not exist`() {
            val repository = mock<ContactRepository>()
            val contactOperations = mock<ContactOperations>()
            val contactKey = DefaultContactKey()
            val emailKey = DefaultEmailKey()

            val operations = EmailOperations(contactOperations, repository)

            whenever(contactOperations.findContactByKey(contactKey))
                .thenReturn(Optional.empty())

            val result = operations.findSingleEmailOnContactByKeys(contactKey, emailKey)

            assertTrue(result.isEmpty)

            verify(contactOperations, times(1))
                .findContactByKey(contactKey)
        }

        @Test
        fun `should report that the email does not exist`() {
            val repository = mock<ContactRepository>()
            val contactOperations = mock<ContactOperations>()
            val contactKey = DefaultContactKey()
            val emailKey = DefaultEmailKey()

            val theEmail = Email(
                label = Label("new"),
                value = "jack@thecornfield.org"
            )

            val emailList = EmailList.mutableEmailListOf(theEmail)

            val contact = Contact(
                key = contactKey,
                emails = emailList
            )

            val operations = EmailOperations(contactOperations, repository)

            whenever(contactOperations.findContactByKey(contactKey))
                .thenReturn(Optional.of(contact))

            val result = operations.findSingleEmailOnContactByKeys(contactKey, emailKey)

            assertTrue(result.isEmpty)

            verify(contactOperations, times(1))
                .findContactByKey(contactKey)
        }
    }

    @Nested
    @DisplayName("findAllEmailsOfContactByKey")
    inner class FindAllEmailsOfContactByKey {
        @Test
        fun `should successfully retrieve the email collection from the contact`() {
            val repository = mock<ContactRepository>()
            val contactOperations = mock<ContactOperations>()
            val contactKey = DefaultContactKey()

            val someEmail = Email(
                label = Label("new"),
                value = "jack@thecornfield.org"
            )

            val emailList = EmailList.mutableEmailListOf(someEmail)

            val contact = Contact(key = contactKey, emails = emailList)

            val operations = EmailOperations(contactOperations, repository)

            whenever(contactOperations.findContactByKey(contactKey))
                .thenReturn(Optional.of(contact))

            val result = operations.findAllEmailsOfContactByKey(contactKey)

            assertTrue(result.isPresent)
            assertEquals(EmailList(listOf(someEmail)), result.get())

            verify(contactOperations, times(1))
                .findContactByKey(contactKey)
        }

        @Test
        fun `should report that the contact does not exist`() {
            val repository = mock<ContactRepository>()
            val contactOperations = mock<ContactOperations>()
            val contactKey = DefaultContactKey()

            val operations = EmailOperations(contactOperations, repository)

            whenever(contactOperations.findContactByKey(contactKey))
                .thenReturn(Optional.empty())

            val result = operations.findAllEmailsOfContactByKey(contactKey)

            assertTrue(result.isEmpty)

            verify(contactOperations, times(1))
                .findContactByKey(contactKey)
        }
    }

    @Nested
    @DisplayName("removeEmailFromContactByKeys")
    inner class RemoveEmailFromContactByKeys {
        @Test
        fun `should successfully remove an email`() {
            val repository = mock<ContactRepository>()
            val contactOperations = mock<ContactOperations>()
            val contactKey = DefaultContactKey()
            val emailKey = DefaultEmailKey()

            val emailList = EmailList.mutableEmailListOf(
                Email(
                    key = emailKey,
                    label = Label("new"),
                    value = "jack@thecornfield.org"
                )
            )

            val contact = Contact(
                key = contactKey,
                emails = emailList
            )

            val operations = EmailOperations(contactOperations, repository)

            whenever(contactOperations.findContactByKey(contactKey))
                .thenReturn(Optional.of(contact))

            val result = operations.removeEmailFromContactByKeys(contactKey, emailKey)

            assertTrue(result.isPresent)
            assertEquals(emailKey, result.get())

            verify(contactOperations, times(1))
                .findContactByKey(contactKey)

            verify(repository, times(1))
                .save(contact)

            assertEquals(EmailList(), emailList)
        }

        @Test
        fun `should report abort if the contact does not exist`() {
            val repository = mock<ContactRepository>()
            val contactOperations = mock<ContactOperations>()
            val contactKey = DefaultContactKey()
            val emailKey = DefaultEmailKey()

            val operations = EmailOperations(contactOperations, repository)

            whenever(contactOperations.findContactByKey(contactKey))
                .thenReturn(Optional.empty())

            val result = operations.removeEmailFromContactByKeys(contactKey, emailKey)

            assertTrue(result.isEmpty)

            verify(contactOperations, times(1)).findContactByKey(contactKey)

            verify(repository, times(0)).save(any())
        }

        @Test
        fun `should do nothing if there is no email with the given key`() {
            val contactOperations = mock<ContactOperations>()
            val repository = mock<ContactRepository>()
            val contactKey = DefaultContactKey()
            val emailKey = DefaultEmailKey()

            val email = Email(
                label = Label("old"),
                value = "jack@thecornfield.org"
            )

            val emailList = EmailList.mutableEmailListOf(email)

            val contact = Contact(
                key = contactKey,
                emails = emailList
            )

            val operations = EmailOperations(contactOperations, repository)

            whenever(contactOperations.findContactByKey(contactKey))
                .thenReturn(Optional.of(contact))

            val result = operations.removeEmailFromContactByKeys(contactKey, emailKey)

            assertTrue(result.isPresent)
            assertEquals(emailKey, result.get())

            verify(contactOperations, times(1)).findContactByKey(contactKey)

            verify(repository, times(0)).save(any())

            assertEquals(EmailList(listOf(email)), emailList)
        }
    }

    @Nested
    @DisplayName("replaceEmailOnContactByKeys")
    inner class ReplaceEmailOnContactByKeys {
        @Test
        fun `should successfully update the contact`() {
            val repository = mock<ContactRepository>()
            val contactOperations = mock<ContactOperations>()
            val contactKey = DefaultContactKey()
            val emailKey = DefaultEmailKey()

            val emailList = EmailList.mutableEmailListOf(
                Email(
                    key = emailKey,
                    label = Label("new"),
                    value = "jack@thecornfield.org"
                )
            )

            val newEmail = Email(
                key = emailKey,
                label = Label("test"),
                value = "jack@pumpkinghead.com"
            )

            val contact = Contact(
                key = contactKey,
                emails = emailList
            )

            val operations = EmailOperations(contactOperations, repository)

            whenever(contactOperations.findContactByKey(contactKey))
                .thenReturn(Optional.of(contact))

            val result = operations.replaceEmailOnContactByKeys(contactKey, emailKey, newEmail)

            assertTrue(result.isPresent)
            assertEquals(emailKey, result.get())

            verify(contactOperations, times(1))
                .findContactByKey(contactKey)

            verify(repository, times(1))
                .save(contact)

            assertEquals(EmailList.mutableEmailListOf(newEmail), emailList)
        }

        @Test
        fun `should report that the contact does not exist`() {
            val repository = mock<ContactRepository>()
            val contactOperations = mock<ContactOperations>()
            val contactKey = DefaultContactKey()
            val emailKey = DefaultEmailKey()
            val email = Email(
                key = emailKey,
                label = Label("test"),
                value = "jack@pumpkinghead.com"
            )

            val operations = EmailOperations(contactOperations, repository)

            whenever(contactOperations.findContactByKey(contactKey)).thenReturn(Optional.empty())

            assertTrue(operations.replaceEmailOnContactByKeys(contactKey, emailKey, email).isEmpty)

            verify(contactOperations, times(1)).findContactByKey(contactKey)
            verify(repository, times(0)).save(any())
        }

        @Test
        fun `should do nothing if the email does not exist`() {
            val repository = mock<ContactRepository>()
            val contactOperations = mock<ContactOperations>()
            val contactKey = DefaultContactKey()
            val emailKey = DefaultEmailKey()

            val someEmail = Email(
                label = Label("new"),
                value = "jack@thecornfield.org"
            )

            val incomingEmail = Email(
                key = NullEmailKey,
                label = Label("test"),
                value = "jack@pumpkinghead.com"
            )

            val emailList = EmailList.mutableEmailListOf(someEmail)

            val contact = Contact(
                key = contactKey,
                emails = emailList
            )

            val operations = EmailOperations(contactOperations, repository)

            whenever(contactOperations.findContactByKey(contactKey))
                .thenReturn(Optional.of(contact))

            val result = operations
                .replaceEmailOnContactByKeys(contactKey, emailKey, incomingEmail)

            assertTrue(result.isPresent)
            assertEquals(emailKey, result.get())

            verify(contactOperations, times(1))
                .findContactByKey(contactKey)
            verify(repository, times(0)).save(any())

            assertEquals(EmailList.mutableEmailListOf(someEmail), emailList)
        }
    }

    @Nested
    @DisplayName("addEmailToContactByKey")
    inner class AddEmailToContactByKey {
        @Test
        fun `should report that the contact was not found`() {
            val repository = mock<ContactRepository>()
            val contactOperations = mock<ContactOperations>()
            val contactKey = DefaultContactKey()
            val email = Email(
                label = Label("test"),
                value = "jack@pumpkinghead.com"
            )

            val operations = EmailOperations(contactOperations, repository)

            whenever(contactOperations.findContactByKey(contactKey)).thenReturn(Optional.empty())

            assertTrue(operations.addEmailToContactByKey(contactKey, email).isEmpty)

            verify(contactOperations, times(1)).findContactByKey(contactKey)
            verify(repository, times(0)).save(any())
        }

        @Test
        fun `should add an email to the list and save the contact`() {
            val repository = mock<ContactRepository>()
            val contactOperations = mock<ContactOperations>()
            val contactKey = DefaultContactKey()
            val emailKey = DefaultEmailKey()
            val emailList = EmailList()

            val email = Email(
                key = emailKey,
                label = Label("test"),
                value = "jack@pumpkinghead.com"
            )

            val contact = Contact(
                key = contactKey,
                emails = emailList
            )

            val operations = EmailOperations(contactOperations, repository)

            whenever(contactOperations.findContactByKey(contactKey)).thenReturn(Optional.of(contact))

            val result = operations.addEmailToContactByKey(contactKey, email)

            assertTrue(result.isPresent)

            verify(contactOperations, times(1)).findContactByKey(contactKey)
            verify(repository, times(1)).save(contact)

            assertEquals(EmailList.mutableEmailListOf(email), emailList)
        }
    }
}