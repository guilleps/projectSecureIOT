package com.cmt.secureiot.alarm.data.repository

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

interface AlarmRepository {
    fun getAlarmStatus(): Flow<Boolean>
    suspend fun updateAlarmStatus(status: Boolean)
}

class AlarmRepositoryImpl(private val firebaseDatabase: FirebaseDatabase): AlarmRepository {
    override fun getAlarmStatus(): Flow<Boolean> = callbackFlow {
        val alarmRef = firebaseDatabase.getReference("alarma")
        val listener = alarmRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                trySend(snapshot.getValue(Boolean::class.java) == true)
            }
            override fun onCancelled(error: DatabaseError) {
                close(error.toException())
            }
        })
        awaitClose { alarmRef.removeEventListener(listener) }
    }

    override suspend fun updateAlarmStatus(status: Boolean) {
        firebaseDatabase.getReference("alarma").setValue(status).await()
    }
}