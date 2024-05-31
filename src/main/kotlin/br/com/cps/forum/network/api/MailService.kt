package br.com.cps.forum.network.api

import br.com.cps.forum.dto.MailForm
import org.springframework.stereotype.Service
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

@Service
interface MailService {
    @POST("send-mail")
    fun sendMails(@Body model: MailForm): Call<Unit>
}