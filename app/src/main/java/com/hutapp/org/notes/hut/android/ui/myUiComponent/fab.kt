package com.hutapp.org.notes.hut.android.ui.myUiComponent

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.hutapp.org.notes.hut.android.ui.navigation.Screens

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
            onClick = onFABClisk
        ) {
            Icon(
                imageVector = iconForFAB, contentDescription = null
            )
        }
    }

}