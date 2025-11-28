package com.example.devilcasinodemo.paginas.lobby

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.devilcasinodemo.R

@Composable
fun User(navController: NavHostController) {

        val winPercent = 32.0
        val losePercent = 20.0

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black)
                .padding(top = 40.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 40.dp),
                horizontalArrangement = Arrangement.End
            ) {
                // Avatar + Settings button
                Box(
                    modifier = Modifier
                        .size(160.dp)
                        .background(Color(0xFFB71C1C), RoundedCornerShape(24.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = null,
                        tint = Color.Black,
                        modifier = Modifier.size(90.dp)
                    )
                }

                // Settings button on the side
                Box(
                    modifier = Modifier
                        .size(100.dp)
                        .background(Color.Transparent, RoundedCornerShape(24.dp)),
                    contentAlignment = Alignment.Center
                ){
                IconButton(onClick = { /* TODO */ }) {
                    Icon(
                        imageVector = Icons.Default.Settings,
                        contentDescription = null,
                        tint = Color.Black,
                        modifier = Modifier.size(90.dp)
                    )

                }
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                "(USER NAME)",
                color = Color.Yellow,
                fontSize = 26.sp
            )

            Text(
                "XXXX   DC",
                color = Color(0xFFFF1744),
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(40.dp))

            // Donut chart
            DonutChart(
                wins = winPercent,
                losses = losePercent,
                size = 250.dp
            )

            Spacer(modifier = Modifier.height(40.dp))

            // Card in usage
            Box(
                modifier = Modifier
                    .fillMaxWidth(0.85f)
                    .height(90.dp)
                    .border(3.dp, Color.Red, RoundedCornerShape(20.dp))
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {

                Row(verticalAlignment = Alignment.CenterVertically) {

                    // Fake mastercard logo
                    Box(
                        modifier = Modifier
                            .size(26.dp)
                            .background(Color.Red, CircleShape)
                    )

                    Box(
                        modifier = Modifier
                            .offset(x = (-10).dp)
                            .size(26.dp)
                            .background(Color.Yellow, CircleShape)
                    )

                    Spacer(modifier = Modifier.width(10.dp))

                    Text(
                        "Card in usage:   ES149185...",
                        color = Color.White
                    )
                }
            }
        }
    }



    @Composable
    fun DonutChart(
        wins: Double,
        losses: Double,
        size: Dp
    ) {
        val sweepWin = (wins / (wins + losses)) * 360
        val sweepLoss = 360 - sweepWin

        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.size(size)
        ) {

            Canvas(modifier = Modifier.size(size)) {

                val strokeWidth = size.toPx() * 0.20f

                // Losses (red)
                drawArc(
                    color = Color.Red,
                    startAngle = -90f,
                    sweepAngle = sweepLoss.toFloat(),
                    useCenter = false,
                    style = Stroke(width = strokeWidth)
                )

                // Wins (yellow)
                drawArc(
                    color = Color.Yellow,
                    startAngle = (-90 + sweepLoss).toFloat(),
                    sweepAngle = sweepWin.toFloat(),
                    useCenter = false,
                    style = Stroke(width = strokeWidth)
                )
            }

            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text("Wins: $wins%", color = Color.White, fontSize = 14.sp)
                Text("Losses: $losses%", color = Color.White, fontSize = 14.sp)
            }
        }
    }

