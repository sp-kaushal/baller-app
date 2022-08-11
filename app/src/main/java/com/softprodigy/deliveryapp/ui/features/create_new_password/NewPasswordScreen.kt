package com.softprodigy.deliveryapp.ui.features.create_new_password

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.delivery_app.core.util.UiEvent

import com.softprodigy.deliveryapp.R
import com.softprodigy.deliveryapp.common.Route
import com.softprodigy.deliveryapp.common.isValidPassword
import com.softprodigy.deliveryapp.common.passwordMatches
import com.softprodigy.deliveryapp.ui.features.components.AppButton
import com.softprodigy.deliveryapp.ui.features.components.AppOutlineTextField
import com.softprodigy.deliveryapp.ui.features.components.AppText
import com.softprodigy.deliveryapp.ui.theme.spacing
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow

@Composable
fun NewPasswordScreen(
    navController: NavController,
    token: String,
    resetPasswordViewModel: ResetPasswordViewModel = hiltViewModel()
) {

    val verifyResetPassState = resetPasswordViewModel.resetPassUiState.value
    val context = LocalContext.current
    val forgotPassState = resetPasswordViewModel.resetPassUiState.value

    var password by rememberSaveable { mutableStateOf("") }
    var confirmPassword by rememberSaveable { mutableStateOf("") }

    var passwordVisibility by rememberSaveable { mutableStateOf(false) }
    var confirmPasswordVisibility by rememberSaveable { mutableStateOf(false) }

    LaunchedEffect(key1 = Unit) {
        resetPasswordViewModel.uiEvent.collect { uiEvent ->
            when (uiEvent) {
                is UiEvent.Success -> {
                    resetPasswordViewModel.resetPasswordResponse?.let {
                        Toast.makeText(context, it.message, Toast.LENGTH_LONG)
                            .show()
                        navController.navigate(Route.LOGIN_SCREEN)
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
                painter = painterResource(id = R.drawable.ic_otp_verification),
                contentDescription = "Welcome Icon",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
            )
            Spacer(modifier = Modifier.height(MaterialTheme.spacing.medium))
            AppText(
                text = stringResource(id = R.string.create_new_password),
                style = MaterialTheme.typography.h1,
                color = MaterialTheme.colors.onSurface,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(MaterialTheme.spacing.small))
            AppText(
                text = stringResource(id = R.string.new_password_must_be_different),
                style = MaterialTheme.typography.h2,
                textAlign = TextAlign.Start,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(MaterialTheme.spacing.large))
            AppOutlineTextField(
                value = password,
                label = { Text(text = stringResource(id = R.string.new_password)) },
                modifier = Modifier.fillMaxWidth(),
                onValueChange = {
                    password = it
                },
                placeholder = { Text(text = stringResource(id = R.string.create_password)) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                isError = (!password.isValidPassword() && password.length >= 4),
                errorMessage = stringResource(id = R.string.password_error),
                visualTransformation = if (passwordVisibility) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    IconButton(onClick = {
                        passwordVisibility = !passwordVisibility

                    }) {
                        Icon(
                            imageVector = if (passwordVisibility)
                                Icons.Filled.Visibility
                            else
                                Icons.Filled.VisibilityOff, ""
                        )
                    }
                },
            )
            Spacer(modifier = Modifier.height(MaterialTheme.spacing.medium))
            AppOutlineTextField(
                value = confirmPassword,
                label = { Text(text = stringResource(id = R.string.confirm_password)) },
                modifier = Modifier.fillMaxWidth(),
                onValueChange = {
                    confirmPassword = it
                },
                placeholder = { Text(text = stringResource(id = R.string.re_enter_password)) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                errorMessage = stringResource(id = R.string.confirm_password_error),
                isError = (!confirmPassword.isValidPassword() && !confirmPassword.passwordMatches(password)),
                visualTransformation = if (confirmPasswordVisibility) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    IconButton(onClick = {
                        confirmPasswordVisibility = !confirmPasswordVisibility

                    }) {
                        Icon(
                            imageVector = if (confirmPasswordVisibility)
                                Icons.Filled.Visibility
                            else
                                Icons.Filled.VisibilityOff, ""
                        )
                    }
                },
            )
            Spacer(modifier = Modifier.height(MaterialTheme.spacing.large))
            AppButton(
                onClick = {
                    resetPasswordViewModel.onEvent(
                        ResetPasswordUIEvent.Submit(
                            token,
                            confirmPassword
                        )
                    )
                },
                modifier = Modifier
                    .fillMaxWidth(),
                enabled = confirmPassword.passwordMatches(password)
            ) {
                Text(text = stringResource(id = R.string.save))
            }
        }

        if (forgotPassState.isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        }
    }
}