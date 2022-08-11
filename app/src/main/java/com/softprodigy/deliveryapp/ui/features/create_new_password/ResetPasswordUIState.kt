package com.softprodigy.deliveryapp.ui.features.create_new_password

import com.softprodigy.deliveryapp.data.UserInfo


data class ResetPasswordUIState(
    var isLoading: Boolean = false,
    var errorMessage: String? = null,
    var message: String? = null,
    var user: UserInfo? = null,
    var isPasswordError: Boolean? = null,
)