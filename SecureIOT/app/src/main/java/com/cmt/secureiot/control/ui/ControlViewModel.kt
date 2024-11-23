package com.cmt.secureiot.control.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cmt.secureiot.control.data.service.LogService
import com.cmt.secureiot.control.data.service.response.LogEntry
import com.cmt.secureiot.control.domain.usecase.ControlUseCase
import com.cmt.secureiot.core.viewmodel.SharedState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ControlViewModel @Inject constructor(
    private val controlUseCase: ControlUseCase,
    private val sharedState: SharedState
) : ViewModel() {
    val completeAlarmStatus: StateFlow<Boolean> = sharedState.completeAlarmStatus

    private val _logs = MutableStateFlow<List<LogEntry>>(emptyList())
    val logs: StateFlow<List<LogEntry>> = _logs

    fun toggleCompleteAlarmStatus() {
        val newStatus = !sharedState.completeAlarmStatus.value
        sharedState.setCompleteAlarmStatus(newStatus)

        // Launch coroutine to call suspend function
        viewModelScope.launch {
            controlUseCase.updateCompleteAlarmStatus(newStatus)
            val action = if (newStatus) "Se activó el sistema" else "Se desactivó el sistema"
            LogService.addLog("Seguridad Total", "", action)
            loadLogs()
        }
    }

    fun clearLogs() {
        LogService.clearLogs()
        loadLogs()
    }

    private fun loadLogs() {
        _logs.value = LogService.getLogs()
    }
}