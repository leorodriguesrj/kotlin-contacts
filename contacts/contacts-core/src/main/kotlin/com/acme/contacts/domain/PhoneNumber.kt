package com.acme.contacts.domain

data class PhoneNumber(
    val label: Label,
    val number : String,
    val areaCode : PhoneAreaCode
) {

    @Throws(DuplicatedPhoneNumberException::class)
    fun confirmIsNotDuplicatedIn(phones: MutableList<PhoneNumber>) {
        if (phones.any { p -> p.number == this.number && p.areaCode == areaCode })
            throw DuplicatedPhoneNumberException(this)
    }

    override fun toString(): String {
        return "PhoneNumber(label=$label, number='$number', phoneAreaCode=$areaCode)"
    }
}
