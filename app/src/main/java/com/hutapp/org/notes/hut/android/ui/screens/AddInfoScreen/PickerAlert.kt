package com.hutapp.org.notes.hut.android.ui.screens.AddInfoScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PickerAlert(
    modifier: Modifier = Modifier,
    onDismissRequest: () -> Unit = {},
    onDoneClickListener: () -> Unit = {},
    content: @Composable ColumnScope.() -> Unit = {},
) {
    AlertDialog(
        onDismissRequest = onDismissRequest
    ) {
        Box(
            modifier = modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Card(
                modifier = modifier.padding(16.dp)
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    content()
                    Row() {
                        Button(
                            modifier = modifier.padding(bottom = 16.dp),
                            onClick = {
                                onDismissRequest()
                            }) {
                            Image(
                                imageVector = Icons.Default.Close,
                                contentDescription = null,
                                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.background)
                            )
                        }
                        Spacer(modifier = modifier.width(32.dp))
                        Button(
                            modifier = modifier.padding(bottom = 16.dp),
                            onClick = {
                                onDoneClickListener()
                                onDismissRequest()
                            }) {
                            Image(
                                imageVector = Icons.Default.Done,
                                contentDescription = null,
                                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.background)
                            )
                        }
                    }
                }
            }
        }

    }
}