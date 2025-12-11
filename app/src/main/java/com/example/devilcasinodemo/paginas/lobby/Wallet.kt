import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import kotlinx.coroutines.delay
import java.util.concurrent.TimeUnit

@Composable
fun Wallet(navController: NavHostController) {
    val free100Cooldown = remember { mutableStateOf(0L) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "userName",
            fontSize = 28.sp,
            color = Color(0xFFFFD700),
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(top = 24.dp)
        )

        Spacer(modifier = Modifier.height(24.dp))

        Column(verticalArrangement = Arrangement.spacedBy(20.dp)) {

            Row(horizontalArrangement = Arrangement.spacedBy(20.dp)) {

                // Free 100 — timer
                ShopCard(
                    amount = "100",
                    price = "Free",
                    cooldownState = free100Cooldown,
                    borderColor = Color(0xFFCC0000)
                ) {
                    free100Cooldown.value =
                        System.currentTimeMillis() + TimeUnit.HOURS.toMillis(24)
                }


                ShopCard(
                    amount = "1000",
                    price = "1.99$",
                    borderColor = Color(0xFFCC0000)
                ) {
                    // Handle paid purchase
                }
            }

            Row(horizontalArrangement = Arrangement.spacedBy(20.dp)) {

                ShopCard(
                    amount = "10000",
                    price = "15$",
                    borderColor = Color(0xFFCC0000)
                ) {
                    // Handle purchase
                }

                ShopCard(
                    amount = "100000",
                    price = "$99.99",  // Added $ for consistency
                    borderColor = Color(0xFFCC0000)
                ) {
                    // Handle purchase
                }
            }
        }
    }
}

fun formatTime(millis: Long): String {
    if (millis <= 0) return "Available"

    val hours = TimeUnit.MILLISECONDS.toHours(millis)
    val minutes = TimeUnit.MILLISECONDS.toMinutes(millis) % 60
    val seconds = TimeUnit.MILLISECONDS.toSeconds(millis) % 60

    return String.format("%02d:%02d:%02d", hours, minutes, seconds)
}

@Composable
fun ShopCard(
    amount: String,
    price: String,
    cooldownState: MutableState<Long>? = null,
    borderColor: Color,
    onClick: () -> Unit
) {
    val cooldown = cooldownState?.value
    val isOnCooldown = cooldown != null && cooldown > System.currentTimeMillis()

    // This drives the 1-second UI updates
    var now by remember { mutableStateOf(System.currentTimeMillis()) }

    LaunchedEffect(isOnCooldown) {
        if (isOnCooldown) {
            while (true) {
                now = System.currentTimeMillis()
                if (cooldown!! - now <= 0) {
                    cooldownState?.value = 0L  // Reset cooldown when it expires
                    break
                }
                delay(1000)
            }
        }
    }

    val remainingTime = if (isOnCooldown) formatTime(cooldown!! - now) else null

    val cardColor = if (isOnCooldown) Color.DarkGray else Color(0xFF141212)
    val textAlpha = if (isOnCooldown) 0.5f else 1f

    Box(
        modifier = Modifier
            .width(160.dp)
            .height(200.dp)
            .background(cardColor, RoundedCornerShape(20.dp))
            .border(4.dp, borderColor, RoundedCornerShape(20.dp))
            .clickable(enabled = !isOnCooldown) { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = amount, fontSize = 24.sp, color = Color(0xFFFFD700).copy(alpha = textAlpha))
            Text(text = "DC", fontSize = 20.sp, color = Color(0xFFCC3333).copy(alpha = textAlpha))
            Spacer(modifier = Modifier.height(12.dp))

            if (!isOnCooldown) {
                Text(text = price, fontSize = 22.sp, color = Color(0xFFFFD700))
            } else {
                Text(text = remainingTime!!, fontSize = 20.sp, color = Color(0xFFFFD700))
            }
        }
    }
}
