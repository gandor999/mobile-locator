package com.gandor.mobile_locator.layers.ui.composables.panels.register

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gandor.mobile_locator.layers.data.constants.ConstantNumbers
import com.gandor.mobile_locator.layers.data.constants.ConstantStrings
import com.gandor.mobile_locator.layers.ui.viewmodels.RegisterViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterPanel(
    registerViewModel: RegisterViewModel,
) {
    val registerState = registerViewModel.registerState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize(),
//            .border(2.dp, Color.Red),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = ConstantStrings.REGISTER,
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
                registerViewModel.submit()
            }
        ) {
            Text(ConstantStrings.SUBMIT)
        }
    }
}