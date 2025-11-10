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
import com.example.devilcasinodemo.R
import com.example.devilcasinodemo.ui.theme.DevilCasinoDemoTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateAccountScreen() {
    val neonOrange = Color(0xFFFF6600)
    val backgroundColor = Color(0xFF0C0602)
    var user by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var rpasword by remember { mutableStateOf("") }
    var checked by remember { mutableStateOf(false) }
    var showDialog by remember { mutableStateOf(false) }

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor),
        color = backgroundColor
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
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

                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("User Name", color = neonOrange) },
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

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    label = { Text("Repeat Password", color = neonOrange) },
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
        CreateAccountScreen()
    }
}