package com.example.deliveryprojectstructuredemo.ui.features.create_new_password

import com.example.deliveryprojectstructuredemo.data.UserInfo

data class ResetPasswordUIState(
    var isLoading: Boolean = false,
    var errorMessage: String? = null,
    var message: String? = null,
    var user: UserInfo? = null,
    var isPasswordError: Boolean? = null,
)