package com.cmt.secureiot.door.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cmt.secureiot.core.viewmodel.SharedState
import com.cmt.secureiot.door.domain.usecase.DoorUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DoorViewModel @Inject constructor(private  val doorUseCase: DoorUseCase,
                                        private val sharedState: SharedState,): ViewModel() {
    private val _doorsStatus = MutableStateFlow<Map<String, Boolean>>(emptyMap())
    val doorsStatus = _doorsStatus.asStateFlow()

    val securityTotalActive: StateFlow<Boolean> get() = sharedState.completeAlarmStatus

    init {
        viewModelScope.launch {
            doorUseCase.getAllDoorsStatus().collect { _doorsStatus.value = it }
        }
    }

    fun toggleDoorStatus(doorName: String, isOpen: Boolean) {
        viewModelScope.launch {
            doorUseCase.updateDoorStatus(doorName, isOpen)
        }
    }

    fun toggleAllDoorsStatus(openAll: Boolean) {
        _doorsStatus.value.keys.forEach { doorName ->
            toggleDoorStatus(doorName, openAll)
        }
    }
}