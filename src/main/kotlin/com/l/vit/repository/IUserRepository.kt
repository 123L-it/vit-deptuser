package com.l.vit.repository

import com.l.vit.domain.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

@Repository
@Transactional(propagation = Propagation.MANDATORY)
interface IUserRepository : JpaRepository<User, UUID> {
    fun findByUserName(username: String): User?
}
