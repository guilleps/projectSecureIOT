package com.cmt.secureiot.door.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DoorFront
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.LockOpen
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
import com.cmt.secureiot.core.ui.ButtonTitle
import com.cmt.secureiot.core.ui.HeaderSectionBack
import com.cmt.secureiot.core.ui.Section
import com.cmt.secureiot.core.ui.SectionTitle

@Composable
fun Door(
    modifier: Modifier = Modifier,
    navigationController: NavHostController,
    doorViewModel: DoorViewModel = hiltViewModel(),
) {
    val doorsStatus by doorViewModel.doorsStatus.collectAsState()
    val securityTotalActive by doorViewModel.securityTotalActive.collectAsState()
    val allOpen = doorsStatus.values.all { it }

    Column(
        modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        HeaderSectionBack(navigationController)

        Spacer(modifier = Modifier.height(5.dp))

        Row(horizontalArrangement = Arrangement.Center) {
            SectionTitle(Icons.Default.DoorFront, stringResource(id = R.string.button_door))
            ButtonTitle(
                if (allOpen) stringResource(id = R.string.button_close_all_door) else stringResource(
                    id = R.string.button_open_all_door
                ),
                if (allOpen) Icons.Default.Lock else Icons.Default.LockOpen,
                160,
                { doorViewModel.toggleAllDoorsStatus(!allOpen) },
                enabled = !securityTotalActive
            )
        }

        Spacer(modifier = Modifier.height(30.dp))

        Column(Modifier.fillMaxSize()) {
            doorsStatus.forEach { (doorName, isOpen) ->
                Section(
                    title = doorName,
                    message = if (isOpen) stringResource(id = R.string.message_open_door) else stringResource(
                        id = R.string.message_close_door
                    ),
                    buttonText = if (isOpen) stringResource(id = R.string.button_close_door) else stringResource(
                        id = R.string.button_open_door
                    ),
                    icon = if (isOpen) Icons.Default.LockOpen else Icons.Default.Lock,
                    status = isOpen,
                    enabled = !securityTotalActive,
                    onToggle = { doorViewModel.toggleDoorStatus(doorName, !isOpen) })
            }
        }
    }
}

