package com.example.devilcasinodemo.paginas

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.devilcasinodemo.R
import com.example.devilcasinodemo.ui.theme.DevilCasinoDemoTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NeonLoginScreen() {
    val neonOrange = Color(0xFFFF6600)
    val backgroundColor = Color(0xFF0C0602)

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor),
        color = backgroundColor
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(painter = painterResource(id = R.drawable._7e8780e_f2a5_4a66_9a91_b81658c397f3),
                contentDescription = "Login Sign",
                modifier = Modifier.padding(10.dp),
                contentScale = ContentScale.Fit
            )
            Text(

                text="Welcome back Sinner",
                color = Color.Red

            )

            Spacer(modifier = Modifier.height(32.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(BorderStroke(3.dp, neonOrange), RoundedCornerShape(16.dp))
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("Email", color = neonOrange) },
                    textStyle = LocalTextStyle.current.copy(color = neonOrange),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = neonOrange,
                        unfocusedBorderColor = neonOrange,
                        cursorColor = neonOrange
                    ),
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    label = { Text("Password", color = neonOrange) },
                    textStyle = LocalTextStyle.current.copy(color = neonOrange),
                    visualTransformation = PasswordVisualTransformation(),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = neonOrange,
                        unfocusedBorderColor = neonOrange,
                        cursorColor = neonOrange
                    ),
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(24.dp))

                Button(
                    onClick = { /* login */ },
                    colors = ButtonDefaults.buttonColors(containerColor = neonOrange),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                ) {
                    Text("Login", color = Color.Black, )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = { /* Google to implemt */ },
                    colors = ButtonDefaults.buttonColors(containerColor = neonOrange),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                ) {
                    Image(painter = painterResource(id = R.drawable.google__g__logo_svg),
                        contentDescription = "Google Logo",
                        modifier = Modifier.
                        fillMaxWidth()
                            .fillMaxHeight(),
                        contentScale = ContentScale.Fit
                    )
                }
            }
        }
    }
}
@Preview(showBackground = true)
@Composable
fun Preview() {
    DevilCasinoDemoTheme {
        NeonLoginScreen()
    }
}