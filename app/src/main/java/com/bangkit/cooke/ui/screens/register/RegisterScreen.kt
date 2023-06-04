package com.bangkit.cooke.ui.screens.register

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.KeyboardArrowLeft
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.bangkit.cooke.R
import com.bangkit.cooke.ui.theme.CookETheme
import com.bangkit.cooke.ui.theme.GrayContainer
import com.bangkit.cooke.ui.theme.GrayText
import com.bangkit.cooke.ui.theme.Green80

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(navController: NavController) {

    val email = remember {
        mutableStateOf(TextFieldValue())
    }

    val username = remember {
        mutableStateOf(TextFieldValue())
    }

    val password = remember {
        mutableStateOf(TextFieldValue())
    }

    val confirmPassword = remember {
        mutableStateOf(TextFieldValue())
    }

    val showPassword = remember { mutableStateOf(false) }


    CookETheme() {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxHeight(),
                verticalArrangement = Arrangement.SpaceEvenly
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Image(
                        painter = painterResource(id = R.drawable.arrow_left_square),
                        contentDescription = "back_button",
                        modifier = Modifier
                            .padding(end = 8.dp)
                            .clickable { navController.popBackStack() })

                    Text(
                        text = "Log in",
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        color = Green80
                    )
                }

                Column() {
                    //Email
                    Text(
                        "Email Address",
                        modifier = Modifier.padding(bottom = 16.dp),
                        color = Green80,
                        fontWeight = FontWeight.SemiBold
                    )
                    TextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 20.dp),
                        maxLines = 1,
                        label = { Text("Your email address", color = GrayText) },
                        value = username.value,
                        colors = TextFieldDefaults.textFieldColors(
                            containerColor = GrayContainer,
                            textColor = GrayText,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            disabledIndicatorColor = Color.Transparent
                        ),
                        shape = RoundedCornerShape(12.dp),
                        onValueChange = { username.value = it })

                    //Username
                    Text(
                        "Username",
                        modifier = Modifier.padding(bottom = 16.dp),
                        color = Green80,
                        fontWeight = FontWeight.SemiBold
                    )
                    TextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 20.dp),
                        maxLines = 1,
                        label = { Text("Your email address", color = GrayText) },
                        value = email.value,
                        colors = TextFieldDefaults.textFieldColors(
                            containerColor = GrayContainer,
                            textColor = GrayText,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            disabledIndicatorColor = Color.Transparent
                        ),
                        shape = RoundedCornerShape(12.dp),
                        onValueChange = { email.value = it })


                    //Password
                    Text(
                        "Password",
                        modifier = Modifier.padding(bottom = 16.dp),
                        color = Green80,
                        fontWeight = FontWeight.SemiBold
                    )
                    TextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 20.dp),
                        maxLines = 1,
                        label = { Text("Your password", color = GrayText) },
                        value = password.value,
                        colors = TextFieldDefaults.textFieldColors(
                            containerColor = GrayContainer,
                            textColor = GrayText,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            disabledIndicatorColor = Color.Transparent
                        ),
                        shape = RoundedCornerShape(12.dp),
                        visualTransformation = if (showPassword.value) VisualTransformation.None else PasswordVisualTransformation(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                        trailingIcon = {
                            if (showPassword.value) {
                                IconButton(onClick = { showPassword.value = false }) {
                                    Icon(
                                        imageVector = Icons.Filled.Visibility,
                                        contentDescription = "hide_password"
                                    )

                                }
                            } else {
                                IconButton(
                                    onClick = { showPassword.value = true }) {
                                    Icon(
                                        imageVector = Icons.Filled.VisibilityOff,
                                        contentDescription = "hide_password"
                                    )
                                }

                            }
                        },
                        onValueChange = { password.value = it })

                    //Confirm Password
                    Text(
                        "Confirm Password",
                        modifier = Modifier.padding(bottom = 16.dp),
                        color = Green80,
                        fontWeight = FontWeight.SemiBold
                    )
                    TextField(
                        modifier = Modifier
                            .fillMaxWidth(),
                        maxLines = 1,
                        label = { Text("Re-type your password", color = GrayText) },
                        value = password.value,
                        colors = TextFieldDefaults.textFieldColors(
                            containerColor = GrayContainer,
                            textColor = GrayText,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            disabledIndicatorColor = Color.Transparent
                        ),
                        shape = RoundedCornerShape(12.dp),
                        visualTransformation = if (showPassword.value) VisualTransformation.None else PasswordVisualTransformation(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                        trailingIcon = {
                            if (showPassword.value) {
                                IconButton(onClick = { showPassword.value = false }) {
                                    Icon(
                                        imageVector = Icons.Filled.Visibility,
                                        contentDescription = "hide_password"
                                    )

                                }
                            } else {
                                IconButton(
                                    onClick = { showPassword.value = true }) {
                                    Icon(
                                        imageVector = Icons.Filled.VisibilityOff,
                                        contentDescription = "hide_password"
                                    )
                                }

                            }
                        },
                        onValueChange = { password.value = it })

                }
                //Login button
                Column() {
                    Button(
                        onClick = { /*TODO*/ },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 16.dp),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Green80
                        ),
                    ) {
                        Text(
                            "Sign Up",
                            fontSize = 16.sp,
                            modifier = Modifier.padding(top = 14.dp, bottom = 14.dp),
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("Already have an account?", fontSize = 14.sp)
                        TextButton(
                            onClick = { navController.popBackStack() },
                            content = {
                                Text(
                                    "Login",
                                    fontSize = 14.sp,
                                    color = Green80,
                                    textDecoration = TextDecoration.Underline
                                )
                            }

                        )

                    }
                }


            }
        }
    }

}