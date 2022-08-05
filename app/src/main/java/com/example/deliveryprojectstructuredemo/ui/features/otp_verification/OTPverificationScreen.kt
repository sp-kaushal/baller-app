package com.example.deliveryprojectstructuredemo.ui.features.otp_verification

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.deliveryprojectstructuredemo.R
import com.example.deliveryprojectstructuredemo.common.Route
import com.example.deliveryprojectstructuredemo.ui.features.components.AppButton
import com.example.deliveryprojectstructuredemo.ui.features.components.AppText
import com.example.deliveryprojectstructuredemo.ui.features.components.OtpBugView
import com.example.deliveryprojectstructuredemo.ui.theme.spacing
import okhttp3.internal.tls.OkHostnameVerifier.verify

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun OTPVerificationScreen(navController: NavController) {
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
                text = stringResource(id = R.string.otp_verifcation),
                style = MaterialTheme.typography.h1,
                color = MaterialTheme.colors.onSurface,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(MaterialTheme.spacing.small))
            AppText(
                text = stringResource(id = R.string.please_enter_4_digit_code, "your email"),
                style = MaterialTheme.typography.h2,
                textAlign = TextAlign.Start,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(MaterialTheme.spacing.large))
            OtpBugView()
            Spacer(modifier = Modifier.height(MaterialTheme.spacing.large))
            AppButton(
                onClick = {
                    navController.navigate(Route.NEW_PASSWORD_SCREEN)
                },
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(text = stringResource(id = R.string.verify))
            }
            Spacer(modifier = Modifier.height(MaterialTheme.spacing.extraLarge))
            AppText(
                text = stringResource(id = R.string.resend_code),
                color = MaterialTheme.colors.primary,
                modifier = Modifier
                    .clickable {

                    }
            )
            Spacer(modifier = Modifier.height(MaterialTheme.spacing.medium))
        }
    }
}