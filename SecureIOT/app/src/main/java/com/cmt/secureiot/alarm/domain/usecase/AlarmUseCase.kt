package com.cmt.secureiot.alarm.domain.usecase

import com.cmt.secureiot.alarm.data.repository.AlarmRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AlarmUseCase @Inject constructor(private val alarmRepository: AlarmRepository) {
    fun getAlarmStatus(): Flow<Boolean> = alarmRepository.getAlarmStatus()
    suspend fun updateAlarmStatus(status: Boolean) = alarmRepository.updateAlarmStatus(status)
}