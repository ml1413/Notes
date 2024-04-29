package com.hutapp.org.notes.hut.android.ui.screens.AddInfoScreen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hutapp.org.notes.hut.android.R

@Composable
@OptIn(ExperimentalMaterial3Api::class)
@Preview(showSystemUi = true, showBackground = true)
 fun MyAlertPermission(
    onDismissRequest: () -> Unit = {},
    onClickButton: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    AlertDialog(onDismissRequest = { onDismissRequest() }) {
        Box(
            modifier = modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Card {
                Column(
                    modifier = modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = stringResource(R.string.need_to_allow_notifications))
                    Button(onClick = {
                        onClickButton()
                    }) {
                        Text(text = stringResource(R.string.open_permission))
                    }
                }
            }
        }
    }
}
