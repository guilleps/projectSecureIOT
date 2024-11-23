package com.cmt.secureiot.light.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cmt.secureiot.core.viewmodel.SharedState
import com.cmt.secureiot.light.domain.usecase.LightUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LightViewModel @Inject constructor(
    private val lightUseCase: LightUseCase,
    private val sharedState: SharedState,
) : ViewModel() {

    private val _lightsStatus = MutableStateFlow<Map<String, Boolean>>(emptyMap())
    val lightsStatus = _lightsStatus.asStateFlow()

    val securityTotalActive: StateFlow<Boolean> get() = sharedState.completeAlarmStatus

    init {
        getAllLightsStatus()
    }

    private fun getAllLightsStatus() {
        viewModelScope.launch {
            lightUseCase.getAllLightsStatus().collect { statusMap ->
                _lightsStatus.value = statusMap
            }
        }
    }

    fun toggleLightStatus(lightName: String, isOn: Boolean) {
        viewModelScope.launch {
            lightUseCase.updateLightStatus(lightName, isOn)
        }
    }

    fun toggleAllLights(on: Boolean) {
        viewModelScope.launch {
            _lightsStatus.value.keys.forEach { lightName ->
                lightUseCase.updateLightStatus(lightName, on)
            }
        }
    }
}