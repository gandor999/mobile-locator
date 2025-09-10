package com.gandor.mobile_locator.layers.ui.composables.panels.settings

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.gandor.mobile_locator.layers.data.constants.ConstantStrings
import com.gandor.mobile_locator.layers.data.managers.PermissionManager
import com.gandor.mobile_locator.layers.ui.composables.panels.buttons.BackArrowButton
import com.gandor.mobile_locator.layers.ui.viewmodels.SettingsViewModel

@RequiresApi(Build.VERSION_CODES.Q)
@SuppressLint("ContextCastToActivity")
@Composable
fun SettingsPanel(
    settingsViewModel: SettingsViewModel,
) {
    val context = LocalContext.current
    val settingsState  = settingsViewModel.settingsState.collectAsState().value

    Column {
        BackArrowButton(
            onBackClick = {
                settingsViewModel.switchBackToRecentPanel()
            }
        )

        LazyColumn(
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start,
            modifier = Modifier.fillMaxSize()
        ) {
            item {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth().padding(8.dp)
                ) {
                    Text(text = ConstantStrings.CoordinatesConstants.EMIT_LOCATION)

                    Switch(
                        checked = settingsState.isShowCoordinatesClicked,
                        onCheckedChange = { isChecked ->
//                            settingsViewModel.switchForegroundLocationEmit(context, isChecked)

                            if (!PermissionManager.isFineOrCourseGrainedPermissionGranted(context)) {
                                PermissionManager.promptRequiredPermissions(context)
                                return@Switch
                            }

                            settingsViewModel.setIsShowCoordinatesClicked(context, isChecked)
                        }
                    )
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth().padding(8.dp)
                ) {
                    Text(text = ConstantStrings.ALWAYS_EMIT_LOCATION)

                    Switch(
                        checked = settingsState.isBackgroundLocationTurnedOn,
                        onCheckedChange = { isChecked ->
                            settingsViewModel.switchBackgroundLocationEmit(context, isChecked)
                        }
                    )
                }
            }
        }
    }
}