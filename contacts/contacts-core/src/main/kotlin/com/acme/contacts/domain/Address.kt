package com.acme.contacts.domain

data class Address(
    val zip: Zip,
    val number: Int,
    val complement: AddressComplement,
    val label: Label,
    val key: AddressKey = DefaultAddressKey()
) {
    fun confirmIsNotDuplicatedIn(addresses: MutableList<Address>) {
        if (addresses.any { a -> a == this })
            throw DuplicatedAddressException(this)
    }

    override fun equals(other: Any?): Boolean {
        return (other is Address)
                && key == other.key
                && label == other.label
                && zip == other.zip
                && number == other.number
                && complement == other.complement
    }

    override fun hashCode(): Int {
        var result = zip.hashCode()
        result = 31 * result + number
        result = 31 * result + complement.hashCode()
        result = 31 * result + label.hashCode()
        return result
    }

    override fun toString(): String {
        return "Address(zip=$zip, number=$number, complement=$complement, label=$label)"
    }

    fun isIdentifiedBy(addressKey: AddressKey) : Boolean = this.key == addressKey
}