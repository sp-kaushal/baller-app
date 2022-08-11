package com.softprodigy.deliveryapp.ui.features.forgot_password

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.delivery_app.core.util.UiEvent

import com.softprodigy.deliveryapp.R
import com.softprodigy.deliveryapp.common.Route
import com.softprodigy.deliveryapp.common.isValidEmail
import com.softprodigy.deliveryapp.ui.features.components.AppButton
import com.softprodigy.deliveryapp.ui.features.components.AppOutlineTextField
import com.softprodigy.deliveryapp.ui.features.components.AppText
import com.softprodigy.deliveryapp.ui.theme.spacing

@Composable
fun ForgotPasswordScreen(
    navController: NavController,
    vm: ForgotPasswordViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    var email by rememberSaveable { mutableStateOf("") }
    val forgotPassState = vm.forgotPassUiState.value

    LaunchedEffect(key1 = true) {
        vm.uiEvent.collect { uiEvent ->
            when (uiEvent) {
                is UiEvent.Success -> {
                    vm.forgotResponse?.let {
                        Toast.makeText(context, it.message, Toast.LENGTH_LONG)
                            .show()
                        navController.navigate(Route.OTP_VERIFICATION_SCREEN + "/${it.verifyToken}")
                    }
                }
                is UiEvent.ShowToast -> {
                    Toast.makeText(context, uiEvent.message.asString(context), Toast.LENGTH_LONG)
                        .show()
                }
                else -> Unit
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp)
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(MaterialTheme.spacing.extraXLarge))

            Image(
                painter = painterResource(id = R.drawable.ic_forgot_password),
                contentDescription = "Welcome Icon",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
            )
            Spacer(modifier = Modifier.height(MaterialTheme.spacing.medium))
            AppText(
                text = stringResource(id = R.string.forgot_password_heading),
                style = MaterialTheme.typography.h1,
                color = MaterialTheme.colors.onSurface,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(MaterialTheme.spacing.small))
            AppText(
                text = stringResource(id = R.string.forgot_password_subtitle),
                style = MaterialTheme.typography.h2,
                textAlign = TextAlign.Start,
            )
            Spacer(modifier = Modifier.height(MaterialTheme.spacing.large))
            AppOutlineTextField(
                value = email,
                label = { Text(text = stringResource(id = R.string.email)) },
                modifier = Modifier.fillMaxWidth(),
                onValueChange = {
                    email = it
                },
                placeholder = { Text(text = stringResource(id = R.string.enter_your_email)) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                isError = (!email.isValidEmail() && email.length >= 6),
                errorMessage = stringResource(id = R.string.email_error),
            )
            Spacer(modifier = Modifier.height(MaterialTheme.spacing.large))
            AppButton(
                onClick = {
                    vm.onEvent(
                        ForgotPasswordUIEvent.Submit(email)
                    )

                },
                modifier = Modifier
                    .fillMaxWidth(),
                enabled = email.isValidEmail()
            ) {
                Text(text = stringResource(id = R.string.send))
            }
            Spacer(modifier = Modifier.height(MaterialTheme.spacing.extraLarge))
            AppText(
                text = stringResource(id = R.string.back_to_login),
                color = MaterialTheme.colors.primary,
                modifier = Modifier
                    .clickable {
                        navController.navigate(Route.LOGIN_SCREEN)
                    }
            )
            Spacer(modifier = Modifier.height(MaterialTheme.spacing.large))

        }

        if (forgotPassState.isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        }
    }

}