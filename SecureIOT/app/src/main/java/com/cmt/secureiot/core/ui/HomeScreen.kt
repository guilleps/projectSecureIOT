package com.cmt.secureiot.core.ui

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.cmt.secureiot.R
import com.cmt.secureiot.core.navigation.Routes

//@Preview(showSystemUi = true)
@Composable
fun Home(modifier: Modifier = Modifier, navigationController: NavHostController) {
    Column(
        modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        HeaderSection(false, navigationController)

        Spacer(modifier = Modifier.height(5.dp))

        CustomText(
            text = stringResource(id = R.string.tittle_home),
            fontSize = 35,
            fontWeight = FontWeight.ExtraBold,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.secondary
        )

        Spacer(modifier = Modifier.height(40.dp))

        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(70.dp))
                .background(MaterialTheme.colorScheme.primary)
                .size(215.dp),
            contentAlignment = Alignment.BottomCenter
        ) {
            Image(
                painterResource(id = R.drawable.senior),
                contentDescription = "senior",
                Modifier
                    .aspectRatio(1f),
                contentScale = ContentScale.Crop
            )
        }

        Spacer(modifier = Modifier.height(30.dp))

        CustomText(
            text = stringResource(id = R.string.content_home),
            fontSize = 24,
            color = MaterialTheme.colorScheme.secondary,
            fontWeight = FontWeight.ExtraBold,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 50.dp)
        )

        Spacer(modifier = Modifier.height(100.dp))

        Button(
            onClick = { navigationController.navigate(Routes.LoginScreen.route) },
            modifier = Modifier
                .width(300.dp)
                .height(60.dp)
                .clip(RoundedCornerShape(20.dp)),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.secondary
            )
        ) {
            CustomText(
                text = stringResource(id = R.string.button_home),
                fontSize = 20,
                fontWeight = FontWeight.ExtraBold,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onSecondary
            )
        }
    }
}

@Composable
fun HeaderSection(onLogout: Boolean, navigationController: NavHostController) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(180.dp), contentAlignment = Alignment.Center
    ) {
        if (onLogout) IconLogout(Modifier.align(Alignment.TopStart), navigationController)
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
fun IconLogout(modifier: Modifier, navigationController: NavHostController) {
    val context = LocalContext.current
    Icon(
        Icons.AutoMirrored.Filled.Logout,
        contentDescription = "logout",
        tint = Color.Black,
        modifier = modifier
            .padding(24.dp)
            .rotate(180f)
            .clickable {
                navigationController.navigate(Routes.LoginScreen.route)
                Toast.makeText(context, "Cuenta cerrada", Toast.LENGTH_SHORT).show();
            }
    )
}

@Composable
fun CustomText(
    text: String,
    fontSize: Int,
    color: Color,
    fontWeight: FontWeight,
    textAlign: TextAlign,
    modifier: Modifier = Modifier,
) {
    Text(
        text = text,
        color = color,
        fontSize = fontSize.sp,
        fontWeight = fontWeight,
        textAlign = textAlign,
        modifier = modifier
    )
}