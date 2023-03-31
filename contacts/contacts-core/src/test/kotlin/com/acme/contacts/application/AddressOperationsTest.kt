package com.acme.contacts.application

import com.acme.contacts.domain.*
import com.acme.contacts.domain.AddressComplementType.Apartment
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import java.util.*

@DisplayName("com.acme.contacts.application.AddressOperations")
internal class AddressOperationsTest {

    @Nested
    @DisplayName("findSingleAddressOnContactByKeys")
    inner class FindSingleAddressOnContactByKeys {
        @Test
        fun `should successfully retrieve the address`() {
            val repository = mock<ContactRepository>()
            val contactOperations = mock<ContactOperations>()
            val contactKey = DefaultContactKey()
            val addressKey = DefaultAddressKey()

            val theAddress = Address(
                key = addressKey,
                zip = Zip("24230200"),
                number = 91,
                complement = AddressComplement(
                    type = Apartment, description = Description("105")
                ),
                label = Label("home"),
            )

            val addressList = AddressList.mutableAddressListOf(theAddress)

            val contact = Contact(
                key = contactKey,
                addresses = addressList
            )

            val operations = AddressOperations(contactOperations, repository)

            whenever(contactOperations.findContactByKey(contactKey))
                .thenReturn(Optional.of(contact))

            val result = operations.findSingleAddressOnContactByKeys(contactKey, addressKey)

            assertTrue(result.isPresent)
            assertEquals(theAddress, result.get())

            verify(contactOperations, times(1))
                .findContactByKey(contactKey)
        }

        @Test
        fun `should report that the contact does not exist`() {
            val repository = mock<ContactRepository>()
            val contactOperations = mock<ContactOperations>()
            val contactKey = DefaultContactKey()
            val addressKey = DefaultAddressKey()

            val operations = AddressOperations(contactOperations, repository)

            whenever(contactOperations.findContactByKey(contactKey))
                .thenReturn(Optional.empty())

            val result = operations.findSingleAddressOnContactByKeys(contactKey, addressKey)

            assertTrue(result.isEmpty)

            verify(contactOperations, times(1))
                .findContactByKey(contactKey)
        }

        @Test
        fun `should report that the address does not exist`() {
            val repository = mock<ContactRepository>()
            val contactOperations = mock<ContactOperations>()
            val contactKey = DefaultContactKey()
            val addressKey = DefaultAddressKey()

            val theAddress = Address(
                zip = Zip("24230200"),
                number = 91,
                complement = AddressComplement(
                    type = Apartment, description = Description("105")
                ),
                label = Label("home"),
            )

            val addressList = AddressList.mutableAddressListOf(theAddress)

            val contact = Contact(
                key = contactKey,
                addresses = addressList
            )

            val operations = AddressOperations(contactOperations, repository)

            whenever(contactOperations.findContactByKey(contactKey))
                .thenReturn(Optional.of(contact))

            val result = operations.findSingleAddressOnContactByKeys(contactKey, addressKey)

            assertTrue(result.isEmpty)

            verify(contactOperations, times(1))
                .findContactByKey(contactKey)
        }
    }

    @Nested
    @DisplayName("findAllAddressesOfContactByKey")
    inner class FindAllAddressesOfContactByKey {
        @Test
        fun `should successfully retrieve the address collection from the contact`() {
            val repository = mock<ContactRepository>()
            val contactOperations = mock<ContactOperations>()
            val contactKey = DefaultContactKey()
            val addressKey = DefaultAddressKey()

            val theAddress = Address(
                key = addressKey,
                zip = Zip("24230200"),
                number = 91,
                complement = AddressComplement(
                    type = Apartment, description = Description("105")
                ),
                label = Label("home"),
            )

            val addressList = AddressList.mutableAddressListOf(theAddress)

            val contact = Contact(
                key = contactKey,
                addresses = addressList
            )

            val operations = AddressOperations(contactOperations, repository)

            whenever(contactOperations.findContactByKey(contactKey))
                .thenReturn(Optional.of(contact))

            val result = operations.findAllAddressesOfContactByKey(contactKey)

            assertTrue(result.isPresent)
            assertEquals(AddressList.mutableAddressListOf(theAddress), result.get())

            verify(contactOperations, times(1))
                .findContactByKey(contactKey)
        }

        @Test
        fun `should report that the contact does not exist`() {
            val repository = mock<ContactRepository>()
            val contactOperations = mock<ContactOperations>()
            val contactKey = DefaultContactKey()

            val operations = AddressOperations(contactOperations, repository)

            whenever(contactOperations.findContactByKey(contactKey))
                .thenReturn(Optional.empty())

            val result = operations.findAllAddressesOfContactByKey(contactKey)

            assertTrue(result.isEmpty)

            verify(contactOperations, times(1))
                .findContactByKey(contactKey)
        }
    }

