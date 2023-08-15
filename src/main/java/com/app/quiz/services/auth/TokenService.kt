package com.app.quiz.services.auth

import com.app.quiz.entities.user.User
import com.app.quiz.services.user.UsersService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Lazy
import org.springframework.security.oauth2.jwt.*
import org.springframework.stereotype.Service
import java.time.Instant
import java.time.temporal.ChronoUnit

@Service
class TokenService @Autowired constructor(
    private val jwtDecoder: JwtDecoder,
    private val jwtEncoder: JwtEncoder,
    @Lazy private var usersService: UsersService
)
{

    fun createToken(user: User): String
    {
        val jwsHeader = JwsHeader.with { "HS256" }.build()
        val claims = JwtClaimsSet.builder()
            .issuedAt(Instant.now())
            .expiresAt(Instant.now().plus(30L, ChronoUnit.DAYS))
            .subject(user.email)
            .claim("userId", user.id)
            .build()
        return jwtEncoder.encode(JwtEncoderParameters.from(jwsHeader, claims)).tokenValue
    }

    fun parseToken(token: String): User?
    {
        return try
        {
            val jwt = jwtDecoder.decode(token)
            val userId = jwt.claims["userId"] as Long
            this.usersService.findUserById(userId)
        } catch (e: Exception)
        {
            null
        }
    }
}