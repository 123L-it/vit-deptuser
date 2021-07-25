package com.l.vit.services

import com.l.vit.domain.User

interface IUserService {
    fun createOrUpdateUser(user: User): User?
    fun getUsers():List<User>?
    fun validateUser(user: User): Boolean
}
