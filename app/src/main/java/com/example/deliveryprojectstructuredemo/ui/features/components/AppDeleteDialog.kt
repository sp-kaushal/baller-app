package com.example.deliveryprojectstructuredemo.ui.features.components

import android.content.res.Configuration
import androidx.compose.foundation.layout.padding
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.deliveryprojectstructuredemo.R
import com.example.deliveryprojectstructuredemo.ui.theme.DeliveryProjectStructureDemoTheme

@Composable
fun <T> DeleteDialog(
    item: T,
    message: String,
    title: @Composable (() -> Unit)? = null,
    onDelete: (T) -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = title,
        text = { Text(text = message) },
        confirmButton = {
            AppButton(
                onClick = { onDelete(item) }
            ) {
                AppText(stringResource(R.string.dialog_button_confirm))
            }
        },
        dismissButton = {
            AppButton(
                onClick = onDismiss
            ) {
                AppText(stringResource(R.string.dialog_button_cancel))
            }
        }
    )
}

@OptIn(ExperimentalMaterialApi::class)
@Preview("default", "round")
@Preview("dark theme", "round", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview("large font", "round", fontScale = 2f)
@Composable
private fun ButtonPreview() {
    DeliveryProjectStructureDemoTheme() {
        DeleteDialog(
            item = "Delete Item",
            title = {
                AppText(text = "Delete title")
            },
            message = "Do you really want to kill me?",
            onDelete = {

            }) {
        }
    }
}