package com.gandor.mobile_locator.layers.ui.composables.panels.login

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gandor.mobile_locator.layers.data.constants.ConstantNumbers
import com.gandor.mobile_locator.layers.data.constants.ConstantStrings
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

        Text(
            text = ConstantStrings.LoginConstants.LOGIN,
            fontSize = ConstantNumbers.PANEL_NAME_FONT_SIZE.sp
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
            Text(ConstantStrings.ButtonTextConstants.SUBMIT)
        }
    }
}