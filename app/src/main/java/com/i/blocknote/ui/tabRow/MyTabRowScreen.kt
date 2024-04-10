package com.i.blocknote.ui.tabRow

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerScope
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp

@Composable
@OptIn(ExperimentalFoundationApi::class)
fun MyTabRowScreen(
    modifier: Modifier = Modifier,
    tabRowStateViewModel: TabRowStateViewModel,
    paddingValues: PaddingValues,
    pageContent: @Composable PagerScope.(page: Int) -> Unit
) {
//    val tabItems = listOf(
//        stringResource(R.string.note),
//        stringResource(R.string.reminding),
//        stringResource(R.string.bookmarks)
//    )
//    val selectedTabIndex = remember {
//        mutableStateOf(0)
//    }

    val pagerStave = rememberPagerState {
        tabRowStateViewModel.listTabItem.size
    }
    LaunchedEffect(tabRowStateViewModel.selectedItem.value) {
        pagerStave.animateScrollToPage(tabRowStateViewModel.selectedItem.value)
    }
    LaunchedEffect(pagerStave.currentPage, pagerStave.isScrollInProgress) {
        if (!pagerStave.isScrollInProgress) {
            tabRowStateViewModel.selectedItem.value = pagerStave.currentPage
        }
    }
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(paddingValues)
    ) {
        ScrollableTabRow(
            selectedTabIndex = tabRowStateViewModel.selectedItem.value,
            edgePadding = 0.dp
        ) {
            tabRowStateViewModel.listTabItem.forEachIndexed { index, modelTabRowItem ->
                Tab(
                    selected = tabRowStateViewModel.selectedItem.value == index,
                    text = {
                        Text(
                            maxLines = 1,
                            text = stringResource(id = modelTabRowItem.title)
                        )
                    },
                    onClick = {
                        tabRowStateViewModel.selectedItem.value = index
                    })
            }
        }
        HorizontalPager(
            modifier = modifier
                .fillMaxWidth()
                .weight(1f),
            state = pagerStave
        ) { index ->
            pageContent(index)
        }
    }
}