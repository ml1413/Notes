package com.hutapp.org.notes.hut.android.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.hutapp.org.notes.hut.android.ui.drawerSheet.MyHeader

@Composable
@Preview(showSystemUi = true, showBackground = true)
fun BackUpScreen(
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxSize()) {
        MyHeader()
    }
}