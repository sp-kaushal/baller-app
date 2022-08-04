package com.example.deliveryprojectstructuredemo.ui.features.sign_up

import com.example.deliveryprojectstructuredemo.data.UserInfo


data class SignUpUIState(
    var isLoading: Boolean = false,
    var errorMessage: String? = null,
    var user: UserInfo? = null
)
