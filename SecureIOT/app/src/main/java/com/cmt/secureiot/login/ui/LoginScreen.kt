package com.cmt.secureiot.login.ui

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowRightAlt
import androidx.compose.material.icons.filled.Key
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.cmt.secureiot.R
import com.cmt.secureiot.core.navigation.Routes
import com.cmt.secureiot.core.ui.CustomText
import com.cmt.secureiot.core.ui.HeaderSection
import com.cmt.secureiot.login.domain.model.Result

@Composable
fun Login(
    modifier: Modifier = Modifier,
    navigationController: NavHostController,
    loginViewModel: LoginViewModel = hiltViewModel(),
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val loginResult by loginViewModel.loginResult.collectAsState()
    val context = LocalContext.current
    val focusManager = LocalFocusManager.current

    Column(
        modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        HeaderSection(false, navigationController)

        Box(
            modifier = Modifier
                .width(320.dp)
                .weight(1f),
            contentAlignment = Alignment.Center
        ) {
            Column(
                Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(.8f)
                    .clip(RoundedCornerShape(20.dp))
                    .background(MaterialTheme.colorScheme.tertiary)
                    .padding(vertical = 35.dp), horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    imageVector = Icons.Default.Key,
                    contentDescription = "icon_login",
                    tint = MaterialTheme.colorScheme.secondary,
                    modifier = Modifier.size(50.dp)
                )

                Spacer(modifier = Modifier.height(15.dp))

                CustomText(
                    text = stringResource(id = R.string.title_login),
                    fontSize = 20,
                    fontWeight = FontWeight.ExtraBold,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.secondary
                )

                Spacer(modifier = Modifier.height(25.dp))

                CustomText(
                    text = stringResource(id = R.string.subtitle_login),
                    fontSize = 16,
                    fontWeight = FontWeight.Normal,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.secondary
                )
                Text(
                    text = stringResource(id = R.string.content_login),
                    fontSize = 14.sp,
                    lineHeight = 15.sp,
                    fontWeight = FontWeight.Normal,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.secondary,
                    modifier = Modifier.padding(horizontal = 50.dp)
                )

                Spacer(modifier = Modifier.height(25.dp))

                EmailField(
                    value = email,
                    onValueChange = { email = it },
                    validateInputs = { e, p -> loginViewModel.validateInputs(e, p) },
                    password = password,
                    onNext = { focusManager.moveFocus(FocusDirection.Down) }
                )

                Spacer(modifier = Modifier.height(20.dp))

                PasswordField(
                    value = password,
                    onValueChange = { password = it },
                    validateInputs = { e, p -> loginViewModel.validateInputs(e, p) },
                    email = email,
                    onDone = { }
                )

                Spacer(modifier = Modifier.height(30.dp))

                LoginButton(
                    { loginViewModel.login(email, password) },
                    loginViewModel.isLoginEnabled.collectAsState().value
                )
            }
        }

        // Mostrar mensaje de error o navegar según el resultado
        when (loginResult) {
            is Result.Success -> {
                LaunchedEffect(Unit) {
                    navigationController.navigate(Routes.PanelScreen.route) {
                        popUpTo(Routes.LoginScreen.route) { inclusive = true }
                    }
                }
            }

            is Result.Error -> {
                LaunchedEffect(loginResult) {
                    Toast.makeText(context, "Error de autenticación", Toast.LENGTH_SHORT).show();
                    loginViewModel.resetLoginResult()
                }
            }

            else -> { /* No mostrar nada */
            }
        }
    }
}