    @Nested
    @DisplayName("removeAddressFromContactByKeys")
    inner class RemoveAddressFromContactByKeys {
        @Test
        fun `should successfully remove an address`() {
            val repository = mock<ContactRepository>()
            val contactOperations = mock<ContactOperations>()
            val contactKey = DefaultContactKey()
            val addressKey = DefaultAddressKey()

            val theAddress = Address(
                key = addressKey,
                zip = Zip("24230200"),
                number = 91,
                complement = AddressComplement(
                    type = Apartment, description = Description("105")
                ),
                label = Label("home"),
            )

            val addressList = AddressList.mutableAddressListOf(theAddress)

            val contact = Contact(
                key = contactKey,
                addresses = addressList
            )

            val operations = AddressOperations(contactOperations, repository)

            whenever(contactOperations.findContactByKey(contactKey))
                .thenReturn(Optional.of(contact))

            val result = operations.removeEmailFromContactByKeys(contactKey, addressKey)

            assertTrue(result.isPresent)
            assertEquals(addressKey, result.get())

            verify(contactOperations, times(1))
                .findContactByKey(contactKey)

            assertEquals(AddressList(), addressList)
        }

        @Test
        fun `should report if the contact does not exist`() {
            val repository = mock<ContactRepository>()
            val contactOperations = mock<ContactOperations>()
            val contactKey = DefaultContactKey()
            val addressKey = DefaultAddressKey()

            val operations = AddressOperations(contactOperations, repository)

            whenever(contactOperations.findContactByKey(contactKey))
                .thenReturn(Optional.empty())

            val result = operations.removeEmailFromContactByKeys(contactKey, addressKey)

            assertTrue(result.isEmpty)

            verify(contactOperations, times(1))
                .findContactByKey(contactKey)
        }

        @Test
        fun `should do nothing if there is no address with the given key`() {
            val repository = mock<ContactRepository>()
            val contactOperations = mock<ContactOperations>()
            val contactKey = DefaultContactKey()
            val addressKey = DefaultAddressKey()

            val theAddress = Address(
                zip = Zip("24230200"),
                number = 91,
                complement = AddressComplement(
                    type = Apartment, description = Description("105")
                ),
                label = Label("home"),
            )

            val addressList = AddressList.mutableAddressListOf(theAddress)

            val contact = Contact(
                key = contactKey,
                addresses = addressList
            )

            val operations = AddressOperations(contactOperations, repository)

            whenever(contactOperations.findContactByKey(contactKey))
                .thenReturn(Optional.of(contact))

            val result = operations.removeEmailFromContactByKeys(contactKey, addressKey)

            assertTrue(result.isPresent)
            assertEquals(addressKey, result.get())

            verify(contactOperations, times(1))
                .findContactByKey(contactKey)

            assertEquals(AddressList.mutableAddressListOf(theAddress), addressList)
        }
    }

    @Nested
    @DisplayName("replaceAddressOnContactByKeys")
    inner class ReplaceAddressOnContactByKeys {
        @Test
        fun `should successfully update the contact`() {
            TODO("Hang on tight!")
        }

        @Test
        fun `should report that the contact does not exist`() {
            TODO("Hang on tight!")
        }

        @Test
        fun `should do nothing if the address does not exist`() {
            TODO("Hang on tight!")
        }
    }

    @Nested
    @DisplayName("addAddressToContactByKey")
    inner class AddAddressToContactByKey {
        @Test
        fun `should report that the contact was not found`() {
            TODO("Hang on tight!")
        }

        @Test
        fun `should add an address to the list and save the contact`() {
            TODO("Hang on tight!")
        }
    }
}