package com.cmt.secureiot.light.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FlashOff
import androidx.compose.material.icons.filled.FlashOn
import androidx.compose.material.icons.filled.Light
import androidx.compose.material3.HorizontalDivider
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
fun Light(
    modifier: Modifier = Modifier,
    navigationController: NavHostController,
    lightViewModel: LightViewModel = hiltViewModel(),
) {
    val lightsStatus by lightViewModel.lightsStatus.collectAsState()
    val securityTotalActive by lightViewModel.securityTotalActive.collectAsState() // Observa el estado compartido
    val scrollState = rememberScrollState()
    val allOn = lightsStatus.values.all { it }

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
            SectionTitle(Icons.Default.Light, stringResource(id = R.string.button_light))
            ButtonTitle(
                if (allOn) stringResource(id = R.string.button_off_all_light) else stringResource(id = R.string.button_on_all_light),
                if (allOn) Icons.Default.FlashOff else Icons.Default.FlashOn,
                160,
                { lightViewModel.toggleAllLights(!allOn) },
                enabled = !securityTotalActive)
        }

        Spacer(modifier = Modifier.height(30.dp))

        Column(
            Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
        ) {
            lightsStatus.forEach { (lightName, isOn) ->
                Section(
                    title = lightName,
                    message = if (isOn) stringResource(id = R.string.message_on) else stringResource(
                        id = R.string.message_off
                    ),
                    buttonText = if (isOn) stringResource(id = R.string.button_off) else stringResource(
                        id = R.string.button_on
                    ),
                    icon = if (isOn) Icons.Default.FlashOff else Icons.Default.FlashOn,
                    status = isOn,
                    enabled = !securityTotalActive,
                    onToggle = { lightViewModel.toggleLightStatus(lightName, !isOn) },
                )

                HorizontalDivider(
                    color = MaterialTheme.colorScheme.secondary,
                    thickness = 1.dp,
                    modifier = Modifier.padding(horizontal = 45.dp)
                )
            }
        }
    }
}