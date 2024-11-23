package com.cmt.secureiot.core.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.cmt.secureiot.R

@Composable
fun HeaderSectionBack(navHostController: NavHostController) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(180.dp), contentAlignment = Alignment.Center
    ) {
        IconBack(navHostController, Modifier.align(Alignment.TopStart))
        Image(
            painterResource(id = R.drawable.logo_iot_light),
            contentDescription = "logo",
            Modifier
                .width(90.dp)
                .aspectRatio(1f),
            contentScale = ContentScale.Crop
        )
    }
}


@Composable
fun IconBack(navController: NavController, modifier: Modifier) {
    Icon(
        imageVector = Icons.Default.ArrowBackIosNew,
        contentDescription = "Retroceso",
        modifier = modifier
            .padding(24.dp)
            .clickable { navController.popBackStack() },
        tint = MaterialTheme.colorScheme.secondary
    )
}