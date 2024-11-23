package com.cmt.secureiot.login.data.repository

import com.cmt.secureiot.login.domain.model.Result
import com.cmt.secureiot.login.domain.repository.AuthRepository
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AuthRepositoryImpl @Inject  constructor(
    private val firebaseAuth: FirebaseAuth
) : AuthRepository {
    override suspend fun login(email: String, password: String): Result<Unit> {
        return try {
            val result = firebaseAuth.signInWithEmailAndPassword(email, password).await()
            if (result.user != null) {
                Result.Success(Unit)
            } else {
                Result.Error(Exception("User not found").toString())
            }
        } catch (e: Exception) {
            Result.Error(e.toString())
        }
    }
}