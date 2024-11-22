package br.com.cps.forum.extension

import br.com.cps.forum.security.JwtAuthentication
import org.springframework.security.core.context.SecurityContextHolder

fun getCurrentJwtAuthentication(): JwtAuthentication? {
    val auth = SecurityContextHolder.getContext().authentication
    return if (auth is JwtAuthentication) {
        auth
    } else {
        null
    }
}