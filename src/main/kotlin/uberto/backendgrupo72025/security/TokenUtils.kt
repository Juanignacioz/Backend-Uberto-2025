package uberto.backendgrupo72025.security

import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.stereotype.Component
import uberto.backendgrupo72025.domain.CredencialesInvalidasException
import uberto.backendgrupo72025.domain.ROLES
import uberto.backendgrupo72025.domain.TokenExpiradoException
import java.util.*
import kotlin.time.Duration.Companion.minutes

@Component
class TokenUtils {
    @Value("\${security.secret-key}")
    lateinit var secretKey: String

    @Value("\${security.access-token-minutes}")
    var accessTokenMinutes: Int = 30

    val logger: Logger = LoggerFactory.getLogger(TokenUtils::class.java)

    fun createToken(id: String?, rol : ROLES): String? {
        val longExpirationTime = accessTokenMinutes.minutes.inWholeMilliseconds

        val now = Date()

        return Jwts.builder()
            .subject(id)
            .issuedAt(now)
            .expiration(Date(now.time + longExpirationTime))
            .claim("rol", rol)
            .signWith(Keys.hmacShaKeyFor(secretKey.toByteArray()))
            .compact()
    }

    fun getAuthentication(bearerToken: String): UsernamePasswordAuthenticationToken {
        val token = bearerToken.substringAfter("Bearer ")
        try {
            val secret = Keys.hmacShaKeyFor(secretKey.toByteArray())
            val claims = Jwts.parser()
                .verifyWith(secret)
                .build()
                // acá se valida el vencimiento del token
                .parseSignedClaims(token)
                .payload

            // Token no tiene usuario
            if (claims.subject == null || claims.subject.isBlank()) {
                throw CredencialesInvalidasException()
            }

            logger.info("Token decoded, user: " + claims.subject + " - rol: " + claims["rol"])

            val rol = claims.get("rol", String::class.java) ?: "VIAJERO"

            // Creación de la lista de autoridades (Collection<GrantedAuthority>)
            val authorities: Collection<GrantedAuthority> = listOf(SimpleGrantedAuthority(rol))
            return UsernamePasswordAuthenticationToken(claims.subject, null, authorities)
        } catch (expiredJwtException: ExpiredJwtException) {
            throw TokenExpiradoException()
        }
    }

    fun authenticate(bearerToken: String): Pair<String, Boolean> {
        val authentication = getAuthentication(bearerToken)
        val userID = authentication.name
        val esChofer = authentication.authorities.any { it.authority.equals("CONDUCTOR", ignoreCase = false) }
        return Pair(userID, esChofer)
    }
}

