package com.acme.contacts.domain

open class AddressComplement(
    val type : AddressComplementType,
    val description : Description
) {

    override fun equals(other: Any?): Boolean {
        return (other is AddressComplement)
                && other.type == this.type
                && other.description == this.description
    }

    override fun toString(): String {
        return "AddressComplement(type=$type, description=$description)"
    }

    override fun hashCode(): Int {
        var result = type.hashCode()
        result = 31 * result + description.hashCode()
        return result
    }
}
