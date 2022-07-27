package com.example.deliveryprojectstructuredemo.ui.features

import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.example.deliveryprojectstructuredemo.R

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
            Button(
                onClick = { onDelete(item) }
            ) {
                Text(stringResource(R.string.dialog_button_confirm))
            }
        },
        dismissButton = {
            Button(
                onClick = onDismiss
            ) {
                Text(stringResource(R.string.dialog_button_cancel))
            }
        }
    )
}