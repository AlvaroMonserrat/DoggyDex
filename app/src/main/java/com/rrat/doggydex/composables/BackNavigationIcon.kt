package com.rrat.doggydex.composables

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.stringResource
import com.rrat.doggydex.R

@Composable
fun BackNavigationIcon(onClick: ()->Unit) {
    IconButton(onClick = onClick) {
        Icon(
            painter = rememberVectorPainter(image = Icons.Sharp.ArrowBack),
            contentDescription = stringResource(
                R.string.back_button
            )
        )
    }
}