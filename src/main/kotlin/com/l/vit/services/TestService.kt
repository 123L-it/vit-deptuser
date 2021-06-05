package com.l.vit.services

import com.l.vit.context.IUsers
import com.l.vit.exceptions.NotFoundException
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class TestService {

    companion object {
        val LOGGER: Logger = LoggerFactory.getLogger(TestService::class.java)
    }

    init {
        LOGGER.info("init test service")
    }

    @Autowired
    private lateinit var users: IUsers

    fun getTestName(id: String) = users.getUserById(id)?.let { it } ?: throw NotFoundException("user not found")

    fun getAllUsers() = users.getUsers()
}
