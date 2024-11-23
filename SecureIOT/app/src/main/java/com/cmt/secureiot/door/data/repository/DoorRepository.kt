package com.cmt.secureiot.door.data.repository

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

interface DoorRepository {
    fun getDoorStatus(doorName: String): Flow<Boolean>
    suspend fun updateDoorStatus(lightName: String, status: Boolean)
    fun getAllDoorsStatus(): Flow<Map<String, Boolean>>
}

class DoorRepositoryImpl(private val firebaseDatabase: FirebaseDatabase): DoorRepository {
    override fun getDoorStatus(doorName: String): Flow<Boolean> = callbackFlow {
        val doorRef = firebaseDatabase.getReference("servos/$doorName")
        val listener = doorRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val isOpen = snapshot.getValue(Boolean::class.java) ?: false
                trySend(isOpen)
            }

            override fun onCancelled(error: DatabaseError) {
                close(error.toException())
            }
        })
        awaitClose { doorRef.removeEventListener(listener) }
    }

    override suspend fun updateDoorStatus(doorName: String, status: Boolean) {
        firebaseDatabase.getReference("servos/$doorName").setValue(status).await()
    }

    override fun getAllDoorsStatus(): Flow<Map<String, Boolean>> = callbackFlow {
        val doorsRef = firebaseDatabase.getReference("servos")
        val listener = doorsRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val doorsStatus = snapshot.children.associate {
                    it.key.orEmpty() to (it.getValue(Boolean::class.java) ?: false)
                }
                trySend(doorsStatus)
            }

            override fun onCancelled(error: DatabaseError) {
                close(error.toException())
            }
        })
        awaitClose { doorsRef.removeEventListener(listener) }
    }
}