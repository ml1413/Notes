package com.i.blocknote.ui.drawerSheet

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.DrawerState
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.i.blocknote.ui.navigation.Screens
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun MyDrawerSheet(
    modifier: Modifier,
    drawerItemStateViewModel: DrawerItemStateViewModel,
    coroutineScope: CoroutineScope,
    drawerState: DrawerState,
    onItemDrawMenuListener: (Screens) -> Unit
) {
    drawerItemStateViewModel.selectedItemDrawState.apply {
        if (value == null) {
            value = drawerItemStateViewModel.listMenu[0]
        }
    }
    ModalDrawerSheet {
        LazyColumn {
            items(drawerItemStateViewModel.listMenu) { modelDrawerItem ->
                NavigationDrawerItem(
                    modifier = modifier.padding(horizontal = 12.dp),
                    icon = {
                        Icon(
                            imageVector = Icons.Default.KeyboardArrowRight,
                            contentDescription = null
                        )
                    },
                    label = {
                        Text(text = stringResource(id = modelDrawerItem.title))
                    },
                    selected = modelDrawerItem == drawerItemStateViewModel.selectedItemDrawState.value,
                    onClick = {
                        coroutineScope.launch { drawerState.close() }
                        onItemDrawMenuListener(modelDrawerItem.screens)
                    })
            }
        }
    }
}
