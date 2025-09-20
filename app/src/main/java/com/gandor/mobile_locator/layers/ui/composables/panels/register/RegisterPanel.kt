package com.gandor.mobile_locator.layers.ui.composables.panels.register

import android.annotation.SuppressLint
import android.provider.Settings
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gandor.mobile_locator.layers.data.constants.ConstantNumbers
import com.gandor.mobile_locator.layers.data.constants.ConstantStrings
import com.gandor.mobile_locator.layers.ui.composables.LoginPage
import com.gandor.mobile_locator.layers.ui.composables.RegisterPage
import com.gandor.mobile_locator.layers.ui.viewmodels.RegisterViewModel
import com.gandor.mobile_locator.layers.ui.viewmodels.SettingsViewModel

// TODO: add to privacy policy about location sharing and android id
@SuppressLint("HardwareIds")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterPanel(
    settingsViewModel: SettingsViewModel,
    registerViewModel: RegisterViewModel,
) {
    val registerState = registerViewModel.registerState.collectAsState()
    val baseRegisterState = registerViewModel.baseState.collectAsState()

    val context = LocalContext.current
    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        if (baseRegisterState.value.isLoading) {
            CircularProgressIndicator()
            return
        }

        Scaffold(
            content = { paddingValues ->
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = ConstantStrings.RegistrationConstants.REGISTER,
                        fontSize = ConstantNumbers.PANEL_NAME_FONT_SIZE.sp
                    )

                    Spacer(modifier = Modifier.height((ConstantNumbers.SPACER_HEIGHT * 2).dp))

                    TextField(
                        value = registerState.value.username,
                        onValueChange = {
                            registerViewModel.setUsername(it)
                        },
                        placeholder = { Text(ConstantStrings.PlaceHolderEnum.USERNAME.value) }
                    )

                    Spacer(modifier = Modifier.height(ConstantNumbers.SPACER_HEIGHT.dp))

                    TextField(
                        value = registerState.value.password,
                        onValueChange = {
                            registerViewModel.setPassword(it)
                        },
                        placeholder = { Text(ConstantStrings.PlaceHolderEnum.PASSWORD.value) }
                    )

                    Spacer(modifier = Modifier.height(ConstantNumbers.SPACER_HEIGHT.dp))

                    TextField(
                        value = registerState.value.email,
                        onValueChange = {
                            registerViewModel.setEmail(it)
                        },
                        placeholder = { Text(ConstantStrings.PlaceHolderEnum.EMAIL.value) }
                    )

                    Spacer(modifier = Modifier.height((ConstantNumbers.SPACER_HEIGHT * 2).dp))

                    Button(
                        onClick = {
                            registerViewModel.setAndroidId(
                                Settings.Secure.getString(
                                    context.contentResolver,
                                    Settings.Secure.ANDROID_ID
                                )
                            )
                            registerViewModel.submit()
                        }
                    ) {
                        Text(ConstantStrings.ButtonTextConstants.SUBMIT)
                    }
                }
            },
            bottomBar = {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(ConstantStrings.LoginConstants.ALREADY_HAVE_AN_ACCOUNT + " ")
                    Text(
                        text = ConstantStrings.LoginConstants.LOGIN,
                        modifier = Modifier.clickable(
                            onClick = {
                                registerViewModel.switchPages(LoginPage)
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