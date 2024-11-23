package com.cmt.secureiot.light.data.repository

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

interface LightRepository {
    fun getLightStatus(lightName: String): Flow<Boolean>
    suspend fun updateLightStatus(lightName: String, status: Boolean)
    fun getAllLightsStatus(): Flow<Map<String, Boolean>>
}

class LightRepositoryImpl(
    private val firebaseDatabase: FirebaseDatabase
) : LightRepository {

    override fun getLightStatus(lightName: String): Flow<Boolean> = callbackFlow {
        val lightRef = firebaseDatabase.reference.child("leds/$lightName")
        val listener = lightRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val status = snapshot.getValue(String::class.java) == "on"
                trySend(status)
            }

            override fun onCancelled(error: DatabaseError) {
                close(error.toException())
            }
        })
        awaitClose { lightRef.removeEventListener(listener) }
    }

    override suspend fun updateLightStatus(lightName: String, status: Boolean) {
        val lightRef = firebaseDatabase.reference.child("leds/$lightName")
        lightRef.setValue(if (status) "on" else "off").await()
    }

    override fun getAllLightsStatus(): Flow<Map<String, Boolean>> = callbackFlow {
        val lightsRef = firebaseDatabase.reference.child("leds")
        val listener = lightsRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val statusMap = snapshot.children.associate {
                    it.key!! to (it.getValue(String::class.java) == "on")
                }
                trySend(statusMap)
            }

            override fun onCancelled(error: DatabaseError) {
                close(error.toException())
            }
        })
        awaitClose { lightsRef.removeEventListener(listener) }
    }
}