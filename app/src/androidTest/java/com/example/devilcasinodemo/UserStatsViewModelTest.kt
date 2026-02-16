
@file:OptIn(ExperimentalCoroutinesApi::class)
package com.example.devilcasinodemo

import com.example.devilcasinodemo.mvc.UserStatsViewModel
import com.example.devilcasinodemo.mvc.dto.WinLossResponse
import com.example.devilcasinodemo.util.FakeApiService
import com.example.devilcasinodemo.util.MainDispatcherRule
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class UserStatsViewModelTest {

    @get:Rule
    val dispatcherRule = MainDispatcherRule()

    private lateinit var api: FakeApiService
    private lateinit var viewModel: UserStatsViewModel

    @Before
    fun setup() {
        api = FakeApiService()
        viewModel = UserStatsViewModel(api)
    }

    @Test
    fun loadStats_updatesRates() = runTest {
        api.statsResponse = WinLossResponse(wins = 8, losses = 2, winRate = 80.0, lossRate = 20.0)

        viewModel.loadStats(1L)
        advanceUntilIdle()

        assertEquals(80.0, viewModel.winRate, 0.01)
        assertEquals(20.0, viewModel.lossRate, 0.01)
    }
}
