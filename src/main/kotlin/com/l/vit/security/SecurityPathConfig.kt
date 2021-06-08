package com.l.vit.security

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding
import org.springframework.http.HttpMethod

@ConstructorBinding
@ConfigurationProperties(prefix = "auth")
data class SecurityPathConfig(val securityPaths: List<SecurityPath>) {
    data class SecurityPath(
        val httpMethod: HttpMethod?,
        val path: String,
        val isOpen: Boolean
    )
}
