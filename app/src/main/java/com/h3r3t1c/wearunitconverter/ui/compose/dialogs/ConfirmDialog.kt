package com.h3r3t1c.wearunitconverter.ui.compose.dialogs

import androidx.compose.runtime.Composable
import androidx.wear.compose.material3.AlertDialog
import androidx.wear.compose.material3.AlertDialogDefaults
import androidx.wear.compose.material3.Text

@Composable
fun ConfirmDialog(visible: Boolean, title: String, text: String, onDismiss: () -> Unit, onConfirm: () -> Unit){
    AlertDialog(
        visible = visible,
        onDismissRequest = onDismiss,
        title = {
            Text(title)
        },
        text = {
            Text(text)
        },
        confirmButton = {
            AlertDialogDefaults.ConfirmButton(
                onClick = onConfirm,
            )
        },
        dismissButton = {
            AlertDialogDefaults.DismissButton(
                onClick = onDismiss,
            )
        }
    )
}