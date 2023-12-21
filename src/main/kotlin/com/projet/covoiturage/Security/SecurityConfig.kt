package com.projet.covoiturage.Security

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.invoke
import org.springframework.security.oauth2.core.DelegatingOAuth2TokenValidator
import org.springframework.security.oauth2.core.OAuth2TokenValidator
import org.springframework.security.oauth2.jwt.*
import org.springframework.security.web.SecurityFilterChain

@Configuration
@EnableWebSecurity
class SecurityConfig {

    @Value("\${auth0.audience}")
    private lateinit var audience: String

    @Value("\${spring.security.oauth2.resourceserver.jwt.issuer-uri}")
    private lateinit var issuer: String

    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        http {
            authorizeHttpRequests {
                authorize("/", permitAll)
                authorize("/api-docs/**", permitAll)
                authorize("/documentation", permitAll)
                authorize("/swagger-ui/**", permitAll)
                authorize(HttpMethod.GET, "/**", authenticated)
                authorize(HttpMethod.POST, "/**", authenticated)
                authorize(HttpMethod.PUT, "/**", authenticated)
                authorize(HttpMethod.DELETE, "/**", authenticated)
                //authorize(anyRequest, authenticated)
            }

            oauth2ResourceServer {
                jwt { }
            }
        }
        return http.build()
    }

    @Bean
    fun jwtDecoder(): JwtDecoder {
        val withAudience: OAuth2TokenValidator<Jwt> = AudienceValidator(audience)
        val withIssuer: OAuth2TokenValidator<Jwt> = JwtValidators.createDefaultWithIssuer(issuer)
        val validator: OAuth2TokenValidator<Jwt> = DelegatingOAuth2TokenValidator(withAudience, withIssuer)

        val jwtDecoder: NimbusJwtDecoder = JwtDecoders.fromOidcIssuerLocation(issuer) as NimbusJwtDecoder
        jwtDecoder.setJwtValidator(validator)
        return jwtDecoder
    }

}
