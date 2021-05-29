package com.l.vit.context

import com.l.vit.models.User
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class Users: IUsers {

    private var users: List<User> = listOf(
            User("1", "test", true),
            User("2", "test2", true),
            User("3", "test3", true),
            User("4", "test4", true),
            User("5", "test5", true),
            User("6", "test6", true)
    )

    override fun getUserById(id: String): User? {
        return users.find { it.id == id }
    }

    override fun getUsers(): List<User>? {
        return users
    }
}