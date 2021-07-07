package com.l.vit.services

import com.l.vit.domain.User

interface IUserService {
    fun createOrUpdateUser(user: User): User?
}
