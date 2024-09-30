package br.com.cps.forum.network

import br.com.cps.forum.config.ServiceBrainAIConfig
import br.com.cps.forum.config.ServiceMailConfig
import br.com.cps.forum.network.api.HashBrainService
import br.com.cps.forum.network.api.MailService
import okhttp3.OkHttpClient
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Configuration
class RetrofitClient(
    private val serviceMailConfig: ServiceMailConfig,
    private val serviceBrainAIConfig: ServiceBrainAIConfig
) {
    private fun buildClient() = OkHttpClient.Builder().build()

    private fun buildMailRetrofit() = Retrofit.Builder()
        .baseUrl(serviceMailConfig.mailUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .client(buildClient())
        .build()

    private fun buildHashRetrofit() = Retrofit.Builder()
        .baseUrl(serviceBrainAIConfig.brainAIUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .client(buildClient())
        .build()

    @Bean
    fun serviceMail(): MailService = buildMailRetrofit().create(MailService::class.java)

    @Bean
    fun serviceHashBrain(): HashBrainService = buildHashRetrofit().create(HashBrainService::class.java)
}