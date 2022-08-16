package com.softprodigy.deliveryapp.ui.features.welcome

import com.softprodigy.deliveryapp.data.GoogleUserModel

sealed class WelcomeUIEvent {
    data class OnGoogleClick(val googleUser:GoogleUserModel) : WelcomeUIEvent()
}
