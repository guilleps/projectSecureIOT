package com.cmt.secureiot.control.data.repository

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

interface ControlRepository {
    fun getCompleteAlarmStatus(): Flow<Boolean>
    suspend fun updateCompleteAlarmStatus(status: Boolean)
}

class ControlRepositoryImpl(private val firebaseDatabase: FirebaseDatabase): ControlRepository {

    override fun getCompleteAlarmStatus(): Flow<Boolean> = callbackFlow {
        val alarmCompletaRef = firebaseDatabase.getReference("alarma_completa")
        val listener = alarmCompletaRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                trySend(snapshot.getValue(Boolean::class.java) == true)
            }

            override fun onCancelled(error: DatabaseError) {
                close(error.toException())
            }
        })
        awaitClose { alarmCompletaRef.removeEventListener(listener) }
    }

    override suspend fun updateCompleteAlarmStatus(status: Boolean) {
        firebaseDatabase.getReference("alarma_completa").setValue(status).await()
    }
}