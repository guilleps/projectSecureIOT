package com.cmt.secureiot.door.domain.usecase

import com.cmt.secureiot.door.data.repository.DoorRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DoorUseCase @Inject constructor(private val doorRepository: DoorRepository) {
    fun getDoorStatus(doorName: String): Flow<Boolean> = doorRepository.getDoorStatus(doorName)
    suspend fun updateDoorStatus(doorName: String, status: Boolean) =
        doorRepository.updateDoorStatus(doorName, status)

    fun getAllDoorsStatus(): Flow<Map<String, Boolean>> = doorRepository.getAllDoorsStatus()
}