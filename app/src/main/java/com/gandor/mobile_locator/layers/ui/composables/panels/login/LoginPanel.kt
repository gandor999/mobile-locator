package com.gandor.mobile_locator.layers.ui.composables.panels.login

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gandor.mobile_locator.layers.data.constants.ConstantNumbers
import com.gandor.mobile_locator.layers.data.constants.ConstantStrings
import com.gandor.mobile_locator.layers.ui.composables.RegisterPage
import com.gandor.mobile_locator.layers.ui.viewmodels.LoginViewModel

@SuppressLint("HardwareIds")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginPanel(
    loginViewModel: LoginViewModel
) {
    val context = LocalContext.current
    val loginState = loginViewModel.loginState.collectAsState().value
    val baseLoginState = loginViewModel.baseState.collectAsState().value

    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        if (baseLoginState.isLoading) {
            CircularProgressIndicator()
            return
        }

        Scaffold(
            content = { paddingValues ->
                Column(
                    modifier = Modifier.padding(paddingValues).fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = ConstantStrings.LoginConstants.LOGIN,
                        fontSize = ConstantNumbers.PANEL_NAME_FONT_SIZE.sp,
                    )

                    Spacer(modifier = Modifier.height((ConstantNumbers.SPACER_HEIGHT * 2).dp))

                    TextField(
                        value = loginState.usernameOrEmail,
                        onValueChange = { loginViewModel.setUsernameOrEmail(it) },
                        placeholder = { Text("${ConstantStrings.PlaceHolderEnum.USERNAME.value} / ${ConstantStrings.PlaceHolderEnum.EMAIL.value}") }
                    )

                    Spacer(modifier = Modifier.height(ConstantNumbers.SPACER_HEIGHT.dp))

                    TextField(
                        value = loginState.password,
                        onValueChange = { loginViewModel.setPassword(it) },
                        placeholder = { Text(ConstantStrings.PlaceHolderEnum.PASSWORD.value) }
                    )

                    Spacer(modifier = Modifier.height(ConstantNumbers.SPACER_HEIGHT.dp))

                    Button(
                        onClick = {
                            loginViewModel.loginConventionally(context)
                        }
                    ) {
                        Text(ConstantStrings.LoginConstants.SUBMIT)
                    }


                }
            },
            bottomBar = {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(ConstantStrings.RegistrationConstants.DONT_HAVE_AN_ACCOUNT + " ")
                        Text(
                            text = ConstantStrings.RegistrationConstants.REGISTER,
                            modifier = Modifier.clickable(
                                onClick = {
                                    loginViewModel.switchPages(RegisterPage)
                                }
                            ),
                            style = TextStyle(textDecoration = TextDecoration.Underline),
                            color = Color.Blue
                        )
                    }

                    Text(
                        text = "Forgot username or password",
                        modifier = Modifier.clickable(
                            onClick = {
                                Log.d("GEO TEST", "Forgot username or password was clicked")
                            }
                        ),
                        style = TextStyle(textDecoration = TextDecoration.Underline),
                        color = Color.Blue
                    )
                }
            }
        )
    }
}