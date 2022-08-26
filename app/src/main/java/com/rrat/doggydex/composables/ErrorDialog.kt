package com.rrat.doggydex.composables

import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.rrat.doggydex.R

@Composable
fun ErrorDialog(
    message: String,
    onDialogDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = { },
        title = { Text(stringResource(R.string.error_dialog_title)) },
        text = {
            Text(text = message)
        },
        confirmButton = {
            Button(onClick = { onDialogDismiss() }) {
                Text(text = stringResource(R.string.try_again))
            }
        }
    )

}
