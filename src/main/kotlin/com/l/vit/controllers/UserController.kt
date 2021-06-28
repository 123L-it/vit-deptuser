package com.l.vit.controllers

import com.l.vit.models.User
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties
import org.springframework.security.core.authority.AuthorityUtils
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import java.util.*


@RestController
class UserController {


    @PostMapping("user")
    fun login(@RequestParam("user") username: String? ) : User? {
        val token: String = getJWTToken(username)
        val user = User(token, username ?: "", true)
        return user
    }

    fun getJWTToken(username: String?): String {

        val secretKey = "aplusbreturns123" // Secret key

        val issuer = username

        var token = Jwts.builder()
                .setIssuer(issuer)
                .setExpiration(Date(System.currentTimeMillis() + 60 * 24 * 1000)) // 1 day
                .signWith(SignatureAlgorithm.ES512, secretKey)
                .compact()

        return "Bearer $token"
    }
}
