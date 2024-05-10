package com.hutapp.org.notes.hut.android.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.hutapp.org.notes.hut.android.R
import com.hutapp.org.notes.hut.android.ui.drawerSheet.MyHeader
import com.hutapp.org.notes.hut.android.ui.myComponent.MyDivider
import com.hutapp.org.notes.hut.android.utilsAccount.AccountViewModel


@Composable
fun BackUpScreen(
    modifier: Modifier = Modifier,
    paddingValues: PaddingValues = PaddingValues(),
    accountVewModel: AccountViewModel,
    onSaveClickListener: () -> Unit
) {
    Column(
        modifier = modifier
            .padding(paddingValues)
            .fillMaxSize()
    ) {
        MyHeader(accountVewModel = accountVewModel)

        MyDivider()
        //save button
        MyOutLineButton(
            painter = painterResource(id = R.drawable.baseline_cloud_upload_24),
            title = stringResource(R.string.save_to_cloud),
            onSaveClickListener = { onSaveClickListener() }
        )

    }
}

@Composable
private fun MyOutLineButton(
    modifier: Modifier = Modifier,
    onSaveClickListener: () -> Unit,
    painter: Painter,
    title: String

) {
    OutlinedIconButton(
        modifier = modifier.fillMaxWidth(),
        shape = RectangleShape,
        border = null,
        onClick = { onSaveClickListener() }) {

        Row {
            Image(
                painter = painter,
                contentDescription = null,
                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary),
                modifier = modifier.padding(horizontal = 16.dp)
            )
            Text(
                color = MaterialTheme.colorScheme.primary,
                text = title
            )
        }
    }
}