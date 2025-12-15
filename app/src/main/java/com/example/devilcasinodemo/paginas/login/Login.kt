package com.example.devilcasinodemo.paginas.login
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.devilcasinodemo.R
import com.example.devilcasinodemo.mvc.LoginViewModel
import com.example.devilcasinodemo.ui.theme.DevilCasinoDemoTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Login(navController: NavHostController, viewModel: LoginViewModel = LoginViewModel()) {
    val neonOrange = Color(0xFFFF6600)
    val backgroundColor = Color(0xFF0C0602)

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var message by remember { mutableStateOf("") }
    val scrollState = rememberScrollState()
    val emailRegex = Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$")
    var emailError by remember { mutableStateOf(false) }

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor),
        color = backgroundColor
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp)
                .verticalScroll(scrollState),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Logo
            Image(
                painter = painterResource(id = R.drawable._7e8780e_f2a5_4a66_9a91_b81658c397f3),
                contentDescription = "Login Sign",
                modifier = Modifier.padding(10.dp),
                contentScale = ContentScale.Fit
            )

            Text(
                text = "Welcome back Sinner",
                color = Color.Red
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Form
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(BorderStroke(3.dp, neonOrange), RoundedCornerShape(16.dp))
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Email Field
                OutlinedTextField(
                    value = email,
                    onValueChange = {
                        email = it
                        emailError = !emailRegex.matches(email)
                    },
                    label = { Text("Email", color = neonOrange) },
                    textStyle = LocalTextStyle.current.copy(color = neonOrange),
                    isError = emailError,
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = neonOrange,
                        unfocusedBorderColor = neonOrange,
                        cursorColor = neonOrange,
                        errorBorderColor = Color.Red
                    ),
                    modifier = Modifier.fillMaxWidth()
                )

                if (emailError) {
                    Text(
                        text = "Email inválido",
                        color = Color.Red,
                        modifier = Modifier.align(Alignment.Start).padding(start = 8.dp, top = 4.dp)
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Password Field
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

                Spacer(modifier = Modifier.height(16.dp))

                // Message display
                if (message.isNotEmpty()) {
                    Text(
                        text = message,
                        color = if (message == "Login correcto") Color.Green else Color.Red,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Login Button
                Button(
                    onClick = {
                        if (!emailError && email.isNotBlank() && password.isNotBlank()) {
                            message = "Conectando..."
                            viewModel.login(email, password) { success, errorMessage ->
                                message = when {
                                    success -> {
                                        navController.navigate("lobby") {
                                            popUpTo("login") { inclusive = true }
                                        }
                                        "Login correcto"
                                    }
                                    errorMessage != null -> errorMessage
                                    else -> "Error desconocido"
                                }
                            }
                        } else {
                            message = "Rellena todos los campos correctamente"
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = neonOrange),
                    enabled = !emailError && email.isNotBlank() && password.isNotBlank(),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                ) {
                    Text("Login", color = Color.Black)
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Optional Google Login Button
                Button(
                    onClick = {
                        navController.navigate("test_login") {
                            popUpTo("login") { inclusive = true }
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = neonOrange),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.google__g__logo_svg),
                        contentDescription = "Google Logo",
                        modifier = Modifier.fillMaxWidth().fillMaxHeight(),
                        contentScale = ContentScale.Fit
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(text = "New here? Come with me ")
                Spacer(modifier = Modifier.height(16.dp))

                ClickableText(
                    text = AnnotatedString(text = "Welcome sinner"),
                    style = LocalTextStyle.current.copy(
                        color = Color.Red,
                        textDecoration = TextDecoration.Underline,
                        textAlign = TextAlign.Center,
                        shadow = Shadow(color = Color.Red, blurRadius = 8f)
                    ),
                    onClick = { navController.navigate("create_user") }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewNeonLoginScreen() {
    DevilCasinoDemoTheme {
        // Create a dummy NavController for preview
        val navController = rememberNavController()
        Login(navController = navController)
    }
}