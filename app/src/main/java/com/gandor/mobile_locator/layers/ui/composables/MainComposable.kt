package com.gandor.mobile_locator.layers.ui.composables

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LifecycleEventEffect
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.gandor.mobile_locator.MainActivity
import com.gandor.mobile_locator.layers.data.constants.ConstantNumbers
import com.gandor.mobile_locator.layers.ui.composables.dialogs.ErrorDialog
import com.gandor.mobile_locator.layers.ui.composables.dialogs.SuccessDialog
import com.gandor.mobile_locator.layers.ui.composables.panels.coordinates.CoordinatesPanel
import com.gandor.mobile_locator.layers.ui.composables.panels.login.LoginPanel
import com.gandor.mobile_locator.layers.ui.composables.panels.register.RegisterPanel
import com.gandor.mobile_locator.layers.ui.composables.panels.settings.SettingsPanel
import com.gandor.mobile_locator.layers.ui.viewmodels.BaseViewModel
import com.gandor.mobile_locator.layers.ui.viewmodels.CoordinatesViewModel
import com.gandor.mobile_locator.layers.ui.viewmodels.DialogViewModel
import com.gandor.mobile_locator.layers.ui.viewmodels.LoginViewModel
import com.gandor.mobile_locator.layers.ui.viewmodels.RegisterViewModel
import com.gandor.mobile_locator.layers.ui.viewmodels.SettingsViewModel
import kotlinx.serialization.Serializable

@RequiresApi(Build.VERSION_CODES.Q)
@Composable
fun MainComposable() {
    val context = LocalContext.current
    val navController = rememberNavController()

    val registerViewModel: RegisterViewModel = viewModel()
    val coordinatesViewModel: CoordinatesViewModel = viewModel()
    val settingsViewModel: SettingsViewModel = viewModel()
    val loginViewModel: LoginViewModel = viewModel()

    val errorDialogState = DialogViewModel.errorDialogState.collectAsState()
    val successDialogState = DialogViewModel.successDialogState.collectAsState()
    val settingsState = settingsViewModel.settingsState.collectAsState().value

    settingsViewModel.registerListener(coordinatesViewModel)

    LifecycleEventEffect(Lifecycle.Event.ON_RESUME) {
        settingsViewModel.syncPermissions(context)
        settingsViewModel.syncWithSharedPreference(context)

        when(context) {
            is MainActivity -> {
                if (settingsState.isShowCoordinatesClicked) {
                    context.mainActivityConfigurator.startLocationService(context)
                }
            }
        }
    }

    LifecycleEventEffect(Lifecycle.Event.ON_PAUSE) {
        settingsViewModel.syncWithSharedPreference(context)
    }

    setNavHostControllersToBaseViewModels(
        listOf(registerViewModel, coordinatesViewModel, settingsViewModel),
        navController
    )

    Column(
        modifier = Modifier.padding(ConstantNumbers.MAIN_PADDING.dp),
    ) {
        if (errorDialogState.value.openErrorDialog) {
            ErrorDialog(DialogViewModel)
        }

        if (successDialogState.value.openSuccessDialog) {
            SuccessDialog(DialogViewModel)
        }

        NavHost(
            navController = navController,
            startDestination = if (loginViewModel.isUserLoggedInAlready()) CoordinatesPage else LoginPage,
        ) {
            composable<RegisterPage> { RegisterPanel(settingsViewModel, registerViewModel) }
            composable<CoordinatesPage> { CoordinatesPanel(settingsViewModel, coordinatesViewModel) }
            composable<SettingsPage> { SettingsPanel(settingsViewModel) }
            composable<LoginPage> { LoginPanel(loginViewModel) }
        }
    }
}

fun setNavHostControllersToBaseViewModels(baseViewModels: List<BaseViewModel>, navHostController: NavHostController) {
    baseViewModels.forEach {
        it.setNavHostController(navHostController)
    }
}