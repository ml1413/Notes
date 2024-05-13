package com.hutapp.org.notes.my_organization.android.ui.myComponent

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun MyDivider(modifier :Modifier = Modifier) {
    Divider(
        color = MaterialTheme.colorScheme.primary,
        modifier = modifier.fillMaxWidth(), thickness = 1.dp)
}
