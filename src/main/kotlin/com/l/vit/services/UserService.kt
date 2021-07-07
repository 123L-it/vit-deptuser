package com.l.vit.services

import com.l.vit.domain.User
import com.l.vit.repository.IUserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserService @Autowired constructor(
    private val userRepository: IUserRepository,
    private val encoder: PasswordEncoder
) : IUserService {

    @Transactional
    override fun createOrUpdateUser(user: User): User? =
        userRepository.findByUserName(user.username)?.let {
            return it
        } ?: run {
            return userRepository.save(user.copy(password = encoder.encode(user.password)))
        }
}
