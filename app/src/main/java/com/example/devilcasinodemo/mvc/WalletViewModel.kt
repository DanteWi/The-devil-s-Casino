import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.devilcasinodemo.retrofit.ApiClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException

class WalletViewModel : ViewModel() {

    companion object {
        private const val TAG = "WalletViewModel"
    }

    var walletAmount by mutableStateOf(0.0)
        private set

    var freeCooldownMillis by mutableStateOf(0L)
        private set

    var isLoading by mutableStateOf(false)
        private set

    var errorMessage by mutableStateOf<String?>(null)
        private set

    private val api = ApiClient.api

    @RequiresApi(Build.VERSION_CODES.O)
    fun fetchWallet(userId: Long) {

        if (isLoading) return

        viewModelScope.launch {
            isLoading = true
            errorMessage = null

            Log.d(TAG, "Fetching wallet for userId=$userId")

            runCatching {
                withContext(Dispatchers.IO) {
                    val wallet = api.getWallet(userId).body()
                    val cooldown = api.getFreeCooldown(userId)
                    wallet to cooldown
                }
            }.onSuccess { (wallet, cooldown) ->

                walletAmount = wallet?.saldoDC ?: 0.0
                freeCooldownMillis = System.currentTimeMillis() + (cooldown * 1000)

                Log.i(TAG, "Wallet fetched: saldo=${walletAmount}, cooldown=${freeCooldownMillis}")

            }.onFailure { throwable ->

                errorMessage = when (throwable) {
                    is IOException -> "Sin conexión a internet"
                    is HttpException -> "Error del servidor (${throwable.code()})"
                    else -> "Error inesperado al cargar wallet"
                }

                Log.e(TAG, "Failed to fetch wallet", throwable)
            }.also {
                isLoading = false
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun claimFree(userId: Long) {
        viewModelScope.launch {
            if (isLoading) return@launch
            isLoading = true
            errorMessage = null

            Log.d(TAG, "Claiming free coins for userId=$userId")

            runCatching {
                withContext(Dispatchers.IO) {
                    api.claimFree(userId)
                }
            }.onSuccess { response ->

                if (response.isSuccessful) {
                    Log.i(TAG, "Free coins claimed successfully")
                    fetchWallet(userId)
                } else {
                    val msg = response.errorBody()?.string() ?: "Error al reclamar gratis"
                    errorMessage = msg
                    Log.w(TAG, "Claim free failed: $msg")
                }

            }.onFailure { throwable ->
                errorMessage = when (throwable) {
                    is IOException -> "Sin conexión a internet"
                    is HttpException -> "Error del servidor (${throwable.code()})"
                    else -> "Error inesperado al reclamar gratis"
                }
                Log.e(TAG, "Claim free failed", throwable)
            }.also { isLoading = false }
        }
    }

    fun purchase(userId: Long, amount: Double) {
        viewModelScope.launch {
            if (isLoading) return@launch
            isLoading = true
            errorMessage = null

            Log.d(TAG, "Purchasing $amount coins for userId=$userId")

            runCatching {
                withContext(Dispatchers.IO) {
                    api.purchaseCoins(userId, amount)
                }
            }.onSuccess { response ->

                if (response.isSuccessful) {
                    walletAmount = response.body()?.saldoDC ?: walletAmount
                    Log.i(TAG, "Purchase successful: saldo=${walletAmount}")
                } else {
                    val msg = response.errorBody()?.string() ?: "Error en la compra"
                    errorMessage = msg
                    Log.w(TAG, "Purchase failed: $msg")
                }

            }.onFailure { throwable ->
                errorMessage = when (throwable) {
                    is IOException -> "Sin conexión a internet"
                    is HttpException -> "Error del servidor (${throwable.code()})"
                    else -> "Error inesperado al comprar"
                }
                Log.e(TAG, "Purchase failed", throwable)
            }.also { isLoading = false }
        }
    }
}
