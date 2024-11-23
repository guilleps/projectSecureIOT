package com.cmt.secureiot.login.domain.repository

import com.cmt.secureiot.login.domain.model.Result

interface AuthRepository {
    suspend fun login(email: String, password: String): Result<Unit>
}
