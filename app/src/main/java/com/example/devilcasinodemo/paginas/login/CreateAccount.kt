package com.example.devilcasinodemo.paginas.login

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.devilcasinodemo.R
import com.example.devilcasinodemo.mvc.RegisterViewModel
import com.example.devilcasinodemo.ui.theme.CasinoFont

@Composable
fun CreateAccountScreen(
    navController: NavHostController,
    viewModel: RegisterViewModel = viewModel()
) {
    val neonOrange = Color(0xFFFF6600)
    val backgroundColor = Color(0xFF0C0602)

    var user by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var rpassword by remember { mutableStateOf("") }
    var checked by remember { mutableStateOf(false) }

    var showDialog by remember { mutableStateOf(false) }
    var message by remember { mutableStateOf("") }
    var isSuccess by remember { mutableStateOf(false) }

    var userError by remember { mutableStateOf(false) }
    var emailError by remember { mutableStateOf(false) }
    var passwordError by remember { mutableStateOf(false) }
    var rpasswordError by remember { mutableStateOf(false) }

    val emailRegex = Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$")

    val formIsValid =
        !userError && !emailError && !passwordError && !rpasswordError &&
                checked && user.isNotBlank() && email.isNotBlank() &&
                password.isNotBlank() && rpassword.isNotBlank()

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = backgroundColor
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Image(
                painter = painterResource(id = R.drawable.chatgpt_image_10_nov__2025__16_07_21),
                contentDescription = null,
                modifier = Modifier.width(200.dp),
                contentScale = ContentScale.Fit
            )

            Spacer(modifier = Modifier.height(32.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(BorderStroke(3.dp, neonOrange), RoundedCornerShape(16.dp))
                    .padding(24.dp)
            ) {

                OutlinedTextField(
                    value = user,
                    onValueChange = {
                        user = it
                        userError = it.isBlank()
                    },
                    label = { Text(stringResource(R.string.user_name), color = neonOrange) },
                    isError = userError,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = email,
                    onValueChange = {
                        email = it
                        emailError = !emailRegex.matches(it)
                    },
                    label = { Text(stringResource(R.string.email), color = neonOrange) },
                    isError = emailError,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = password,
                    onValueChange = {
                        password = it
                        passwordError = it.length < 6
                    },
                    label = { Text(stringResource(R.string.password), color = neonOrange) },
                    visualTransformation = PasswordVisualTransformation(),
                    isError = passwordError,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = rpassword,
                    onValueChange = {
                        rpassword = it
                        rpasswordError = it != password
                    },
                    label = { Text(stringResource(R.string.repeat_password), color = neonOrange) },
                    visualTransformation = PasswordVisualTransformation(),
                    isError = rpasswordError,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(16.dp))

                if (message.isNotEmpty()) {
                    Text(
                        text = message,
                        color = if (isSuccess) Color.Green else Color.Red,
                        fontSize = 14.sp
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }

                Button(
                    onClick = {
                        message = R.string.creating_account.toString()
                        viewModel.register(user, email, password) { success, result ->
                            isSuccess = success
                            message = result ?: ""

                            if (success) {
                                navController.navigate("login") {
                                    popUpTo("create_user") { inclusive = true }
                                }
                            }
                        }
                    },
                    enabled = formIsValid,
                    colors = ButtonDefaults.buttonColors(containerColor = neonOrange),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                ) {
                    Text(stringResource(R.string.create_account), color = Color.Black)
                }

                Spacer(modifier = Modifier.height(12.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Checkbox(checked = checked, onCheckedChange = { checked = it })
                    Spacer(modifier = Modifier.width(8.dp))
                    ClickableText(
                        text = buildAnnotatedString {
                            withStyle(
                                SpanStyle(
                                    color = Color.White,
                                    fontFamily = CasinoFont,
                                    fontSize = 14.sp
                                )
                            ) {
                                append(stringResource(R.string.terms_and_conditions2))
                            }
                        },
                        onClick = { showDialog = true }
                    )
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
                                Column(
                                    modifier = Modifier
                                        .padding(20.dp)
                                        .verticalScroll(rememberScrollState()),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {

                                    Text(
                                        text = stringResource(R.string.terms_and_conditions),
                                        color = neonOrange,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 20.sp
                                    )

                                    Spacer(modifier = Modifier.height(16.dp))

                                    Text(
                                        text = stringResource(R.string.terms),
                                        lineHeight = 20.sp,
                                        style = MaterialTheme.typography.labelLarge.copy(
                                            fontFamily = CasinoFont),
                                        fontSize = 15.sp
                                    )

                                    Spacer(modifier = Modifier.height(16.dp))

                                    Button(
                                        onClick = { showDialog = false },
                                        colors = ButtonDefaults.buttonColors(containerColor = neonOrange)
                                    ) {
                                        Text(
                                            "Close",
                                            color = Color.Black,
                                            fontWeight = FontWeight.Bold,
                                                    style = MaterialTheme.typography.labelLarge.copy(
                                                    fontFamily = CasinoFont),
                                            fontSize = 15.sp
                                        )
                                    }
                                }
                            }
                        }
                    }

                }
            }
        }
    }
}

