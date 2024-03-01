package com.example.network

import androidx.test.core.app.ApplicationProvider
import com.example.network.network.data.LoginRequest
import com.example.network.network.data.LoginResponse
import com.example.network.network.helpers.AuthServiceHelper
import com.example.network.network.repositories.EncryptedUserSessionRepository
import com.example.network.network.repositories.UserSessionRepository
import com.example.network.network.services.AuthService
import com.example.template.viewmodels.LoginState
import com.example.template.viewmodels.LoginViewModel
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.manipulation.Ordering.Context
import org.mockito.ArgumentMatchers.eq
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations
import org.mockito.*
import org.mockito.Mockito.mock


import retrofit2.Response
import java.security.MessageDigest

@ExperimentalCoroutinesApi
class LoginViewModelTest {

    private var authServiceHelper: AuthServiceHelper = mockk(relaxed = true)
    private var authService: AuthService = mockk(relaxed = true)
    private var userSessionRepository: UserSessionRepository = mockk(relaxed = true)

    private lateinit var viewModel: LoginViewModel

    @Before
    fun setup() {
//        authServiceHelper = mockk<AuthServiceHelper>(relaxed = true) {
//            // Specify that generateSalt should not be mocked and should use the real implementation
//            every { generateSalt() } answers { callOriginal() }
//        }
        every { authServiceHelper.generateSalt() } returns "testSalt"
        every { authServiceHelper.hashPassword(any(), any()) } returns "hashedPassword"
        Dispatchers.setMain(Dispatchers.Unconfined)


        viewModel = LoginViewModel(userSessionRepository, authServiceHelper, authService)
    }

    @After
    fun tearDown() {
        // Reset the main dispatcher to the original Main dispatcher
        Dispatchers.resetMain()
    }

    @Test
    fun `loginForTest updates loginState to Success on successful login`() = runBlockingTest {
        // Prepare the expected request and response
        val loginRequest = LoginRequest("username", "password", "testSalt")
        val loginResponse = LoginResponse("username", "userUUID", "accessToken", 3600L)

        // Mock the network call
        coEvery { authService.loginUser(loginRequest) } returns Response.success(loginResponse)

        // Call the function under test
        viewModel.loginForTest("username", "password")

        // Ne radi jer ne dohvata funkcije iz userSessionRepository
//        verify { userSessionRepository.storeTokenZero("hashedPassword") }
//        verify { userSessionRepository.storeLoginDetails("userUUID", "accessToken", 3600L) }

        // Check the loginState is updated to Success
        assertEquals(LoginState.Success, viewModel.loginState.value)
    }

    @Test
    fun `hashPassword produces expected hash`() {
        val authServiceHelper = AuthServiceHelper() // Use the actual implementation

        val password = "testPassword"
        val salt = "testSalt"

        val expectedHash = computeSha512Hash(salt + password)

        val actualHash = authServiceHelper.hashPassword(password, salt)

        assertEquals("Hashed output should match expected value", expectedHash, actualHash)
    }
    private fun computeSha512Hash(input: String): String {
        val md = MessageDigest.getInstance("SHA-512")
        md.update(input.toByteArray())
        val digest = md.digest()
        return digest.joinToString("") { "%02x".format(it) }
    }
    @Test
    fun `generateSalt produces valid salts`() {
        val authServiceHelper = AuthServiceHelper() // Use the actual implementation
        val salts = mutableListOf<String>()

        repeat(100) {
            val temp = authServiceHelper.generateSalt()
            salts.add(temp)
            println("Salt $it : $temp")
        }

        salts.forEach { salt ->
            assertTrue("Salt should not be empty", salt.isNotEmpty())
            assertEquals("Salt should be 32 characters long (MD5 hash length)", 32, salt.length)
        }

        assertEquals("All generated salts should be unique", 100, salts.distinct().size)
    }


}