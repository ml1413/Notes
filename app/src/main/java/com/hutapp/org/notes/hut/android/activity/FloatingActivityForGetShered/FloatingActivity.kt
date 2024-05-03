package com.hutapp.org.notes.hut.android.activity.FloatingActivityForGetShered

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hutapp.org.notes.hut.android.R
import com.hutapp.org.notes.hut.android.db.NoteEntity
import com.hutapp.org.notes.hut.android.db.NoteViewModel
import com.hutapp.org.notes.hut.android.ui.myComponent.MyFAB
import com.hutapp.org.notes.hut.android.ui.theme.NotesHutAndroidTheme
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalDate
import java.time.ZoneId
import javax.inject.Inject

@AndroidEntryPoint
class FloatingActivity : ComponentActivity() {
    private var textFromIntent = ""

    @Inject
    lateinit var sharedIsNewEntityIsExistImpl: SharedIsNewEntityIsExistImpl
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        intent?.apply {
            getStringExtra(Intent.EXTRA_TEXT)?.apply {
                textFromIntent = this
            }
        }
        val noteViewModel by viewModels<NoteViewModel>()
        noteViewModel.noteList.observe(this) {
            if (sharedIsNewEntityIsExistImpl.newEntityIsExist())
                finish()
        }


        setContent {
            NotesHutAndroidTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.wrapContentSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    SharedDialog(textFromIntent = textFromIntent,
                        onFABClick = { noteEntity ->
                            // save entity in db
                            noteViewModel.addNoteEntityInDB(noteEntity = noteEntity)
                            //inform another activity new entity isExist
                            sharedIsNewEntityIsExistImpl.entityHasSave(isExist = true)
                        }
                    )
                }
            }
        }
    }
}

