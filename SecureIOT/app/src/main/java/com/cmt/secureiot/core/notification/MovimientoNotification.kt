package com.cmt.secureiot.core.notification

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class MovimientoViewModel : ViewModel() { // por terminar
    private val database = FirebaseDatabase.getInstance()
    private val notificacionesRef = database.getReference("notificaciones")

    // Canal para enviar eventos de notificación a la UI
    private val _notificationEvent = Channel<String>(Channel.BUFFERED)
    val notificationEvent = _notificationEvent.receiveAsFlow()

    private var estadoPatioAnterior = false
    private var estadoSalaAnterior = false

    init {
        iniciarEscucha()
    }

    private fun iniciarEscucha() {
        notificacionesRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (zonaSnapshot in snapshot.children) {
                    val zona = zonaSnapshot.key ?: continue
                    val estadoActual = zonaSnapshot.getValue(Boolean::class.java) ?: false

                    when (zona) {
                        "Patio" -> {
                            if (estadoActual && !estadoPatioAnterior) {
                                enviarEventoNotificacion("Se detectó movimiento en el patio.")
                            }
                            estadoPatioAnterior = estadoActual
                        }
                        "Sala" -> {
                            if (estadoActual && !estadoSalaAnterior) {
                                enviarEventoNotificacion("Se detectó movimiento en la sala.")
                            }
                            estadoSalaAnterior = estadoActual
                        }
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                // Manejo de errores si es necesario
            }
        })
    }

    private fun enviarEventoNotificacion(mensaje: String) {
        viewModelScope.launch {
            _notificationEvent.send(mensaje)
        }
    }
}