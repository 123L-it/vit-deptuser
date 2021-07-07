package com.l.vit.controllers

import com.l.vit.domain.User
import com.l.vit.exceptions.NotFoundException
import com.l.vit.services.TestService
import com.l.vit.services.UserService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/test")
class TestController {

    companion object {
        val LOGGER: Logger = LoggerFactory.getLogger(TestController::class.java)
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
