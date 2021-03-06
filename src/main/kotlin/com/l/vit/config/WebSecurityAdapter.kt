package com.l.vit.config

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.security.SecurityProperties
import org.springframework.context.annotation.Bean
import org.springframework.core.annotation.Order
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.factory.PasswordEncoderFactories
import org.springframework.security.crypto.password.PasswordEncoder

@EnableWebSecurity
@Order(SecurityProperties.BASIC_AUTH_ORDER)
class WebSecurityAdapter @Autowired constructor(
    private val securityPathConfig: SecurityPathConfig
) : WebSecurityConfigurerAdapter() {

    @Bean
    fun passwordEncoder(): PasswordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder()

    override fun configure(http: HttpSecurity?) {
        http?.let { httpSecurity ->
            val httpRegistry = httpSecurity.csrf().disable().cors().and().authorizeRequests()

            for (securityPath: SecurityPathConfig.SecurityPath in securityPathConfig.securityPaths) {
                if (securityPath.isOpen) {
                    securityPath.httpMethod.let {
                        httpRegistry.antMatchers(it, securityPath.path).permitAll()
                    } ?: run {
                        httpRegistry.antMatchers(securityPath.path).permitAll()
                    }
                } else {
                    securityPath.httpMethod.let {
                        httpRegistry.antMatchers(it, securityPath.path).authenticated()
                    } ?: run {
                        httpRegistry.antMatchers(securityPath.path).authenticated()
                    }
                }
            }

            httpRegistry
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)

            httpSecurity.headers().cacheControl()
        }
    }
}
