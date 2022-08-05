package com.example.deliveryprojectstructuredemo.ui.features.forgot_password

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
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
fun ForgotPasswordScreen(navController: NavController) {
    var email by remember {
        mutableStateOf("")
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
//                    vm.onEvent(LoginUIEvent.EmailChange(it))
                    email = it
                },
                placeholder = { Text(text = stringResource(id = R.string.enter_your_email)) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            )
            Spacer(modifier = Modifier.height(MaterialTheme.spacing.large))
            AppButton(
                onClick = {
                    navController.navigate(Route.OTP_VERIFICATION_SCREEN)
                },
                modifier = Modifier
                    .fillMaxWidth()
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
    }
}