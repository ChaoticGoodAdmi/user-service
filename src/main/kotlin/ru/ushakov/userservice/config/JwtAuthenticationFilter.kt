package ru.ushakov.userservice.config

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.web.filter.OncePerRequestFilter

class JwtAuthenticationFilter(
    private val userDetailsService: UserDetailsService,
) : OncePerRequestFilter() {

    @Value("\${jwt.secret}")
    private lateinit var jwtSecret: String

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain,
    ) {
        val jwt = parseJwt(request)
        if (jwt != null && validateJwtToken(jwt)) {
            val username = getUserNameFromJwtToken(jwt)

            val userDetails = userDetailsService.loadUserByUsername(username)
            val authentication = UsernamePasswordAuthenticationToken(userDetails, null, userDetails.authorities)
            authentication.details = WebAuthenticationDetailsSource().buildDetails(request)

            SecurityContextHolder.getContext().authentication = authentication
        }
        filterChain.doFilter(request, response)
    }

    private fun parseJwt(request: HttpServletRequest): String? {
        val headerAuth = request.getHeader("Authorization")
        return if (headerAuth != null && headerAuth.startsWith("Bearer ")) {
            headerAuth.substring(7, headerAuth.length)
        } else null
    }

    private fun validateJwtToken(authToken: String): Boolean {
        return try {
            Jwts.parser().verifyWith(Keys.hmacShaKeyFor(jwtSecret.toByteArray())).build().parseSignedClaims(authToken)
            true
        } catch (e: Exception) {
            println("Invalid JWT token: ${e.message}")
            false
        }
    }

    private fun getUserNameFromJwtToken(token: String): String {
        val claims: Claims = Jwts.parser().verifyWith(Keys.hmacShaKeyFor(jwtSecret.toByteArray())).build()
            .parseSignedClaims(token).payload
        return claims.subject
    }
}
