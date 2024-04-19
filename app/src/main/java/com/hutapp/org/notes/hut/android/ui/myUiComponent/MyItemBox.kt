package com.hutapp.org.notes.hut.android.ui.myUiComponent

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Warning
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
import com.hutapp.org.notes.hut.android.db.NoteEntity
import com.hutapp.org.notes.hut.android.ui.navigation.Screens
import com.hutapp.org.notes.hut.android.ui.tabRow.MyTopBar.CurrentScreenViewModel
import java.time.LocalDate

@Composable
fun MyItemBox(
    modifier: Modifier = Modifier,
    currentScreenViewModel: CurrentScreenViewModel,
    noteEntity: NoteEntity,
    onItemClickListener: (NoteEntity) -> Unit = {},
    onIconButtonClickListener: () -> Unit = {}
) {
    val color = MaterialTheme.colorScheme.primary
    val shape = RoundedCornerShape(16.dp)
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .padding(bottom = 8.dp)
            .clickable { onItemClickListener(noteEntity) },
        elevation = CardDefaults.elevatedCardElevation(2.dp),
        colors = CardDefaults.cardColors(color),
        shape = shape
    ) {
        Box(
            modifier =
            modifier.fillMaxWidth(),
            contentAlignment = Alignment.CenterEnd
        ) {

            Column(
                modifier = modifier
                    .fillMaxWidth()
                    .border(1.dp, shape = shape, color = color)
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
                        Text(text = noteEntity.localDate)
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
                        color = color,
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
                                .background(MaterialTheme.colorScheme.primary),
                            onClick = { onIconButtonClickListener() }) {
                            Icon(
                                imageVector = when (screen) {
                                    Screens.TrashScreen -> Icons.Default.Delete
                                    else -> Icons.Default.Warning
                                },
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
    val noteEntity = NoteEntity(
        id = 1,
        labelNote = "labelNote",
        labelNoteScreen = "labelNoteScreen",
        isDelete = true,
        message = "basdfdsfdsf  sdfsdf fd s",
        localDate = LocalDate.now().toString()
    )
    MyItemBox(currentScreenViewModel = currentScreenViewModel, noteEntity = noteEntity)
}