package br.com.cps.forum.extension

import br.com.cps.forum.dto.Speech
import br.com.cps.forum.exception.NotSafeSpeechException
import br.com.cps.forum.network.api.HashBrainService
import org.springframework.data.jpa.repository.JpaRepository
import java.net.SocketTimeoutException

private const val notSafeSpeech: String = "O título ou corpo contém palavras de baixo calão."

fun <T : Any> checkAndSaveBrainAI(
    brainService: HashBrainService,
    entity: T,
    repository: JpaRepository<T, Long>,
    bodyExtractor: (T) -> String
): T {
    return try {
        val responseContext = brainService.checkSafeSpeech(
            Speech(bodyExtractor(entity))
        ).execute()

        if (responseContext.isSuccessful) {
            val result = responseContext.body()

            if (result != false) {
                throw NotSafeSpeechException(notSafeSpeech)
            }

            repository.save(entity)
        } else {
            throw Exception("API Request Error: ${responseContext.errorBody()?.string()}")
        }
    } catch (e: SocketTimeoutException) {
        println("Timeout: ${e.message}")
        throw e
    } catch (e: NotSafeSpeechException) {
        throw e
    } catch (e: Exception) {
        throw Exception("Error: ${e.message}")
    }
}