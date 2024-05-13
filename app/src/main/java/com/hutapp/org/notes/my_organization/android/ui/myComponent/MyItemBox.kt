package com.hutapp.org.notes.my_organization.android.ui.myComponent

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.hutapp.org.notes.my_organization.android.db.NoteEntity
import com.hutapp.org.notes.my_organization.android.ui.navigation.Screens
import com.hutapp.org.notes.my_organization.android.ui.tabRow.MyTopBar.CurrentScreenViewModel
import java.time.LocalDate
import java.time.ZoneId

@Composable
fun MyItemBox(
    modifier: Modifier = Modifier,
    idEntity2: (Boolean) -> Int? = { null },
    currentScreenViewModel: CurrentScreenViewModel,
    noteEntity: NoteEntity,
    interactionSource: MutableInteractionSource = MutableInteractionSource(),
    onItemClickListener: (NoteEntity) -> Unit = {},
    onIconButtonClickListener: () -> Unit = {}
) {
    val shape = RoundedCornerShape(16.dp)

    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .padding(bottom = 8.dp),
        elevation = CardDefaults.elevatedCardElevation(8.dp),
        colors = CardDefaults.cardColors(MaterialTheme.colorScheme.secondaryContainer),
        shape = shape
    ) {
        Box(
            modifier =
            modifier
                .fillMaxWidth()
                .clickable(
                    interactionSource =
                    if (idEntity2(true) == noteEntity.id) {
                        idEntity2(false)
                        interactionSource
                    } else {
                        MutableInteractionSource()
                    },
                    indication = rememberRipple(
                        bounded = true
                    )
                ) { onItemClickListener(noteEntity) },
            contentAlignment = Alignment.CenterEnd
        ) {

            Column(
                modifier = modifier
                    .fillMaxWidth()
            ) {
                Box(
                    modifier = modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    Box(
                        modifier = modifier
                            .fillMaxWidth()
                            .padding(start = 8.dp)
                    ) {
                        Text(text = noteEntity.addNoteDate)
                    }
                    Text(
                        modifier = modifier.padding(8.dp),
                        text = maxLengthString(string = noteEntity.labelNote, maxLength = 10),
                        maxLines = 1,
                        fontWeight = FontWeight.Bold
                    )

                }
                Box(
                    modifier =
                    modifier
                        .fillMaxWidth()
                        .background(MaterialTheme.colorScheme.background)
                ) {
                    Text(
                        modifier =
                        modifier.padding(8.dp),
                        maxLines = 1,
                        text = maxLengthString(string = noteEntity.message, maxLength = 15)
                    )
                }
            }
            val screen = currentScreenViewModel.screen.value
            screen?.let {
                if (it == Screens.TrashScreen)
                    Box(
                        modifier = modifier,
                    ) {
                        IconButton(
                            modifier = modifier
                                .padding(8.dp)
                                .clip(shape = CircleShape)
                                .border(
                                    1.dp,
                                    color = MaterialTheme.colorScheme.background,
                                    shape = CircleShape
                                )
                                .background(MaterialTheme.colorScheme.background),
                            onClick = { onIconButtonClickListener() }) {
                            Icon(
                                imageVector = when (screen) {
                                    Screens.TrashScreen -> Icons.Default.Delete
                                    else -> Icons.Default.Warning
                                },
                                tint = MaterialTheme.colorScheme.primary,
                                contentDescription = null
                            )

                        }
                    }
            }
        }
    }
}

private fun maxLengthString(string: String, maxLength: Int): String {
    return if (string.length > maxLength) string.substring(0, maxLength) + "..."
    else string
}

@Composable
@Preview(showBackground = true, showSystemUi = true)
private fun Preview(
    modifier: Modifier = Modifier,
    onItemClickListener: (NoteEntity) -> Unit = {},
    onIconButtonClickListener: () -> Unit = {},
    currentScreenViewModel: CurrentScreenViewModel = viewModel(),
) {
    val scrollState = rememberLazyListState()

    val noteEntity = NoteEntity(
        id = 1,
        labelNote = "labelNote",
        labelNoteScreen = "labelNoteScreen",
        isDelete = true,
        message = "basdfdsfdsf  sdfsdf fd s",
        addNoteDate = LocalDate.now(ZoneId.systemDefault()).toString()
    )
    MyItemBox(
        currentScreenViewModel = currentScreenViewModel, noteEntity = noteEntity,
        interactionSource = MutableInteractionSource()
    )
}