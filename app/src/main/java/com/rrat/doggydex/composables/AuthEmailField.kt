package com.rrat.doggydex.composables

import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.VisualTransformation

@Composable
fun AuthEmailField(
    modifier: Modifier = Modifier,
    label: String,
    email: String,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    onTextChanged: (String) -> Unit
) {
    OutlinedTextField(
        modifier = modifier,
        label = { Text(text = label) },
        value = email,
        visualTransformation = visualTransformation,
        onValueChange = { onTextChanged(it) }
    )
}
