package com.acme.contacts.domain

open class Email(
    val key: EmailKey = DefaultEmailKey(),
    val label: Label,
    val value: String
) {
    fun confirmIsUniqueIn(emails : List<Email>) {
        if (emails.any { e -> e.value == this.value && e.key != this.key })
            throw DuplicatedEmailException(this)
    }

    override fun toString(): String {
        return "Email(label=$label, value='$value')"
    }

    override fun equals(other: Any?): Boolean {
        return (other is Email)
                && this.label == other.label
                && this.value == other.value
    }

    override fun hashCode(): Int {
        var result = label.hashCode()
        result = 31 * result + value.hashCode()
        return result
    }

    fun asNewEntity(): Email = Email(DefaultEmailKey(), label, value)

    fun isSameEntityAs(email: Email): Boolean = email.key == key

    fun edit(email: Email): Email {
        return Email(key = key, label = email.label, value = email.value)
    }

    fun isIdentifiedBy(emailKey: EmailKey): Boolean = this.key == emailKey
}