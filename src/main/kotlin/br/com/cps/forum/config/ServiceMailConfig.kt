package br.com.cps.forum.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration

@Configuration
class ServiceMailConfig {
    @Value("\${service.mail.url}")
    lateinit var mailUrl: String
}