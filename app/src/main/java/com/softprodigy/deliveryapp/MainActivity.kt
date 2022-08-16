package com.softprodigy.deliveryapp

import android.os.Bundle
import android.util.Log
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
import com.softprodigy.deliveryapp.common.Route
import com.softprodigy.deliveryapp.ui.features.create_new_password.NewPasswordScreen
import com.softprodigy.deliveryapp.ui.features.forgot_password.ForgotPasswordScreen
import com.softprodigy.deliveryapp.ui.features.otp_verification.OTPVerificationScreen
import com.softprodigy.deliveryapp.common.Route.FORGOT_PASSWORD_SCREEN
import com.softprodigy.deliveryapp.common.Route.HOME_SCREEN
import com.softprodigy.deliveryapp.common.Route.LOGIN_SCREEN
import com.softprodigy.deliveryapp.common.Route.NEW_PASSWORD_SCREEN
import com.softprodigy.deliveryapp.common.Route.OTP_VERIFICATION_SCREEN
import com.softprodigy.deliveryapp.common.Route.SIGN_UP_SCREEN
import com.softprodigy.deliveryapp.common.Route.WELCOME_SCREEN
import com.softprodigy.deliveryapp.data.response.ForgotPasswordResponse
import com.softprodigy.deliveryapp.ui.features.create_new_password.NewPasswordScreen
import com.softprodigy.deliveryapp.ui.features.forgot_password.ForgotPasswordScreen
import com.softprodigy.deliveryapp.ui.features.home.HomeScreen
import com.softprodigy.deliveryapp.ui.features.login.LoginScreen
import com.softprodigy.deliveryapp.ui.features.otp_verification.OTPVerificationScreen
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
            WelcomeScreen(onCreateAccountCLick = {
                navController.navigate(SIGN_UP_SCREEN)
            }, onSkipCLick = {
                navController.navigate(HOME_SCREEN + "/${null}") {
                    popUpTo(WELCOME_SCREEN) {
                        inclusive = true
                    }
                }
            },
                onLoginClick = {
                    navController.navigate(LOGIN_SCREEN)
                },
                onFacebookClick = {

                },
                onGoogleLogin = { userResponse ->
                    navController.navigate(HOME_SCREEN + "/${userResponse.userInfo.firstName}") {
                        popUpTo(WELCOME_SCREEN) {
                            inclusive = true
                        }
                    }
                })
        }
        composable(route = LOGIN_SCREEN) {
            val context = LocalContext.current
            LoginScreen(
                onLoginSuccess = { loginResponse ->
                    navController.navigate(HOME_SCREEN + "/${loginResponse.userInfo.firstName}") {
                        popUpTo(WELCOME_SCREEN) {
                            inclusive = true
                        }
                    }
                },
                onCreateAccountClick = { navController.navigate(SIGN_UP_SCREEN) },
                onForgetPasswordClick = { navController.navigate(FORGOT_PASSWORD_SCREEN) },
                onFacebookClick = {})
        }
        composable(route = SIGN_UP_SCREEN) {
            val context = LocalContext.current
            SignUpScreen(onSuccessfulSignUp = {signUpResponse ->
                Toast.makeText(context, "verifyToken ${signUpResponse.verifyToken}", Toast.LENGTH_SHORT).show()
            },
                onGoogleClick = { name ->
                    navController.navigate(HOME_SCREEN + "/${name}") {
                        popUpTo(WELCOME_SCREEN) {
                            inclusive = true
                        }
                    }
                },
                onFacebookClick = {},
                onLoginClick = { navController.navigate(LOGIN_SCREEN) })
        }
        composable(route = FORGOT_PASSWORD_SCREEN) {
            val context = LocalContext.current
            ForgotPasswordScreen(
                onOtpClick = { forgotPasswordResponse ->
                    Timber.d("NavControllerComposable: " + forgotPasswordResponse.userInfo.email)
                    navController.navigate(
                        OTP_VERIFICATION_SCREEN + "/${forgotPasswordResponse.verifyToken}" + "/${forgotPasswordResponse.userInfo.email}"
                    )
                },
                onSuccess = { forgotPasswordResponse ->
                    Toast.makeText(context, forgotPasswordResponse.message, Toast.LENGTH_LONG)
                        .show()
                },

                onLoginClick = { navController.navigate(LOGIN_SCREEN) })

        }

        composable(route = "$OTP_VERIFICATION_SCREEN/{token}/{email}") {
            val token = it.arguments?.getString("token")
            val email = it.arguments?.getString("email")

            val context = LocalContext.current
            OTPVerificationScreen(token = token!!,
                email = email!!,
                onResetPassword = {
                    navController.navigate(Route.NEW_PASSWORD_SCREEN + "/$token")
                },
                onSuccess = { verifyOtpResponse ->
                    Toast.makeText(context, verifyOtpResponse.message, Toast.LENGTH_LONG)
                        .show()
                }
            )
        }

        composable(route = "$NEW_PASSWORD_SCREEN/{token}") {
            val token = it.arguments?.getString("token")
            val context = LocalContext.current
            NewPasswordScreen(
                token = token!!,
                OnLoginScreen = { navController.navigate(LOGIN_SCREEN) },
                OnSuccess = { resetPasswordResponse ->
                    Toast.makeText(context, resetPasswordResponse.message, Toast.LENGTH_LONG)
                        .show()
                })
        }
        composable(route = "$HOME_SCREEN/{name}") {
            val name = it.arguments?.getString("name")
            HomeScreen(name = name)
        }
    }
}