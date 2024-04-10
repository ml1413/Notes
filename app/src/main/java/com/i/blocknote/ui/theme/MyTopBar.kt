package com.i.blocknote.ui.theme

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.i.blocknote.ui.drawerSheet.DrawerItemStateViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun MyTopBar(
    modifier: Modifier = Modifier,
    drawerItemStateViewModel: DrawerItemStateViewModel,
    coroutineScope: CoroutineScope,
    drawerState: DrawerState
) {
    TopAppBar(
        title = {
            //todo need refactor
            Text(
                text = stringResource(
                    id = drawerItemStateViewModel.selectedItemDrawState.value?.title ?: 0
                )
            )
        },
        navigationIcon = {
            IconButton(onClick = {
                coroutineScope.launch {
                    drawerState.open()
                }
            }) {
                Icon(imageVector = Icons.Default.Menu, contentDescription = null)
            }
        })
}