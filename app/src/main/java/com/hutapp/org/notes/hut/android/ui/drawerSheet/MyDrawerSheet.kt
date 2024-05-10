package com.hutapp.org.notes.hut.android.ui.drawerSheet

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Divider
import androidx.compose.material3.DrawerState
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.hutapp.org.notes.hut.android.R
import com.hutapp.org.notes.hut.android.ui.myComponent.MyDivider
import com.hutapp.org.notes.hut.android.ui.navigation.Screens
import com.hutapp.org.notes.hut.android.utilsAccount.AccountViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun MyDrawerSheet(
    modifier: Modifier,
    drawerItemStateViewModel: DrawerItemStateViewModel,
    coroutineScope: CoroutineScope,
    accountVewModel: AccountViewModel,
    drawerState: DrawerState,
    onItemDrawMenuListener: (Screens) -> Unit,
) {

    ModalDrawerSheet {
        MyHeader(accountVewModel = accountVewModel)

        MyDivider()

        MyOutLineButtonDrawer(
            accountVewModel = accountVewModel,
            onButtonClickListener = {
                coroutineScope.launch { drawerState.close() }
                onItemDrawMenuListener(Screens.BackUpScreen)
            }
        )

        MyDivider()

        LazyColumn(contentPadding = PaddingValues(vertical = 16.dp)) {
            items(drawerItemStateViewModel.listMenu) { screen ->
                NavigationDrawerItem(
                    modifier = modifier.padding(horizontal = 12.dp),
                    icon = {
                        Icon(
                            imageVector = Icons.Default.KeyboardArrowRight,
                            contentDescription = null
                        )
                    },
                    label = {
                        Text(text = stringResource(id = screen.title))
                    },
                    selected = screen == drawerItemStateViewModel.selectedItemDrawState.value,
                    onClick = {
                        coroutineScope.launch { drawerState.close() }
                        onItemDrawMenuListener(screen)
                    })
            }
        }
    }
}

@Composable
private fun MyOutLineButtonDrawer(
    modifier: Modifier = Modifier,
    accountVewModel: AccountViewModel,
    onButtonClickListener: () -> Unit
) {
    val accountViewModelState = accountVewModel.account.observeAsState()

    OutlinedIconButton(
        modifier = modifier.fillMaxWidth(),
        shape = RectangleShape,
        border = null,
        enabled = accountViewModelState.value != null,
        onClick = { onButtonClickListener() }) {
        val isEnabledColor = if (accountViewModelState.value != null) {
            MaterialTheme.colorScheme.primary
        } else {
            Color.LightGray
        }
        Row {
            Image(
                painterResource(id = R.drawable.baseline_cloud_24),
                contentDescription = null,
                colorFilter = ColorFilter.tint(isEnabledColor),
                modifier = modifier.padding(horizontal = 16.dp)
            )
            Text(
                color = isEnabledColor,
                text = stringResource(R.string.synchronization_and_backup)
            )
        }
    }
}

