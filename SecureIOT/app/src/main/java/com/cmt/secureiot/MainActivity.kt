package com.cmt.secureiot

import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.rememberNavController
import com.cmt.secureiot.core.navigation.AppNavGraph
import com.cmt.secureiot.core.notification.MovimientoViewModel
import com.cmt.secureiot.ui.theme.SecureIOTTheme
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val movimientoViewModel: MovimientoViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            SecureIOTTheme {
                val systemUiController = rememberSystemUiController()

                SideEffect {
                    systemUiController.setStatusBarColor(
                        color = Color.Black,
                        darkIcons = false
                    )
                }

                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val navigationController = rememberNavController()

                    CollectNotificationEvents(movimientoViewModel)

                    AppNavGraph(Modifier.padding(innerPadding), navigationController)
                }
            }
        }
    }

    private fun emitirNotificacion(mensaje: String) {
        val channelId = "movimiento_alertas"
        val notificationManager = getSystemService(NotificationManager::class.java)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                "Alertas de Movimiento",
                NotificationManager.IMPORTANCE_HIGH
            )
            notificationManager.createNotificationChannel(channel)
        }

        val notification = androidx.core.app.NotificationCompat.Builder(this, channelId)
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setContentTitle("Movimiento detectado")
            .setContentText(mensaje)
            .setPriority(androidx.core.app.NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .build()

        notificationManager.notify(System.currentTimeMillis().toInt(), notification)
    }

    @Composable
    private fun CollectNotificationEvents(viewModel: MovimientoViewModel) {
        val context = LocalContext.current

        LaunchedEffect(viewModel) {
            viewModel.notificationEvent.collect { mensaje ->
                if (mensaje.isNotEmpty()) {
                    emitirNotificacion(mensaje)
                }
            }
        }
    }
}

@Composable
fun GreetingPreview() {
    SecureIOTTheme {
    }
}