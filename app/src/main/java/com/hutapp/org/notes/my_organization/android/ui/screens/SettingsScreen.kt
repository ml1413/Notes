package com.hutapp.org.notes.my_organization.android.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun SettingsScreen(
    modifier: Modifier = Modifier,
    paddingValues: PaddingValues
) {
    Box (modifier = modifier
        .fillMaxSize()
        .padding(paddingValues),
        contentAlignment = Alignment.Center){
        Text(text = "SettingsScreen")
    }
}