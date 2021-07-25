package com.l.vit.controllers

import com.l.vit.config.JWTSecurity
import com.l.vit.domain.User
import com.l.vit.exceptions.NotFoundException
import com.l.vit.services.UserService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.servlet.http.Cookie
import javax.servlet.http.HttpServletResponse

@RestController
@RequestMapping("/api/v1/auth")
class UserController {
    companion object {
        val LOGGER: Logger = LoggerFactory.getLogger(TestController::class.java)
        val SECRET_KEY = "aplusbreturns123" // Secret key
    }

    @Autowired
    private lateinit var userService: UserService
    private lateinit var jwtSecurity: JWTSecurity

    init {
        TestController.LOGGER.info("Init test controller")
        jwtSecurity = JWTSecurity()
    }

    @PutMapping("")
    fun createUser(
            @RequestBody user: User,
    ): ResponseEntity<User> {
        return ResponseEntity(userService.createOrUpdateUser(user), HttpStatus.OK)
    }

    @GetMapping("/getUsers")
    fun getUserById(): ResponseEntity<*> {
        return ResponseEntity(userService.getUsers(), HttpStatus.OK)
    }

    @Throws(NotFoundException::class)
    @PostMapping("/login")
    fun login(
            @RequestBody user: User,
            response: HttpServletResponse
    ): ResponseEntity<*> {
        val isValidUser = userService.validateUser(user)

        if (isValidUser) {
            val jwt = jwtSecurity.createJWToken(user)
            var cookie = Cookie("jwt", jwt)
            cookie.isHttpOnly = true
            response.addCookie(cookie)
            return ResponseEntity(isValidUser, HttpStatus.OK)
        }
        return ResponseEntity.badRequest().body("User not found")
    }
}
