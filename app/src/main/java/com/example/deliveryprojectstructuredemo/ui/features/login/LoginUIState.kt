package com.example.deliveryprojectstructuredemo.ui.features.login

import com.example.deliveryprojectstructuredemo.data.UserInfo


data class LoginUIState(
    val isDataLoading: Boolean = false,
    val errorMessage: String? = null,
    val user: UserInfo? = null,
    )
