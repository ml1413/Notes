package com.hutapp.org.notes.my_organization.android.ui.tabRow

import android.content.Context
import com.hutapp.org.notes.my_organization.android.R

class TabItemList(context: Context) {
    val listItem: List<ModelTabRowItem> = listOf(
        ModelTabRowItem(
            title = context.resources.getString(R.string.note),
        ),
        ModelTabRowItem(
            title = context.resources.getString(R.string.reminding),
        ),
        ModelTabRowItem(
            title = context.resources.getString(R.string.bookmarks),
        ),
    )
}