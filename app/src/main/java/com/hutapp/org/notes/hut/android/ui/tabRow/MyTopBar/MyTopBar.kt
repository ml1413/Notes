package com.hutapp.org.notes.hut.android.ui.tabRow.MyTopBar

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.hutapp.org.notes.hut.android.ui.navigation.Screens
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun MyTopBar(
    modifier: Modifier = Modifier,
    currentScreenViewModel: CurrentScreenViewModel,
    coroutineScope: CoroutineScope,
    onBackButtonClickListener: () -> Unit,
    onCalendarClickListener: () -> Unit,
    drawerState: DrawerState
) {
    val titleScreen = currentScreenViewModel.screen.observeAsState(Screens.Initial)
    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
            titleContentColor = MaterialTheme.colorScheme.background,
            actionIconContentColor = MaterialTheme.colorScheme.background,
            navigationIconContentColor = MaterialTheme.colorScheme.background
        ),
        title = {
            Text(
                text = stringResource(
                    id = titleScreen.value.title
                )
            )
        },
        navigationIcon = {
            IconButton(onClick = {
                if (titleScreen.value == Screens.AllNotesScreen) {
                    coroutineScope.launch {
                        drawerState.open()
                    }
                } else {
                    onBackButtonClickListener()
                }

            }) {
                Icon(
                    imageVector =
                    if (titleScreen.value == Screens.AllNotesScreen) Icons.Default.Menu
                    else Icons.Default.ArrowBack, contentDescription = null
                )
            }
        },
        actions = {
            IconButton(onClick = { onCalendarClickListener() }) {
                Icon(imageVector = Icons.Default.DateRange, contentDescription = null)
            }
        })
}