package com.l.vit.controllers

import com.l.vit.config.SecurityPathConfig
import com.l.vit.exceptions.NotFoundException
import com.l.vit.models.User
import com.l.vit.services.TestService
import com.l.vit.services.UserService
import com.l.vit.test.utils.Users
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import io.mockk.junit5.MockKExtension
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get

@AutoConfigureMockMvc
@EnableConfigurationProperties(value = [SecurityPathConfig::class])
@WebMvcTest(controllers = [TestController::class])
@ExtendWith(SpringExtension::class, MockKExtension::class)
internal class TestControllerTest {

    companion object {
        private const val TEST_USER_ID_PASS: String = "1"
        private const val TEST_USER_ID_FAILS: String = "15"
    }

    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockkBean
    private lateinit var testService: TestService

    @MockkBean
    private lateinit var userService: UserService

    @Test
    fun `should returns a list of user`() {
        val expectedUsers = Users.getAllUsers()
        every { testService.getAllUsers() } returns expectedUsers

        val result = mockMvc.get("/api/v1/test") {
            contentType = MediaType.APPLICATION_JSON
            accept = MediaType.APPLICATION_JSON
        }.andExpect {
            status { isOk() }
            content { contentType(MediaType.APPLICATION_JSON) }
        }.andReturn()

        val resultUsers = Users.returnUserFromString<List<User>>(result.response.contentAsString)

        Assertions.assertEquals(resultUsers.size, expectedUsers.size)
        Assertions.assertEquals(resultUsers, expectedUsers)
    }

    @Test
    fun `should returns an user with id == 1`() {
        val expectedUser = Users.getUserById(TEST_USER_ID_PASS)

        every { testService.getUserById(any<String>()) } returns expectedUser!!

        val result = mockMvc.get("/api/v1/test/{id}", TEST_USER_ID_PASS) {
            contentType = MediaType.APPLICATION_JSON
            accept = MediaType.APPLICATION_JSON
        }.andExpect {
            status { isOk() }
            content { contentType(MediaType.APPLICATION_JSON) }
        }.andReturn()

        val resultUser = Users.returnUserFromString<User>(result.response.contentAsString)

        Assertions.assertEquals(resultUser, expectedUser)
    }

    @Test
    fun `should throws not found exception if user isn't in the user list`() {
        every { testService.getUserById(any<String>()) }.throws(NotFoundException("user not found"))

        val result = mockMvc.get("/api/v1/test/{id}", TEST_USER_ID_FAILS) {
            contentType = MediaType.APPLICATION_JSON
            accept = MediaType.APPLICATION_JSON
        }.andExpect {
            status { isNotFound() }
            content { contentType(MediaType.APPLICATION_JSON) }
        }.andReturn()

        val resultMap = Users.returnUserFromString<Map<String, Any?>>(result.response.contentAsString)
        val message: String by resultMap

        Assertions.assertEquals(message, "user not found")
    }
}