@Composable
fun LoginButton(onClick: () -> Unit, isEnabled: Boolean) {
    Button(
        onClick = onClick,
        enabled = isEnabled,
        modifier = Modifier
            .width(250.dp)
            .height(50.dp)
            .clip(RoundedCornerShape(20.dp)),
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.secondary,
            contentColor = MaterialTheme.colorScheme.onSecondary,
            disabledContainerColor = MaterialTheme.colorScheme.secondaryContainer,
            disabledContentColor = MaterialTheme.colorScheme.onSecondaryContainer
        ),
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = stringResource(id = R.string.button_login),
                fontWeight = FontWeight.ExtraBold,
                fontSize = 15.sp,
            )
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowRightAlt,
                contentDescription = "icon_login"
            )
        }
    }
}

@Composable
fun EmailField(
    value: String,
    onValueChange: (String) -> Unit,
    validateInputs: (String, String) -> Unit,
    password: String,
    onNext: () -> Unit,
) {
    val focusManager = LocalFocusManager.current

    TextField(
        value = value,
        onValueChange = {
            onValueChange(it)
            validateInputs(it, password)
        },
        modifier = Modifier
            .width(250.dp)
            .height(50.dp),
        placeholder = {
            Text(
                text = stringResource(id = R.string.email_placeholder),
                color = MaterialTheme.colorScheme.onTertiary,
                fontSize = 14.sp, lineHeight = 15.sp
            )
        },
        textStyle = TextStyle(
            fontSize = 14.sp,
            lineHeight = 15.sp,
            color = Color.Black
        ),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Email,
            imeAction = ImeAction.Next
        ),
        keyboardActions = KeyboardActions(
            onNext = { onNext() }),
        singleLine = true,
        maxLines = 1,
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color.White,
            unfocusedContainerColor = Color.White,
            unfocusedTextColor = MaterialTheme.colorScheme.tertiary,
            unfocusedTrailingIconColor = MaterialTheme.colorScheme.tertiary,
            focusedTextColor = MaterialTheme.colorScheme.primary,
            focusedTrailingIconColor = MaterialTheme.colorScheme.primary,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
        ),
        shape = RoundedCornerShape(20.dp),
    )
}

@Composable
fun PasswordField(
    value: String,
    onValueChange: (String) -> Unit,
    validateInputs: (String, String) -> Unit,
    email: String,
    onDone: () -> Unit,
) {
    val focusManager = LocalFocusManager.current
    var passwordStateVisibility by remember { mutableStateOf(false) }

    TextField(
        value = value,
        onValueChange = {
            onValueChange(it)
            validateInputs(email, it)
        },
        modifier = Modifier
            .width(250.dp)
            .height(50.dp),
        placeholder = {
            Text(
                text = stringResource(id = R.string.password_placeholder),
                color = MaterialTheme.colorScheme.onTertiary,
                fontSize = 14.sp, lineHeight = 15.sp
            )
        },
        textStyle = TextStyle(
            fontSize = 14.sp,
            lineHeight = 15.sp,
            color = Color.Black
        ),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Password,
            imeAction = ImeAction.Done
        ),
        keyboardActions = KeyboardActions(
            onDone = {
                focusManager.clearFocus()
                onDone()
            }
        ),
        singleLine = true,
        maxLines = 1,
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color.White,
            unfocusedContainerColor = Color.White,
            unfocusedTextColor = MaterialTheme.colorScheme.tertiary,
            unfocusedTrailingIconColor = MaterialTheme.colorScheme.tertiary,
            focusedTextColor = MaterialTheme.colorScheme.primary,
            focusedTrailingIconColor = MaterialTheme.colorScheme.primary,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
        ),
        trailingIcon = {
            val imagen = if (passwordStateVisibility) {
                Icons.Filled.Visibility
            } else {
                Icons.Filled.VisibilityOff
            }
            IconButton(onClick = { passwordStateVisibility = !passwordStateVisibility }) {
                Icon(
                    imageVector = imagen,
                    contentDescription = "show password",
                    tint = MaterialTheme.colorScheme.onTertiary
                )
            }
        },
        shape = RoundedCornerShape(20.dp),
        visualTransformation = if (!passwordStateVisibility) PasswordVisualTransformation() else VisualTransformation.None
    )
}