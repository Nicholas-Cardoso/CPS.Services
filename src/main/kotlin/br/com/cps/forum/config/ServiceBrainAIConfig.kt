package br.com.cps.forum.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration

@Configuration
class ServiceBrainAIConfig {
    @Value("\${service.brainAI.url}")
    lateinit var brainAIUrl: String
}