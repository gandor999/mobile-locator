package com.gandor.mobile_locator.layers.ui.viewmodels

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import androidx.annotation.RequiresApi
import com.gandor.mobile_locator.layers.data.constants.ConstantStrings
import com.gandor.mobile_locator.layers.ui.viewmodels.interfaces.Listener
import com.gandor.mobile_locator.layers.ui.viewmodels.interfaces.Notifier
import com.gandor.mobile_locator.layers.ui.viewmodels.states.SettingsState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import androidx.core.content.edit
import com.gandor.mobile_locator.layers.data.managers.PermissionManager

class SettingsViewModel: BaseViewModel(), Notifier {
    private val _settingsState = MutableStateFlow(SettingsState())
    val settingsState = _settingsState.asStateFlow()

    private val listeners: MutableList<Listener> = mutableListOf()

    fun getCurrentMapForKeyNameToStateBooleans(): HashMap<String, Boolean> {
        // important to get on current state instead of init as private val
        return hashMapOf(
            SettingsState.Companion.SHARED_PREFERENCE_KEYS.IS_SHOW_COORDINATES_CLICKED to _settingsState.value.isShowCoordinatesClicked,
            SettingsState.Companion.SHARED_PREFERENCE_KEYS.IS_BACKGROUND_LOCATION_TURNED_ON to _settingsState.value.isBackgroundLocationTurnedOn
        )
    }

    fun cacheToSharedPreferences(context: Context) {
        val sharedPreferenceMap = ConstantStrings.SharedPreferenceMapName.SETTINGS
        val sharedPreferences = context.getSharedPreferences(sharedPreferenceMap.first, sharedPreferenceMap.second)

        sharedPreferences.edit {
            getCurrentMapForKeyNameToStateBooleans().forEach {
                putBoolean(
                    it.key,
                    it.value
                )
            }

            apply()
        }
    }

    fun syncWithSharedPreference(context: Context) {
        val sharedPreferenceMap = ConstantStrings.SharedPreferenceMapName.SETTINGS
        val sharedPreferences = context.getSharedPreferences(sharedPreferenceMap.first, sharedPreferenceMap.second)

        setIsShowCoordinatesClicked(
            context,
            sharedPreferences.getBoolean(
                SettingsState.Companion.SHARED_PREFERENCE_KEYS.IS_SHOW_COORDINATES_CLICKED,
                _settingsState.value.isShowCoordinatesClicked
            )
        )

        setIsBackgroundLocationTurnedOn(
            context,
            sharedPreferences.getBoolean(
                SettingsState.Companion.SHARED_PREFERENCE_KEYS.IS_BACKGROUND_LOCATION_TURNED_ON,
                _settingsState.value.isBackgroundLocationTurnedOn
            )
        )
    }

    override fun registerListener(listener: Listener) {
        listeners.add(listener)
    }

    override fun notifyListeners(context: Context?) {
        context?.let {
            listeners.forEach {
                it.consumeEvent(_settingsState.value, context)
            }
        }
    }

    @SuppressLint("MissingPermission")
    fun setIsShowCoordinatesClicked(context: Context, clicked: Boolean) {
        _settingsState.value =
            _settingsState.value.copy(isShowCoordinatesClicked = clicked)

        cacheToSharedPreferences(context)
        notifyListeners(context)
    }

    fun setIsBackgroundLocationTurnedOn(context: Context, bool: Boolean) {
        _settingsState.value =
            _settingsState.value.copy(isBackgroundLocationTurnedOn = bool)

        cacheToSharedPreferences(context)
        notifyListeners(context)
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    fun syncWithPermissionsOnResume(context: Context) {
        val isBackgroundLocationPermissionGranted = PermissionManager.isBackgroundLocationGranted(context)

        setIsBackgroundLocationTurnedOn(
            context,
            isBackgroundLocationPermissionGranted
        )

        if (!isBackgroundLocationPermissionGranted && !PermissionManager.isFineOrCourseGrainedPermissionGranted(context)) {
            setIsShowCoordinatesClicked(context, false)
        }
    }

    fun switchBackgroundLocationEmit(context: Context, isChecked: Boolean) {
        (context as? Activity)?.let {
            if (isChecked) {
                PermissionManager.promptBackgroundLocation(it)
            } else {
                PermissionManager.openAppPermissionSettings(it)
            }
        }
    }
}