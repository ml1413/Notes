@file:OptIn(ExperimentalMaterial3Api::class)

package com.hutapp.org.notes.hut.android.alert

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hutapp.org.notes.hut.android.R

@Composable
@Preview(showSystemUi = true, showBackground = true)
fun MyAlert(
    modifier: Modifier = Modifier,
    onDismiss: () -> Unit = {},
    onDelete: () -> Unit = {},
    onRestore: () -> Unit = {}
) {


    AlertDialog(
        onDismissRequest = onDismiss
    ) {
        Box(
            modifier = modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Row(
                modifier = modifier
                    .clip(shape = RoundedCornerShape(16.dp))
                    .background(MaterialTheme.colorScheme.background)

            ) {
                Button(
                    modifier = modifier.padding(16.dp),
                    onClick = {
                        onDismiss()
                        onRestore()
                    }) {
                    Text(
                        text = stringResource(id = R.string.restore),
                        fontWeight = FontWeight.Bold
                    )
                }
                Button(
                    modifier = modifier.padding(16.dp),
                    onClick = {
                        onDismiss()
                        onDelete()
                    }) {
                    Text(
                        text = stringResource(id = R.string.delete),
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}
