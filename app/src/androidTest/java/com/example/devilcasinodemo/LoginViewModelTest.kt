@file:OptIn(ExperimentalCoroutinesApi::class)
package com.example.devilcasinodemo

import com.example.devilcasinodemo.mvc.LoginViewModel
import com.example.devilcasinodemo.mvc.dto.LoginResponse
import com.example.devilcasinodemo.mvc.dto.LoginRequest
import com.example.devilcasinodemo.retrofit.ApiClient
import com.example.devilcasinodemo.util.FakeApiService
import com.example.devilcasinodemo.util.MainDispatcherRule
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import retrofit2.Response

class LoginViewModelTest {

    @get:Rule
    val dispatcherRule = MainDispatcherRule()

    private lateinit var api: FakeApiService
    private lateinit var viewModel: LoginViewModel

    @Before
    fun setup() {
        api = FakeApiService()
        ApiClient.api = api
        viewModel = LoginViewModel()
    }

    @Test
    fun login_success() = runTest {
        api.loginResponse = Response.success(LoginResponse(userId = 1L, name = "Tester", message = "OK"))

        var success = false
        var msg: String? = null

        viewModel.login("test@test.com", "1234") { s, m ->
            success = s
            msg = m
        }

        advanceUntilIdle()

        assertTrue(success)
        assertEquals(1L, viewModel.userId)
        assertEquals("Tester", viewModel.username)
        assertEquals("OK", msg)
    }
}
