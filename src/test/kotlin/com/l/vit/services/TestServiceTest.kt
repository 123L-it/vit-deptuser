package com.l.vit.services

import com.l.vit.context.IUsers
import com.l.vit.exceptions.NotFoundException
import com.l.vit.test.utils.Users
import com.ninjasquad.springmockk.MockkBean
import com.ninjasquad.springmockk.SpykBean
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.verify
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.test.context.junit.jupiter.SpringExtension

@ExtendWith(SpringExtension::class)
internal class TestServiceTest {

    companion object {
        private const val TEST_USER_ID_PASS: String = "1"
        private const val TEST_USER_ID_FAILS: String = "15"
    }

    @MockkBean
    private lateinit var users: IUsers

    @SpykBean
    private lateinit var testService: TestService

    @Test
    fun `should returns a list of users`() {
        val expectedUsers = Users.getAllUsers()

        every { users.getUsers() } returns expectedUsers

        val result = testService.getAllUsers()

        verify(exactly = 1) { users.getUsers() }
        confirmVerified(users)
        Assertions.assertEquals(result, expectedUsers)
    }

    @Test
    fun `should returns an user with id == 1`() {
        val expectedUser = Users.getUserById(TEST_USER_ID_PASS)

        every { users.getUserById(any<String>()) } returns expectedUser

        val result = testService.getUserById(TEST_USER_ID_PASS)

        verify(exactly = 1) { users.getUserById(any<String>()) }
        confirmVerified(users)
        Assertions.assertEquals(result, expectedUser)
    }

    @Test
    fun `should throws not found exception if user isn't in the user list`() {
        every { users.getUserById(TEST_USER_ID_FAILS) } returns null

        val result = assertThrows<NotFoundException> {
            testService.getUserById(TEST_USER_ID_FAILS)
        }

        Assertions.assertEquals(result.message, "user not found")
    }
}
