package com.app.quiz.securities

import com.app.quiz.services.auth.TokenService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.oauth2.server.resource.InvalidBearerTokenException
import org.springframework.security.oauth2.server.resource.authentication.BearerTokenAuthenticationToken
import org.springframework.security.web.SecurityFilterChain
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource

@Configuration
open class SecurityConfiguration
{

    @Autowired
    private lateinit var tokenService: TokenService

    @Bean
    open fun bCryptPasswordEncoder(): BCryptPasswordEncoder
    {
        return BCryptPasswordEncoder()
    }

    @Bean
    open fun filterChain(http: HttpSecurity): SecurityFilterChain
    {
        // Define public and private routes
        http.authorizeHttpRequests()
//            .requestMatchers(HttpMethod.POST, "/api/login").permitAll()
//            .requestMatchers(HttpMethod.POST, "/api/question/").permitAll()
//            .requestMatchers(HttpMethod.POST, "/api/quiz/create").permitAll()
//            .requestMatchers(HttpMethod.POST, "/api/register-admin").permitAll()
//            .requestMatchers(HttpMethod.GET, "/api/question/all").permitAll()
////            .requestMatchers(HttpMethod.GET, "/api/session/**").permitAll()
//            .requestMatchers(HttpMethod.PUT, "/api/session/end").authenticated()
//            .requestMatchers(HttpMethod.POST, "/api/register").hasAuthority("ADMIN")
//            .requestMatchers("/api/**").authenticated()
            .anyRequest().permitAll()

        // Configure JWT
        http.oauth2ResourceServer().jwt()
        http.authenticationManager { auth ->
            val jwt = auth as BearerTokenAuthenticationToken
            val user = tokenService.parseToken(jwt.token) ?: throw InvalidBearerTokenException("Invalid token")
            val authorities = listOf(SimpleGrantedAuthority(user.role.toString()))
            UsernamePasswordAuthenticationToken(user, "", authorities)
        }

        // Other configuration
        http.cors()
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        http.csrf().disable()
        http.headers().frameOptions().disable()
        http.headers().xssProtection().disable()

        return http.build()
    }


    // TODO voir ça nouveau fonctionnement

//    @Bean
//    @Throws(Exception::class)
//    open fun filterChain(httpSecurity: HttpSecurity): SecurityFilterChain?
//    {
//        return httpSecurity
//            .csrf { csrf: CsrfConfigurer<HttpSecurity> -> csrf.disable() }
//            .authorizeHttpRequests { auth ->
//                auth
//                    .requestMatchers("/api/login").permitAll()
//                    .requestMatchers("/api/register/new-user").permitAll()
//                    .anyRequest().authenticated()
//            }
//            .sessionManagement { sess: SessionManagementConfigurer<HttpSecurity?> ->
//                sess.sessionCreationPolicy(
//                    SessionCreationPolicy.STATELESS
//                )
//            }
//            .oauth2ResourceServer { obj: OAuth2ResourceServerConfigurer<HttpSecurity?> -> obj.jwt() }
//            .httpBasic(Customizer.withDefaults())
//            .build()
//    }

    @Bean
    open fun corsConfigurationSource(): CorsConfigurationSource
    {
        // allow localhost for dev purposes
        val configuration = CorsConfiguration()
        configuration.allowedOrigins = listOf("http://localhost:8080", "http://localhost:4200", "http://localhost:3000", "https://kouiz.in/", "https://www.kouiz.in/")
        configuration.allowedMethods = listOf("GET", "POST", "PUT", "DELETE")
        configuration.allowedHeaders = listOf("authorization", "content-type")
        val source = UrlBasedCorsConfigurationSource()
        source.registerCorsConfiguration("/**", configuration)
        return source
    }
}