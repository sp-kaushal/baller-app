object Compose {
    private const val compose = "1.2.0-alpha05"

    const val compiler = "androidx.compose.compiler:compiler:$compose"
    const val runtime = "androidx.compose.runtime:runtime:$compose"

    private const val activityComposeVersion = "1.4.0"
    const val activityCompose = "androidx.activity:activity-compose:$activityComposeVersion"

    private const val lifecycleVersion = "2.4.1"
    const val viewModelCompose = "androidx.lifecycle:lifecycle-viewmodel-compose:$lifecycleVersion"
    const val viewModelComposeKtx = "androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycleVersion"


    const val lifecycleRuntime= "androidx.lifecycle:lifecycle-runtime-ktx::$lifecycleVersion"

    private const val materialVersion = "1.5.0"
    const val material = "com.google.android.material:material:$materialVersion"

    private const val materialYouVersion = "1.0.0-alpha04"
    const val materialYou = "androidx.compose.material3:material3:$materialYouVersion"


    const val composeUi = "androidx.compose.ui:ui:$compose"
    const val composeMaterial = "androidx.compose.material:material:$compose"
    const val composeUiTooling = "androidx.compose.ui:ui-tooling:$compose"

    private const val navigationVersion = "2.4.1"
    const val navigation = "androidx.navigation:navigation-compose:$navigationVersion"

    private const val hiltNavigationComposeVersion = "1.0.0"
    const val hiltNavigationCompose = "androidx.hilt:hilt-navigation-compose:$hiltNavigationComposeVersion"


}