package com.cmt.secureiot.control.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AdsClick
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.EnhancedEncryption
import androidx.compose.material.icons.filled.NoEncryption
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.cmt.secureiot.R
import com.cmt.secureiot.control.data.service.response.LogEntry
import com.cmt.secureiot.core.ui.HeaderSectionBack
import com.cmt.secureiot.core.ui.Section
import com.cmt.secureiot.core.ui.SectionTitle

@Composable
fun Control(
    modifier: Modifier = Modifier,
    navigationController: NavHostController,
    controlViewModel: ControlViewModel = hiltViewModel(),
) {
    val completeAlarmStatus by controlViewModel.completeAlarmStatus.collectAsState()
    val logs by controlViewModel.logs.collectAsState()
    val listState = rememberLazyListState()

    LaunchedEffect(logs) {
        if (logs.isNotEmpty()) {
            listState.animateScrollToItem(logs.size - 1)
        }
    }

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
                Icons.Default.AdsClick,
                stringResource(id = R.string.button_control)
            )
        }

        Spacer(modifier = Modifier.height(10.dp))

        Box(modifier = Modifier.fillMaxWidth(0.8f)) {
            Section(
                title = stringResource(id = R.string.message_security_total),
                message = if (completeAlarmStatus) stringResource(id = R.string.message_activate) else stringResource(
                    id = R.string.message_deactivate
                ),
                buttonText = if (completeAlarmStatus) stringResource(id = R.string.button_deactivate) else stringResource(
                    id = R.string.button_activate
                ),
                icon = if (completeAlarmStatus) Icons.Default.NoEncryption else Icons.Default.EnhancedEncryption,
                status = completeAlarmStatus,
                enabled = true,
                onToggle = { controlViewModel.toggleCompleteAlarmStatus() },
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth(0.85f)
        ) {
            Text(
                text = stringResource(id = R.string.log_title),
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(8.dp)
            )
            Spacer(modifier = Modifier.weight(1f))
            IconButton(onClick = { controlViewModel.clearLogs() }) {
                Icon(Icons.Default.Delete, contentDescription = "clear log", tint = Color.Black)
            }
        }

        Spacer(modifier = Modifier.height(5.dp))

        // logger
        LazyColumn(
            state = listState,
            modifier = Modifier
                .height(300.dp)
                .fillMaxWidth(0.85f)
                .padding(horizontal = 16.dp)
                .background(Color(0xFFF5F5F5)),
            contentPadding = PaddingValues(vertical = 8.dp)
        ) {
            items(logs) { log ->
                LogItem(logEntry = log)
            }
        }
    }
}

@Composable
fun LogItem(logEntry: LogEntry) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 2.dp, horizontal = 4.dp)
    ) {
        Text(
            text = "-> ${logEntry.timestamp} (${logEntry.category} - ${logEntry.location}): ${logEntry.action}",
            fontSize = 12.sp,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.secondary
        )
    }
}