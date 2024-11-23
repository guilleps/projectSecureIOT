package com.cmt.secureiot.alarm.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.NotificationsActive
import androidx.compose.material.icons.filled.NotificationsOff
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.cmt.secureiot.R
import com.cmt.secureiot.core.ui.HeaderSectionBack
import com.cmt.secureiot.core.ui.Section
import com.cmt.secureiot.core.ui.SectionTitle

@Composable
fun Alarm(
    modifier: Modifier = Modifier,
    navigationController: NavHostController,
    alarmViewModel: AlarmViewModel = hiltViewModel(),
) {
    val alarmStatus by alarmViewModel.alarmStatus.collectAsState()
    val securityTotalActive by alarmViewModel.securityTotalActive.collectAsState()

    Column(
        modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        HeaderSectionBack(navigationController)

        Spacer(modifier = Modifier.height(5.dp))

        Box(modifier = Modifier.fillMaxWidth()) {
            SectionTitle(
                Icons.Default.NotificationsActive,
                stringResource(id = R.string.button_alarm)
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .fillMaxHeight(0.8f), contentAlignment = Alignment.Center
        ) {

            Section(
                title = stringResource(id = R.string.button_alarm),
                message = if (alarmStatus) stringResource(id = R.string.message_on) else stringResource(
                    id = R.string.message_off
                ),
                buttonText = if (alarmStatus) stringResource(id = R.string.button_off) else stringResource(
                    id = R.string.button_on
                ),
                icon = if (alarmStatus) Icons.Default.NotificationsOff else Icons.Default.Notifications,
                status = alarmStatus,
                enabled = !securityTotalActive,
                onToggle = { alarmViewModel.toggleAlarmStatus() })
        }
    }
}