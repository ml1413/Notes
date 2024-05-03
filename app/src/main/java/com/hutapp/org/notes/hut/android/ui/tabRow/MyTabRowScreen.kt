package com.hutapp.org.notes.hut.android.ui.tabRow

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerScope
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Divider
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.hutapp.org.notes.hut.android.R
import com.hutapp.org.notes.hut.android.ui.myComponent.MyFAB
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
@OptIn(ExperimentalFoundationApi::class)
fun MyTabRowScreen(
    modifier: Modifier = Modifier,
    paddingValues: PaddingValues,
    idEntity2: (Boolean) -> Int?,
    coroutineScope: CoroutineScope,
    onCurrentPageListener: (Int) -> Unit,
    tabItemList: TabItemList,
    pageContent: @Composable PagerScope.(page: Int) -> Unit,
    onFABClickListener: () -> Unit
) {
    val pagerState = rememberPagerState { tabItemList.listItem.size }
    val labelScreenViewPager = stringResource(id = R.string.reminding)
    idEntity2(true)?.let {
        LaunchedEffect(null) {
            Log.d("TAG1", "MyTabRowScreen: LaunchedEffect")
            pagerState.scrollToPage(
                tabItemList.listItem.let { list ->
                    val model = list.first { it.title == labelScreenViewPager }
                    list.indexOf(model)
                }
            )
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(paddingValues)
    ) {
        ScrollableTabRow(
            divider = {},
            selectedTabIndex = pagerState.currentPage,
            edgePadding = 0.dp
        ) {
            tabItemList.listItem.forEachIndexed { index, modelTabRowItem ->
                Tab(
                    selected = true,
                    text = {
                        Text(
                            maxLines = 1,
                            text = modelTabRowItem.title
                        )
                    },
                    onClick = {
                        coroutineScope.launch { pagerState.animateScrollToPage(index) }
                    })
            }
        }
        Divider()
        HorizontalPager(
            modifier = modifier
                .fillMaxWidth()
                .weight(1f),
            state = pagerState
        ) { index ->
            pageContent(index)
            onCurrentPageListener(pagerState.currentPage)
        }
    }
    MyFAB(
        iconForFAB = Icons.Default.Add,
        onFABClisk = onFABClickListener
    )
}

