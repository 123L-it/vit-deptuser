package com.l.vit.controllers

import com.l.vit.config.NotFoundException
import com.l.vit.services.TestService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
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

    init {
        LOGGER.info("Init test controller")
    }

    @Throws(NotFoundException::class)
    @GetMapping("/{id}")
    fun getUserById(@PathVariable("id") id: String): ResponseEntity<*> {
        return ResponseEntity(testService.getTestName(id), HttpStatus.OK)
    }

    @GetMapping("")
    fun getUsers(): ResponseEntity<*> {
        return ResponseEntity(testService.getAllUsers(), HttpStatus.OK)
    }
}
