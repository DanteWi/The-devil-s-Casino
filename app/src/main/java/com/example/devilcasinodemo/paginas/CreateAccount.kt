package com.example.devilcasinodemo.paginas

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.devilcasinodemo.R
import com.example.devilcasinodemo.ui.theme.DevilCasinoDemoTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateAccountScreen(navController: NavHostController) {
    val neonOrange = Color(0xFFFF6600)
    val backgroundColor = Color(0xFF0C0602)
    var user by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var rpassword by remember { mutableStateOf("") }
    var checked by remember { mutableStateOf(false) }
    var showDialog by remember { mutableStateOf(false) }
    var userError by remember { mutableStateOf(false) }
    var emailError by remember { mutableStateOf(false) }
    var passwordError by remember { mutableStateOf(false) }
    var rpasswordError by remember { mutableStateOf(false) }

    val emailRegex = Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$")
    val formIsValid = !userError && !emailError && !passwordError && !rpasswordError && checked && user.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty() && rpassword.isNotEmpty()


    Surface(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor),
        color = backgroundColor
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(

                painter = painterResource(id = R.drawable.chatgpt_image_10_nov__2025__16_07_21),
                contentDescription = "Login Sign",
                modifier = Modifier
                    .padding(1.dp)
                    .width(200.dp),
                contentScale = ContentScale.Fit
            )

            Spacer(modifier = Modifier.height(32.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(BorderStroke(3.dp, neonOrange), RoundedCornerShape(16.dp))
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // User name
                OutlinedTextField(
                    value = user,
                    onValueChange = {
                        user = it
                        userError = user.isBlank()
                    },
                    label = { Text("User Name", color = neonOrange) },
                    textStyle = LocalTextStyle.current.copy(color = neonOrange),
                    isError = userError,
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = neonOrange,
                        unfocusedBorderColor = neonOrange,
                        cursorColor = neonOrange,
                        errorBorderColor = Color.Red
                    ),
                    modifier = Modifier.fillMaxWidth()
                )

                if (userError) {
                    Text(
                        text = "El usuario no puede estar vacío",
                        color = Color.Red,
                        modifier = Modifier.align(Alignment.Start)
                            .padding(start = 8.dp, top = 4.dp)
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Emai
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
                        modifier = Modifier.align(Alignment.Start)
                            .padding(start = 8.dp, top = 4.dp)
                    )
                }


                Spacer(modifier = Modifier.height(16.dp))
                // Password
                OutlinedTextField(
                    value = password,
                    onValueChange = {
                        password = it
                        passwordError = password.length < 6
                    },
                    label = { Text("Password", color = neonOrange) },
                    textStyle = LocalTextStyle.current.copy(color = neonOrange),
                    visualTransformation = PasswordVisualTransformation(),
                    isError = passwordError,
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = neonOrange,
                        unfocusedBorderColor = neonOrange,
                        cursorColor = neonOrange,
                        errorBorderColor = Color.Red
                    ),
                    modifier = Modifier.fillMaxWidth()
                )

                if (passwordError) {
                    Text(
                        text = "La contraseña debe tener al menos 6 caracteres",
                        color = Color.Red,
                        modifier = Modifier.align(Alignment.Start)
                            .padding(start = 8.dp, top = 4.dp)
                    )
                }


                Spacer(modifier = Modifier.height(16.dp))

                //Password repeated
                OutlinedTextField(
                    value = rpassword,
                    onValueChange = {
                        rpassword = it
                        rpasswordError = rpassword != password
                    },
                    label = { Text("Repeat Password", color = neonOrange) },
                    textStyle = LocalTextStyle.current.copy(color = neonOrange),
                    visualTransformation = PasswordVisualTransformation(),
                    isError = rpasswordError,
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = neonOrange,
                        unfocusedBorderColor = neonOrange,
                        cursorColor = neonOrange,
                        errorBorderColor = Color.Red
                    ),
                    modifier = Modifier.fillMaxWidth()
                )

                if (rpasswordError) {
                    Text(
                        text = "Las contraseñas no coinciden",
                        color = Color.Red,
                        modifier = Modifier.align(Alignment.Start)
                            .padding(start = 8.dp, top = 4.dp)
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                Button(
                    onClick = { /* create account */ },
                    colors = ButtonDefaults.buttonColors(containerColor = neonOrange),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    enabled = formIsValid
                ) {
                    Text("Create Account", color = Color.Black, fontWeight = FontWeight.Bold)
                }

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Checkbox(
                        checked = checked,
                        onCheckedChange = { checked = it }
                    )

                    ClickableText(
                        text = buildAnnotatedString {
                            withStyle(
                                style = SpanStyle(
                                    color = Color.White, // white text
                                    fontSize = 14.sp // optional: match theme font size
                                )
                            ) {
                                append("Terms and Conditions")
                            }
                        },
                        modifier = Modifier.padding(start = 8.dp),
                        onClick = { showDialog = true } // 👈 open popup
                    )
                }

                if (showDialog) {
                    androidx.compose.ui.window.Dialog(
                        onDismissRequest = { showDialog = false }
                    ) {
                        Surface(
                            color = backgroundColor,
                            shape = RoundedCornerShape(12.dp),
                            border = BorderStroke(2.dp, neonOrange),
                            modifier = Modifier
                                .padding(16.dp)
                                .fillMaxWidth()
                                .fillMaxHeight(0.9f)
                        ) {
                            // Add vertical scroll
                            val scrollState = rememberScrollState()

                            Column(
                                modifier = Modifier
                                    .padding(20.dp)
                                    .fillMaxSize()
                                    .verticalScroll(scrollState), // 👈 makes content scrollable
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    text = "Terms and Conditions",
                                    color = neonOrange,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 20.sp
                                )

                                Spacer(modifier = Modifier.height(16.dp))

                                Text(
                                    text = stringResource(R.string.terms),
                                    color = Color.White,
                                    fontSize = 14.sp,
                                    lineHeight = 20.sp
                                )

                                Spacer(modifier = Modifier.height(16.dp))

                                Button(
                                    onClick = { showDialog = false },
                                    colors = ButtonDefaults.buttonColors(containerColor = neonOrange),
                                    shape = RoundedCornerShape(8.dp)
                                ) {
                                    Text("Close", color = Color.Black, fontWeight = FontWeight.Bold)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun Previewform2() {
    DevilCasinoDemoTheme {
        // Create a dummy NavController for preview
        val navController = rememberNavController()
        CreateAccountScreen(navController = navController)
    }
}