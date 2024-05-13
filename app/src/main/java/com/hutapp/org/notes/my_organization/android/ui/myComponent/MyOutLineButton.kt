package com.hutapp.org.notes.my_organization.android.ui.myComponent

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp

@Composable
fun MyOutLineButton(
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    onSaveClickListener: () -> Unit,
    color: Color? = null,
    painter: Painter,
    title: String

) {
    OutlinedIconButton(
        modifier = modifier.fillMaxWidth(),
        shape = RectangleShape,
        enabled = enabled,
        border = null,
        onClick = { onSaveClickListener() }) {

        Row (
            modifier = modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Start
        ){
            Image(
                painter = painter,
                contentDescription = null,
                colorFilter = ColorFilter.tint(color ?: MaterialTheme.colorScheme.primary),
                modifier = modifier.padding(start = 24.dp)
            )
            Text(
                modifier = modifier.padding(horizontal = 16.dp),
                color = color ?: MaterialTheme.colorScheme.primary,
                text = title
            )
        }
    }
}