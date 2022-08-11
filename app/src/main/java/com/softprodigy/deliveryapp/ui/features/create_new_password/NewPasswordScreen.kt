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

import com.softprodigy.deliveryapp.R
import com.softprodigy.deliveryapp.common.Route
import com.softprodigy.deliveryapp.ui.features.components.AppButton
import com.softprodigy.deliveryapp.ui.features.components.AppOutlineTextField
import com.softprodigy.deliveryapp.ui.features.components.AppText
import com.softprodigy.deliveryapp.ui.theme.spacing

@Composable
fun NewPasswordScreen(
    navController: NavController,
    token: String,
    resetPasswordViewModel: ResetPasswordViewModel = hiltViewModel()
) {
    val verifyResetPassState = resetPasswordViewModel.resetPassUiState.value
    val context = LocalContext.current

    verifyResetPassState.let { state ->
        if (state.isLoading) {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }
        state.errorMessage?.let {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
            state.errorMessage = null
        }

        state.message?.let {
            Toast.makeText(
                context,
                it,
                Toast.LENGTH_SHORT
            ).show()
            state.message = null
            navController.navigate(Route.LOGIN_SCREEN)
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
                    value = resetPasswordViewModel.password.value,
                    label = { Text(text = stringResource(id = R.string.new_password)) },
                    modifier = Modifier.fillMaxWidth(),
                    onValueChange = {
                        resetPasswordViewModel.onEvent(ResetPasswordUIEvent.PasswordChange(it))
                    },
                    placeholder = { Text(text = stringResource(id = R.string.create_password)) },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    visualTransformation = if (resetPasswordViewModel.passwordVisibility.value) VisualTransformation.None else PasswordVisualTransformation(),
                    trailingIcon = {
                        IconButton(onClick = {
                            resetPasswordViewModel.onEvent(
                                ResetPasswordUIEvent.PasswordToggleChange(
                                    !resetPasswordViewModel.passwordVisibility.value
                                )
                            )

                        }) {
                            Icon(
                                imageVector = if (resetPasswordViewModel.passwordVisibility.value)
                                    Icons.Filled.Visibility
                                else
                                    Icons.Filled.VisibilityOff, ""
                            )
                        }
                    },
                )
                Spacer(modifier = Modifier.height(MaterialTheme.spacing.medium))
                AppOutlineTextField(
                    value = resetPasswordViewModel.confirmPassword.value,
                    label = { Text(text = stringResource(id = R.string.confirm_password)) },
                    modifier = Modifier.fillMaxWidth(),
                    onValueChange = {
                        resetPasswordViewModel.onEvent(ResetPasswordUIEvent.ConfirmPasswordChange(it))
                    },
                    placeholder = { Text(text = stringResource(id = R.string.re_enter_password)) },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    visualTransformation = if (resetPasswordViewModel.confirmPasswordVisibility.value) VisualTransformation.None else PasswordVisualTransformation(),
                    trailingIcon = {
                        IconButton(onClick = {
                            resetPasswordViewModel.onEvent(
                                ResetPasswordUIEvent.ConfirmPasswordToggleChange(
                                    !resetPasswordViewModel.confirmPasswordVisibility.value
                                )
                            )

                        }) {
                            Icon(
                                imageVector = if (resetPasswordViewModel.confirmPasswordVisibility.value)
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
                        resetPasswordViewModel.token.value = token
                        resetPasswordViewModel.onEvent(ResetPasswordUIEvent.Submit)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Text(text = stringResource(id = R.string.save))
                }
            }
        }
    }
}