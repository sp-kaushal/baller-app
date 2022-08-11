package com.softprodigy.deliveryapp.ui.features.login

import com.softprodigy.deliveryapp.data.UserInfo


data class LoginUIState(
    val isDataLoading: Boolean = false,
    val errorMessage: String? = null,
    val user: UserInfo? = null,
    )
