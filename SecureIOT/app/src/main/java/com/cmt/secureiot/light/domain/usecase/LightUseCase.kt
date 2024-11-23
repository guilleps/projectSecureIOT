package com.cmt.secureiot.light.domain.usecase

import com.cmt.secureiot.light.data.repository.LightRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LightUseCase @Inject constructor(private val lightRepository: LightRepository) {

    fun getLightStatus(lightName: String): Flow<Boolean> {
        return lightRepository.getLightStatus(lightName)
    }

    suspend fun updateLightStatus(lightName: String, status: Boolean) {
        lightRepository.updateLightStatus(lightName, status)
    }

    fun getAllLightsStatus(): Flow<Map<String, Boolean>> {
        return lightRepository.getAllLightsStatus()
    }
}