@file:OptIn(ExperimentalCoroutinesApi::class)
package com.example.devilcasinodemo

import com.example.devilcasinodemo.mvc.BlackjackViewModel
import com.example.devilcasinodemo.mvc.dto.BlackjackState
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

class BlackjackViewModelTest {

    @get:Rule
    val dispatcherRule = MainDispatcherRule()

    private lateinit var api: FakeApiService
    private lateinit var viewModel: BlackjackViewModel

    @Before
    fun setup() {
        api = FakeApiService()
        ApiClient.api = api
        viewModel = BlackjackViewModel(api)
    }

    @Test
    fun startGame() = runTest {
        val bjState = BlackjackState(playerCards = listOf("10H","5D"), dealerCards = listOf("9C","7S"), playerTotal = 15, dealerTotal = 16, bet = 20.0, status = "PLAYING", finished = false)
        api.startResponse = bjState

        viewModel.startGame(1L, 20.0)
        advanceUntilIdle()

        assertEquals(listOf("10H","5D"), viewModel.gameState.playerCards)
        assertEquals("PLAYING", viewModel.gameState.status)
        assertEquals(20.0, viewModel.gameState.bet, 0.01)
    }

    @Test
    fun hit() = runTest {
        val bjHit = BlackjackState(playerCards = listOf("10H","5D","2C"), playerTotal = 17)
        api.hitResponse = bjHit

        viewModel.hit(1L)
        advanceUntilIdle()

        assertEquals(listOf("10H","5D","2C"), viewModel.gameState.playerCards)
        assertEquals(17, viewModel.gameState.playerTotal)
    }

    @Test
    fun stand() = runTest {
        val bjStand = BlackjackState(dealerCards = listOf("9C","7S","5D"), dealerTotal = 21, status = "PLAYER_WIN", finished = true)
        api.standResponse = bjStand

        viewModel.stand(1L)
        advanceUntilIdle()

        assertEquals(listOf("9C","7S","5D"), viewModel.gameState.dealerCards)
        assertEquals("PLAYER_WIN", viewModel.gameState.status)
        assertTrue(viewModel.gameState.finished)
    }

    @Test
    fun resetGame() {
        viewModel.gameState = BlackjackState(playerCards = listOf("10H"), dealerCards = listOf("9C"))
        viewModel.resetGame()

        assertTrue(viewModel.gameState.playerCards.isEmpty())
        assertTrue(viewModel.gameState.dealerCards.isEmpty())
    }
}
