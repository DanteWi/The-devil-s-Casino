import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.devilcasinodemo.retrofit.ApiClient
import kotlinx.coroutines.launch

class WalletViewModel : ViewModel() {

    var walletAmount by mutableStateOf(0.0)
        private set

    var freeCooldownMillis by mutableStateOf(0L)
        private set

    private val api = ApiClient.api

    @RequiresApi(Build.VERSION_CODES.O)
    fun fetchWallet(userId: Long) {
        viewModelScope.launch {
            try {
                val wallet = api.getWallet(userId).body()
                val cooldown = api.getFreeCooldown(userId)

                walletAmount = wallet?.saldoDC ?: 0.0

                freeCooldownMillis = System.currentTimeMillis() + (cooldown * 1000)
            } catch (_: Exception) {}
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun claimFree(userId: Long) {
        viewModelScope.launch {
            try {
                val response = api.claimFree(userId)
                if (response.isSuccessful) {
                    fetchWallet(userId) // refresh everything after claim
                }
            } catch (_: Exception) {}
        }
    }

    fun purchase(userId: Long, amount: Double) {
        viewModelScope.launch {
            try {
                val response = api.purchaseCoins(userId, amount)
                if (response.isSuccessful) {
                    walletAmount = response.body()?.saldoDC ?: walletAmount
                }
            } catch (_: Exception) {}
        }
    }
}
