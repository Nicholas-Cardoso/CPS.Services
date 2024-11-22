package br.com.cps.forum.security

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import java.util.*

@Component
class JWTAuthenticationFilter : OncePerRequestFilter() {
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val authHeader = request.getHeader("Authorization")

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            val jwtToken = authHeader.substring(7)

            try {
                val payload = jwtToken.split(".")[1]
                val decodedPayload = String(Base64.getUrlDecoder().decode(payload))

                val claims = jacksonObjectMapper().readTree(decodedPayload)

                val oid = claims["oid"]?.asText()
                val name = claims["name"]?.asText()
                val email = claims["email"]?.asText()

                val authentication = JwtAuthentication(oid, name, email)
                SecurityContextHolder.getContext().authentication = authentication
            } catch (e: Exception) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid JWT token.")
                return
            }
        }

        filterChain.doFilter(request, response)
    }
}