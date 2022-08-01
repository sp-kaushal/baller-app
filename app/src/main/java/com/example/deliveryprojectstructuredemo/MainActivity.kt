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
import com.example.deliveryprojectstructuredemo.common.Route.LOGIN_SCREEN
import com.example.deliveryprojectstructuredemo.common.Route.SIGN_UP_SCREEN
import com.example.deliveryprojectstructuredemo.common.Route.WELCOME_SCREEN
import com.example.deliveryprojectstructuredemo.ui.features.login.LoginScreen
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
        composable(route = LOGIN_SCREEN) {
            LoginScreen(navController = navController)
        }
        composable(route = SIGN_UP_SCREEN) {
            SignUpScreen(navController = navController)
        }
        composable(route = WELCOME_SCREEN) {
            WelcomeScreen(navController = navController)
        }
    }
}