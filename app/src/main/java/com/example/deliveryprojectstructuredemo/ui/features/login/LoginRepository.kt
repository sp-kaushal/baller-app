package com.example.deliveryprojectstructuredemo.ui.features.login

import com.example.deliveryprojectstructuredemo.data.response.GoogleLoginResponse
import com.example.deliveryprojectstructuredemo.data.response.LoginResponse

interface LoginRepository {
    suspend fun loginWithEmailAndPass(email: String, password: String): Result<LoginResponse>
    suspend fun loginWithGoogle(googleUser: String): Result<GoogleLoginResponse>
}