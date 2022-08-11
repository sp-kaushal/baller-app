package com.softprodigy.deliveryapp.ui.features.sign_up

import com.softprodigy.deliveryapp.data.UserInfo


data class SignUpUIState(
    var isLoading: Boolean = false,
    var errorMessage: String? = null,
    var user: UserInfo? = null
)
