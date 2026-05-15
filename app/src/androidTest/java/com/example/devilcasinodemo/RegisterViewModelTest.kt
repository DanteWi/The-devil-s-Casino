

@file:OptIn(ExperimentalCoroutinesApi::class)
package com.example.devilcasinodemo

import com.example.devilcasinodemo.mvc.RegisterViewModel
import com.example.devilcasinodemo.mvc.dto.RegisterResponse
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

class RegisterViewModelTest {

    @get:Rule
    val dispatcherRule = MainDispatcherRule()

    private lateinit var api: FakeApiService
    private lateinit var viewModel: RegisterViewModel

    @Before
    fun setup() {
        api = FakeApiService()
        ApiClient.api = api
        viewModel = RegisterViewModel()
    }

    @Test
    fun register_success() = runTest {
        // Provide all required fields for RegisterResponse
        api.registerResponse = Response.success(
            RegisterResponse(
                message = "User created",
                id = 1L,
                nombre = "Test"
            )
        )

        var result = false
        var msg: String? = null

        viewModel.register("Test", "test@test.com", "1234") { success, message ->
            result = success
            msg = message
        }

        advanceUntilIdle()

        assertTrue(result)
        assertEquals("User created", msg)
    }

}
