package com.l.vit.controllers

import com.l.vit.domain.User
import com.l.vit.exceptions.NotFoundException
import com.l.vit.services.TestService
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.apache.logging.log4j.message.Message
import com.l.vit.services.UserService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.CookieValue
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*
import javax.servlet.http.Cookie
import javax.servlet.http.HttpServletResponse

@RestController
@RequestMapping("/api/v1/test")
class TestController {

    companion object {
        val LOGGER: Logger = LoggerFactory.getLogger(TestController::class.java)
        val SECRET_KEY = "aplusbreturns123" // Secret key
    }

    @Autowired
    private lateinit var testService: TestService

    @Autowired
    private lateinit var userService: UserService

    init {
        LOGGER.info("Init test controller")
    }

    @Throws(NotFoundException::class)
    @GetMapping("/{id}")
    fun getUserById(@PathVariable("id") id: String): ResponseEntity<*> {
        LOGGER.info("start request with id: $id")
        return ResponseEntity(testService.getUserById(id), HttpStatus.OK)
    }

    @Throws(NotFoundException::class)
    @GetMapping("login/{username}") // Temp simulate the Login
    fun getUserByName(@PathVariable("username") username: String, response: HttpServletResponse): ResponseEntity<*> {
        var user = testService.getUserByName(username)
        val issuer = user.id
        val jwt = Jwts.builder()
                .setIssuer(issuer)
                .setExpiration(Date(System.currentTimeMillis() + 60 * 24 * 1000)) // 1 day
                .signWith(SignatureAlgorithm.ES512, SECRET_KEY)
                .compact()

        var cookie = Cookie("jwt", jwt)
        cookie.isHttpOnly = true
        response.addCookie(cookie)
        return ResponseEntity.ok("Success")
    }



    @Throws(NotFoundException::class)
    @GetMapping("user") // Temp simulate the Login
    fun validateUser(@CookieValue("jwt") jwt: String?): ResponseEntity<*> {
        try{
            jwt?.let {
                val body = Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(jwt).body
                return ResponseEntity.ok(testService.getUserById(body.toString()))
            }

            return ResponseEntity.status(401).body("You should login first")
        }
        catch (e:Exception){
            return ResponseEntity.status(401).body("You should login first")
        }
    }

    @GetMapping("")
    fun getUsers(): ResponseEntity<*> {
        return ResponseEntity(testService.getAllUsers(), HttpStatus.OK)
    }

    @PutMapping("")
    fun createUser(
        @RequestBody user: User,
    ): ResponseEntity<User> {
        LOGGER.info("username: ${user.username}, password: ${user.password}")
        return ResponseEntity(userService.createOrUpdateUser(user), HttpStatus.OK)
    }
}
