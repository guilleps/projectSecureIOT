package com.cmt.secureiot.login.domain.usecase

import com.cmt.secureiot.login.domain.model.Result
import com.cmt.secureiot.login.domain.repository.AuthRepository
import javax.inject.Inject

class LoginUseCase @Inject constructor(private val authRepository: AuthRepository) {
    suspend operator fun invoke(email: String, password: String): Result<Unit> {
        return authRepository.login(email, password)
    }
}