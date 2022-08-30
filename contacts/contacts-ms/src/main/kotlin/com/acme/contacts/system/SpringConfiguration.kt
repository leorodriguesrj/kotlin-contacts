package com.acme.contacts.system

import com.acme.contacts.application.ContactOperations
import com.acme.contacts.application.ContactRepository
import com.acme.contacts.application.ContactOperationsImpl
import com.acme.contacts.application.EmailOperations
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration

@Configuration
@EnableAutoConfiguration
@ComponentScan("com.acme.contacts")
class SpringConfiguration {
    @Bean
    fun makeContactOperations(repository: ContactRepository): ContactOperations {
        return ContactOperationsImpl(repository)
    }

    @Bean
    fun makeEmailOperations(
        contactOperations: ContactOperations,
        contactRepository: ContactRepository
    ): EmailOperations {
        return EmailOperations(
            contactOperations = contactOperations,
            contactRepository = contactRepository)
    }
}