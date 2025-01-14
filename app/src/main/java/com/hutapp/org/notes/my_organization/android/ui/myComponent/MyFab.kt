package com.hutapp.org.notes.my_organization.android.ui.myComponent

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

@Composable
fun MyFAB(
    modifier: Modifier = Modifier,
    iconForFAB: ImageVector,
    onFABClisk: () -> Unit
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.BottomStart
    ) {
        FloatingActionButton(
            modifier = modifier.padding(16.dp),
            onClick = onFABClisk,
            containerColor = MaterialTheme.colorScheme.primary
        ) {
            Icon(
                imageVector = iconForFAB, contentDescription = null
            )
        }
    }

}