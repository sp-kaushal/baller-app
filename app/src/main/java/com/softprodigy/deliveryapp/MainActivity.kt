package com.softprodigy.deliveryapp

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.view.WindowCompat
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.softprodigy.deliveryapp.ui.features.create_new_password.NewPasswordScreen
import com.softprodigy.deliveryapp.ui.features.forgot_password.ForgotPasswordScreen
import com.softprodigy.deliveryapp.ui.features.otp_verification.OTPVerificationScreen
import com.softprodigy.deliveryapp.common.Route.FORGOT_PASSWORD_SCREEN
import com.softprodigy.deliveryapp.common.Route.LOGIN_SCREEN
import com.softprodigy.deliveryapp.common.Route.NEW_PASSWORD_SCREEN
import com.softprodigy.deliveryapp.common.Route.OTP_VERIFICATION_SCREEN
import com.softprodigy.deliveryapp.common.Route.SIGN_UP_SCREEN
import com.softprodigy.deliveryapp.common.Route.WELCOME_SCREEN
import com.softprodigy.deliveryapp.ui.features.login.LoginScreen
import com.softprodigy.deliveryapp.ui.features.sign_up.SignUpScreen
import com.softprodigy.deliveryapp.ui.features.welcome.WelcomeScreen
import com.softprodigy.deliveryapp.ui.theme.DeliveryProjectStructureDemoTheme
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            DeliveryProjectStructureDemoTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    NavControllerComposable()
                }
            }
        }
    }
}


@Composable
fun NavControllerComposable() {
    val navController = rememberNavController()
    NavHost(navController, startDestination = WELCOME_SCREEN) {
        composable(route = WELCOME_SCREEN) {
            WelcomeScreen(navController = navController)
        }
        composable(route = LOGIN_SCREEN) {
            val context = LocalContext.current

            LoginScreen(
                onLoginSuccess = { user ->
                    Timber.i("Welcome ${user.firstName} ${user.lastName}")
                    Toast.makeText(
                        context,
                        "Welcome ${user.firstName} ${user.lastName}",
                        Toast.LENGTH_SHORT
                    ).show()
                },
                onCreateAccountClick = { navController.navigate(SIGN_UP_SCREEN) },
                onForgetPasswordClick = { navController.navigate(FORGOT_PASSWORD_SCREEN) },
                onGoogleClick = {},
                onFacebookClick = {})
        }
        composable(route = SIGN_UP_SCREEN) {
            val context = LocalContext.current
            SignUpScreen(onSuccessfulSignUp = {signUpResponse ->
                Toast.makeText(context, "verifyToken ${signUpResponse.verifyToken}", Toast.LENGTH_SHORT).show()
            },
            onGoogleClick = {},
            onFacebookClick = {},
            onLoginClick = { navController.navigate(LOGIN_SCREEN) })
        }
        composable(route = FORGOT_PASSWORD_SCREEN) {
            ForgotPasswordScreen(navController = navController)
        }
        composable(route = "$OTP_VERIFICATION_SCREEN/{token}") {
            val token = it.arguments?.getString("token")
            OTPVerificationScreen(navController = navController, token = token!!)
        }
        composable(route = "$NEW_PASSWORD_SCREEN/{token}") {
            val token = it.arguments?.getString("token")
            NewPasswordScreen(navController = navController, token = token!!)
        }
    }
}