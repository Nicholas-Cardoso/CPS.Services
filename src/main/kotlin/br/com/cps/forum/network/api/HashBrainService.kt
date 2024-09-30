package br.com.cps.forum.network.api

import br.com.cps.forum.dto.Speech
import org.springframework.stereotype.Service
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

@Service
interface HashBrainService {
    @POST("api/safe-speech")
    fun checkSafeSpeech(@Body input: Speech): Call<Boolean?>
}