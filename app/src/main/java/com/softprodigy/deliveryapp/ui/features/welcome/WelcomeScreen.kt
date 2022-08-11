package com.softprodigy.deliveryapp.ui.features.welcome

import android.content.res.Configuration
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.softprodigy.deliveryapp.common.Route
import com.softprodigy.deliveryapp.ui.features.components.AppButton
import com.softprodigy.deliveryapp.ui.features.components.AppOutlinedButton
import com.softprodigy.deliveryapp.ui.features.components.AppText
import com.softprodigy.deliveryapp.ui.features.components.SocialSection
import com.softprodigy.deliveryapp.ui.theme.DeliveryProjectStructureDemoTheme
import com.softprodigy.deliveryapp.ui.theme.spacing
import com.softprodigy.deliveryapp.R

@Composable
fun WelcomeScreen(navController: NavController) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_welcome),
                contentDescription = "Welcome Icon",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(163.dp)
            )
            Spacer(modifier = Modifier.height(MaterialTheme.spacing.medium))
            AppText(
                text = stringResource(id = R.string.welcome_to_the_app),
                style = MaterialTheme.typography.h1,
                color = MaterialTheme.colors.onSurface
            )
            Spacer(modifier = Modifier.height(MaterialTheme.spacing.small))
            AppText(
                text = stringResource(id = R.string.create_free_account),
                style = MaterialTheme.typography.h2,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(MaterialTheme.spacing.large))
            AppButton(
                onClick = { navController.navigate(Route.SIGN_UP_SCREEN) },
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                androidx.compose.material.Text(text = stringResource(id = R.string.create_new_account))
            }
            Spacer(modifier = Modifier.height(MaterialTheme.spacing.small))
            AppOutlinedButton(
                onClick = { /*TODO*/ },
                border = BorderStroke(1.dp, MaterialTheme.colors.primary),
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                androidx.compose.material.Text(text = stringResource(id = R.string.skip))
            }

            Spacer(modifier = Modifier.height(MaterialTheme.spacing.large))
            SocialSection(modifier = Modifier.fillMaxWidth(), onGoogleClick = {

            }, onFacebookClick = {

            }, onFooterClick = {
                navController.navigate(Route.LOGIN_SCREEN)
            })

        }
    }
}

@Preview("default", "rectangle")
@Preview("dark theme", "rectangle", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview("large font", "rectangle", fontScale = 2f)
@Composable
private fun RectangleButtonPreview() {
    DeliveryProjectStructureDemoTheme {
        Surface {
//            WelcomeScreen()
        }
    }
}