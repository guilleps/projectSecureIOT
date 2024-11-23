package com.cmt.secureiot.alarm.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cmt.secureiot.alarm.domain.usecase.AlarmUseCase
import com.cmt.secureiot.core.viewmodel.SharedState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AlarmViewModel @Inject constructor(
    private val alarmUseCase: AlarmUseCase,
    private val sharedState: SharedState,
) : ViewModel() {
    private val _alarmStatus = MutableStateFlow(false)
    val alarmStatus: StateFlow<Boolean> = _alarmStatus

    val securityTotalActive: StateFlow<Boolean> get() = sharedState.completeAlarmStatus

    init {
        viewModelScope.launch {
            alarmUseCase.getAlarmStatus().collect { status ->
                _alarmStatus.value = status
            }
        }
    }

    fun toggleAlarmStatus() {
        viewModelScope.launch {
            val newStatus = !_alarmStatus.value
            alarmUseCase.updateAlarmStatus(newStatus)
            _alarmStatus.value = newStatus
        }
    }
}