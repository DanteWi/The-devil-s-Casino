package com.example.devilcasinodemo

import WalletViewModel
import com.example.devilcasinodemo.mvc.dto.WalletResponse
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

@OptIn(ExperimentalCoroutinesApi::class)
class WalletViewModelTest {

    @get:Rule
    val dispatcherRule = MainDispatcherRule()

    private lateinit var api: FakeApiService
    private lateinit var viewModel: WalletViewModel


    @Before
    fun setup() {
        api = FakeApiService()

        ApiClient.api = api // ⚠️ needs to be var in ApiClient
        viewModel = WalletViewModel()
    }


    @Test
    fun fetchWallet_updatesData(): Unit = runTest {

        // Creamos WalletResponse completo
        api.walletResponse =
            Response.success(
                WalletResponse(
                    userId = 1L,
                    saldoDC = 100.0,
                    lastFreeClaim = null
                )
            )

        api.cooldown = 60

        viewModel.fetchWallet(1)

        advanceUntilIdle()

        // Comprobamos que walletAmount se actualiza
        assertEquals(100.0, viewModel.walletAmount, 0.01)
        assertTrue(viewModel.freeCooldownMillis > 0)
    }


    @Test
    fun purchase_updatesWallet() = runTest {

        api.purchaseResponse =
            Response.success(
                WalletResponse(
                    userId = 1L,
                    saldoDC = 200.0,
                    lastFreeClaim = null
                )
            )

        viewModel.purchase(1, 50.0)

        advanceUntilIdle()

        assertEquals(200.0, viewModel.walletAmount, 0.01)
    }

    @Test
    fun claimFree_updatesWalletAndCooldown() = runTest {
        // Set initial wallet
        api.walletResponse = Response.success(
            WalletResponse(
                userId = 1L,
                saldoDC = 100.0,
                lastFreeClaim = null
            )
        )
        api.cooldown = 60

        // Set the claim response (adds +100)
        api.purchaseResponse = Response.success(
            WalletResponse(
                userId = 1L,
                saldoDC = 200.0,
                lastFreeClaim = System.currentTimeMillis().toString()
            )
        )

        // Call claimFree
        viewModel.claimFree(1)

        advanceUntilIdle()

        // Assert walletAmount updated
        assertEquals(200.0, viewModel.walletAmount, 0.01)

        // Assert cooldown updated
        assertTrue(viewModel.freeCooldownMillis > System.currentTimeMillis())
    }

}
