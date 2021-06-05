package com.l.vit.test.utils

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.l.vit.models.User
import java.io.File

class Users {
    companion object {
        private const val USER_LIST = "user-list.json"

        private fun getFile(fileName: String) = File(Users::class.java.classLoader.getResource(fileName).file)

        private fun readUsersFromFile() = jacksonObjectMapper().readValue<List<User>>(getFile(USER_LIST))

        fun getAllUsers() = readUsersFromFile()

        fun getUserById(id: String) = readUsersFromFile().find { it.id == id }

        inline fun <reified T> returnUserFromString(users: String): T = jacksonObjectMapper().readValue<T>(users)
    }
}
