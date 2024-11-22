package br.com.cps.forum.security

import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority

class JwtAuthentication(
    private val oid: String?,
    private val name: String?,
    private val email: String?
) : Authentication {
    private var authenticated = true

    override fun getAuthorities(): Collection<GrantedAuthority> = authorities

    override fun getCredentials(): Any = ""

    override fun getDetails(): Any = ""

    override fun getPrincipal(): Any = name ?: ""

    override fun isAuthenticated(): Boolean = authenticated

    override fun setAuthenticated(isAuthenticated: Boolean) {
        authenticated = isAuthenticated
    }

    override fun getName(): String? = name
    fun getOid(): String? = oid
    fun getEmail(): String? = email
}