package com.example.deliveryprojectstructuredemo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.core.view.WindowCompat
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.deliveryprojectstructuredemo.common.Route
import com.example.deliveryprojectstructuredemo.common.Route.FORGOT_PASSWORD_SCREEN
import com.example.deliveryprojectstructuredemo.common.Route.LOGIN_SCREEN
import com.example.deliveryprojectstructuredemo.common.Route.NEW_PASSWORD_SCREEN
import com.example.deliveryprojectstructuredemo.common.Route.OTP_VERIFICATION_SCREEN
import com.example.deliveryprojectstructuredemo.common.Route.SIGN_UP_SCREEN
import com.example.deliveryprojectstructuredemo.common.Route.WELCOME_SCREEN
import com.example.deliveryprojectstructuredemo.ui.features.create_new_password.NewPasswordScreen
import com.example.deliveryprojectstructuredemo.ui.features.forgot_password.ForgotPasswordScreen
import com.example.deliveryprojectstructuredemo.ui.features.login.LoginScreen
import com.example.deliveryprojectstructuredemo.ui.features.otp_verification.OTPVerificationScreen
import com.example.deliveryprojectstructuredemo.ui.features.sign_up.SignUpScreen
import com.example.deliveryprojectstructuredemo.ui.features.welcome.WelcomeScreen
import com.example.deliveryprojectstructuredemo.ui.theme.DeliveryProjectStructureDemoTheme
import dagger.hilt.android.AndroidEntryPoint

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
            LoginScreen(navController = navController)
        }
        composable(route = SIGN_UP_SCREEN) {
            SignUpScreen(navController = navController)
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