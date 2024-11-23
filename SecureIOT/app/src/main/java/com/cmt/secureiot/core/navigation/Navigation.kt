package com.cmt.secureiot.core.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.cmt.secureiot.alarm.ui.Alarm
import com.cmt.secureiot.camera.ui.Cam
import com.cmt.secureiot.control.ui.Control
import com.cmt.secureiot.core.ui.Home
import com.cmt.secureiot.door.ui.Door
import com.cmt.secureiot.light.ui.Light
import com.cmt.secureiot.login.ui.Login
import com.cmt.secureiot.panel.ui.Panel


@Composable
fun AppNavGraph(
    modifier: Modifier,
    navController: NavHostController,
    startDestination: String = Routes.HomeScreen.route,
) {
    NavHost(navController = navController, startDestination = startDestination) {
        addHomeRoute(modifier, navController)
        addLoginRoute(modifier, navController)
        addPanelRoute(modifier, navController)
        addDoorRoute(modifier, navController)
        addLightRoute(modifier, navController)
        addCamRoute(modifier, navController)
        addAlarmRoute(modifier, navController)
        addControlRoute(modifier, navController)
    }
}

fun NavGraphBuilder.addHomeRoute(modifier: Modifier, navController: NavHostController) {
    composable(Routes.HomeScreen.route) {
        Home(modifier = modifier, navigationController = navController)
    }
}

fun NavGraphBuilder.addLoginRoute(modifier: Modifier, navController: NavHostController) {
    composable(Routes.LoginScreen.route) {
        Login(modifier = modifier, navigationController = navController)
    }
}

fun NavGraphBuilder.addPanelRoute(modifier: Modifier, navController: NavHostController) {
    composable(Routes.PanelScreen.route) {
        Panel(modifier = modifier, navigationController = navController)
    }
}

fun NavGraphBuilder.addDoorRoute(modifier: Modifier, navController: NavHostController) {
    composable(Routes.DoorScreen.route) {
        Door(modifier = modifier, navigationController = navController)
    }
}

fun NavGraphBuilder.addLightRoute(modifier: Modifier, navController: NavHostController) {
    composable(Routes.LightScreen.route) {
        Light(modifier = modifier, navigationController = navController)
    }
}

fun NavGraphBuilder.addCamRoute(modifier: Modifier, navController: NavHostController) {
    composable(Routes.CamScreen.route) {
        Cam(modifier = modifier, navigationController = navController)
    }
}

fun NavGraphBuilder.addAlarmRoute(modifier: Modifier, navController: NavHostController) {
    composable(Routes.AlarmScreen.route) {
        Alarm(modifier = modifier, navigationController = navController)
    }
}

fun NavGraphBuilder.addControlRoute(modifier: Modifier, navController: NavHostController) {
    composable(Routes.ControlScreen.route) {
        Control(modifier = modifier, navigationController = navController)
    }
}