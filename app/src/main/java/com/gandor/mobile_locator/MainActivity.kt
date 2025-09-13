package com.gandor.mobile_locator

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gandor.mobile_locator.layers.data.config.MainActivityConfigurator
import com.gandor.mobile_locator.layers.ui.MainUi
import com.gandor.mobile_locator.layers.ui.viewmodels.DialogViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.Q)
class MainActivity : ComponentActivity() {
    val mainActivityConfigurator = MainActivityConfigurator(this)
    private val mainUi = MainUi(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainActivityConfigurator.initializeConfiguration()
        mainUi.showUi()
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onDestroy() {
        super.onDestroy()
        mainActivityConfigurator.handleClosing()
    }
}

private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
//    when(throwable) {
//        is ConnectException -> {
//
//        }
//    }

    DialogViewModel.showErrorDialog(listOf(throwable.message.toString()))

}

fun ViewModel.launchWithHandler(
    block: suspend CoroutineScope.() -> Unit
) = viewModelScope.launch(exceptionHandler) {
    block()
}