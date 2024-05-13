package com.hutapp.org.notes.my_organization.android.activity.FloatingActivityForGetShered

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.hutapp.org.notes.my_organization.android.db.NoteViewModel
import com.hutapp.org.notes.my_organization.android.ui.theme.NotesHutAndroidTheme
import dagger.hilt.android.AndroidEntryPoint
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

