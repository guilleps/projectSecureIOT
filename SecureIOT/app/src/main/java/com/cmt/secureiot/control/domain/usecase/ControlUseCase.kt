package com.cmt.secureiot.control.domain.usecase

import com.cmt.secureiot.control.data.repository.ControlRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ControlUseCase @Inject constructor(private val controlRepository: ControlRepository) {
    fun getCompleteAlarmStatus(): Flow<Boolean> = controlRepository.getCompleteAlarmStatus()
    suspend fun updateCompleteAlarmStatus(status: Boolean) = controlRepository.updateCompleteAlarmStatus(status)
}