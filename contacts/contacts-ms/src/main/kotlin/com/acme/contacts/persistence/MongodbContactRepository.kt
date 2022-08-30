package com.acme.contacts.persistence

import com.acme.contacts.application.ContactRepository
import com.acme.contacts.domain.Contact
import com.acme.contacts.domain.ContactKey
import com.acme.contacts.domain.DefaultContactKey
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.stereotype.Repository
import java.util.Optional

@Repository
class MongodbContactRepository(private val mongoTemplate: MongoTemplate) : ContactRepository {

    override fun save(contact: Contact): ContactKey {
        return DefaultContactKey(mongoTemplate.save<ContactRecord>(ContactRecord.fromDomain(contact)).key.toString())
    }

    override fun findByKey(key: ContactKey): Optional<Contact> {
        return mongoTemplate.findById(key.toString(), ContactRecord::class.java)?.let {
            Optional.of(it.toDomain())
        } ?: Optional.empty<Contact>()
    }

    override fun findAll(): List<Contact> {
        return mongoTemplate.findAll(ContactRecord::class.java).map { it.toDomain() }
    }

    override fun deleteByKey(key: ContactKey) {
        mongoTemplate.remove(Query.query(Criteria.where("key").`is`(key.toString())), ContactRecord::class.java)
    }
}