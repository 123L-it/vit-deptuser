package com.l.vit.context

import com.l.vit.models.User

interface IUsers {
    fun getUserById(id: String): User?

    fun getUsers(): List<User>?

    fun getUserByName(name:String): User?
}
