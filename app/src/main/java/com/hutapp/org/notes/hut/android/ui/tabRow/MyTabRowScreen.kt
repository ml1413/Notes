package com.hutapp.org.notes.hut.android.ui.tabRow

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
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.hutapp.org.notes.hut.android.ui.myUiComponent.MyFAB
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
@OptIn(ExperimentalFoundationApi::class)
fun MyTabRowScreen(
    modifier: Modifier = Modifier,
    paddingValues: PaddingValues,
    coroutineScope: CoroutineScope,
    currentPage:(Int)->Unit,
    tabItemList: TabItemList,
    pageContent: @Composable PagerScope.(page: Int) -> Unit,
    onFABClickListener: () -> Unit
) {
    val pagerState = rememberPagerState { tabItemList.listItem.size }
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(paddingValues)
    ) {
        ScrollableTabRow(
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
        HorizontalPager(
            modifier = modifier
                .fillMaxWidth()
                .weight(1f),
            state = pagerState
        ) { index ->
            pageContent(index)
            currentPage(pagerState.currentPage)
        }
    }
    MyFAB(
        iconForFAB = Icons.Default.Add,
        onFABClisk = onFABClickListener
    )
}