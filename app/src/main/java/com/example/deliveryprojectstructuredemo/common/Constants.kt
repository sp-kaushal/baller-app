package com.example.deliveryprojectstructuredemo.common

import com.google.gson.annotations.SerializedName

object AppConstants {
    const val ANDROID="android"
    const val KEY_PASSWORD = "password"
    const val KEY_USERS = "users"
}

object Route {
    const val WELCOME_SCREEN = "welcomeScreen"
    const val LOGIN_SCREEN = "loginScreen"
    const val SIGN_UP_SCREEN = "signupScreen"
    const val FORGOT_PASSWORD_SCREEN = "forgotPasswordScreen"
    const val OTP_VERIFICATION_SCREEN = "otpVerificationScreen"
    const val NEW_PASSWORD_SCREEN = "newPasswordScreen"
}

object ApiConstants {
    const val first_name="first_name"
    const val last_name="lastName"
    const val mobile="mobile"
    const val email="email"
    const val token="token"
    const val LOGIN="/login"
    const val SIGNUP="/register"
}