package br.com.cps.forum.network

import br.com.cps.forum.config.ServiceMailConfig
import br.com.cps.forum.network.api.MailService
import okhttp3.OkHttpClient
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Configuration
class RetrofitClient(
    private val serviceMailConfig: ServiceMailConfig
) {
    private fun buildClient() = OkHttpClient.Builder().build()

    private fun buildRetrofit() = Retrofit.Builder()
        .baseUrl(serviceMailConfig.mailUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .client(buildClient())
        .build()

    @Bean
    fun serviceMail(): MailService = buildRetrofit().create(MailService::class.java)
}