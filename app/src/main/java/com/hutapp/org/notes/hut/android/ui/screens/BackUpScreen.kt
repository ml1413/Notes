package com.hutapp.org.notes.hut.android.ui.screens

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.hutapp.org.notes.hut.android.R
import com.hutapp.org.notes.hut.android.db.NoteViewModel
import com.hutapp.org.notes.hut.android.ui.drawerSheet.MyHeader
import com.hutapp.org.notes.hut.android.ui.myComponent.MyDivider
import com.hutapp.org.notes.hut.android.ui.myComponent.MyOutLineButton
import com.hutapp.org.notes.hut.android.utilsAccount.AccountViewModel
import com.hutapp.org.notes.hut.android.utilsAccount.MyGoogleDriveHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@Composable
fun BackUpScreen(
    modifier: Modifier = Modifier,
    paddingValues: PaddingValues = PaddingValues(),
    accountVewModel: AccountViewModel,
    noteViewModel: NoteViewModel,
    myGoogleDriveHelper: MyGoogleDriveHelper,
    onSaveClickListener: () -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    Column(
        modifier = modifier
            .padding(paddingValues)
            .fillMaxSize()
    ) {
        MyHeader(accountVewModel = accountVewModel)

        MyDivider()
        //save button
        MyOutLineButton(
            painter = painterResource(id = R.drawable.baseline_cloud_upload_24),
            title = stringResource(R.string.save_to_cloud),
            onSaveClickListener = {

                // todo try save json in google drive
                coroutineScope.launch(Dispatchers.IO) {
                    val list = noteViewModel.noteList.value
                    list?.let { listEntity ->
                        myGoogleDriveHelper.deleteFile (deleteIsDone = {
                            Log.d("TAG1", "BackUpScreen: deleteIsDone")
                            myGoogleDriveHelper.addFolderInDrive(
                                createFolderDone = {
                                    Log.d("TAG1", "BackUpScreen: createFolderDone")
                                    myGoogleDriveHelper.saveListInGoogleDrive(
                                        fileName = "listJson.json",
                                        list = listEntity,
                                        type = "application/json"
                                    )
                                    Log.d("TAG1", "BackUpScreen: create File")
                                })
                        })
                    }
                }
            }
        )
        MyDivider()
    }
}

