package com.example.deliveryprojectstructuredemo.ui.features.create_new_password

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.deliveryprojectstructuredemo.R
import com.example.deliveryprojectstructuredemo.common.Route
import com.example.deliveryprojectstructuredemo.ui.features.components.AppButton
import com.example.deliveryprojectstructuredemo.ui.features.components.AppOutlineTextField
import com.example.deliveryprojectstructuredemo.ui.features.components.AppText
import com.example.deliveryprojectstructuredemo.ui.theme.spacing

@Composable
fun NewPasswordScreen(navController: NavController) {
    var newPassword by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var newPasswordVisibility by remember { mutableStateOf(false) }
    var confirmPasswordVisibility by remember { mutableStateOf(false) }

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
                value = newPassword,
                label = { Text(text = stringResource(id = R.string.new_password)) },
                modifier = Modifier.fillMaxWidth(),
                onValueChange = {
                    newPassword = it
                },
                placeholder = { Text(text = stringResource(id = R.string.create_password)) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                visualTransformation = if (newPasswordVisibility) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    IconButton(onClick = {
//                        vm.onEvent(LoginUIEvent.PasswordToggleChange(!vm.passwordVisibility.value))
                        newPasswordVisibility = !newPasswordVisibility

                    }) {
                        Icon(
                            imageVector = if (newPasswordVisibility)
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
                visualTransformation = if (confirmPasswordVisibility) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    IconButton(onClick = {
//                        vm.onEvent(LoginUIEvent.PasswordToggleChange(!vm.passwordVisibility.value))
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
                    navController.navigate(Route.LOGIN_SCREEN)
                },
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(text = stringResource(id = R.string.save))
            }
        }
    }
}