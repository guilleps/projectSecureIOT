package com.cmt.secureiot.panel.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AdsClick
import androidx.compose.material.icons.filled.Camera
import androidx.compose.material.icons.filled.DoorFront
import androidx.compose.material.icons.filled.Light
import androidx.compose.material.icons.filled.NotificationsActive
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.cmt.secureiot.R
import com.cmt.secureiot.core.navigation.Routes
import com.cmt.secureiot.core.ui.HeaderSection

@Composable
fun Panel(modifier: Modifier = Modifier, navigationController: NavHostController) {
    Column(
        modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        HeaderSection(true, navigationController)

        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Row(
                horizontalArrangement = Arrangement.spacedBy(30.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                ControlButton(
                    icon = Icons.Default.DoorFront,
                    label = stringResource(id = R.string.button_door)
                ) {
                    navigationController.navigate(
                        Routes.DoorScreen.route
                    )
                }
                ControlButton(
                    icon = Icons.Default.Light,
                    label = stringResource(id = R.string.button_light)
                ) { navigationController.navigate(Routes.LightScreen.route) }
            }

            Spacer(modifier = Modifier.height(30.dp))

            Row(
                horizontalArrangement = Arrangement.spacedBy(30.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                ControlButton(
                    icon = Icons.Default.Camera,
                    label = stringResource(id = R.string.button_cam)
                ) { navigationController.navigate(Routes.CamScreen.route) }
                ControlButton(
                    icon = Icons.Default.NotificationsActive,
                    label = stringResource(id = R.string.button_alarm)
                ) { navigationController.navigate(Routes.AlarmScreen.route) }
            }

            Spacer(modifier = Modifier.height(30.dp))

            ControlButton(
                icon = Icons.Default.AdsClick,
                label = stringResource(id = R.string.button_control)
            ) { navigationController.navigate(Routes.ControlScreen.route) }
        }

    }
}

@Composable
fun ControlButton(icon: ImageVector, label: String, navigate: () -> Unit) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(30.dp))
            .size(120.dp)
            .background(MaterialTheme.colorScheme.tertiary)
            .clickable { navigate() }
    ) {
        Column(
            Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = icon,
                contentDescription = "icon_door",
                modifier = Modifier.size(50.dp),
                tint = MaterialTheme.colorScheme.secondary
            )
            Text(text = label, fontSize = 20.sp, color = MaterialTheme.colorScheme.secondary)
        }
    }
}