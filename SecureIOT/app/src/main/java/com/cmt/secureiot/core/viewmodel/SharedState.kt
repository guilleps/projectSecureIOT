package com.cmt.secureiot.core.viewmodel

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SharedState @Inject constructor() {
    private val _completeAlarmStatus = MutableStateFlow(false)
    val completeAlarmStatus: StateFlow<Boolean> get() = _completeAlarmStatus

    fun setCompleteAlarmStatus(status: Boolean) {
        _completeAlarmStatus.value = status
    }
}