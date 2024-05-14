package com.hutapp.org.notes.my_organization.android.ui.screens

import android.content.Intent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.hutapp.org.notes.my_organization.android.R
import com.hutapp.org.notes.my_organization.android.db.NoteViewModel
import com.hutapp.org.notes.my_organization.android.notification.AlarmSchedulerImpl
import com.hutapp.org.notes.my_organization.android.notification.ModelAlarmItem
import com.hutapp.org.notes.my_organization.android.ui.drawerSheet.MyHeader
import com.hutapp.org.notes.my_organization.android.ui.myComponent.MyDivider
import com.hutapp.org.notes.my_organization.android.ui.myComponent.MyOutLineButton
import com.hutapp.org.notes.my_organization.android.utilsAccount.AccountViewModel
import com.hutapp.org.notes.my_organization.android.utilsAccount.MyGoogleDriveHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@Composable
fun BackUpScreen(
    modifier: Modifier = Modifier,
    paddingValues: PaddingValues = PaddingValues(),
    accountVewModel: AccountViewModel,
    alarmSchedulerImpl: AlarmSchedulerImpl,
    noteViewModel: NoteViewModel,
    myGoogleDriveHelper: MyGoogleDriveHelper,
    onSaveClickListener: () -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    val launcherDrive =
        rememberLauncherForActivityResult(
            contract = ActivityResultContracts.StartActivityForResult(),
            onResult = { }
        )
    Column(
        modifier = modifier
            .padding(paddingValues)
            .fillMaxSize()
    ) {
        MyHeader(
            accountVewModel = accountVewModel,
            myGoogleDriveHelper = myGoogleDriveHelper
        )

        MyDivider()
        //save button
        MyOutLineButton(
            painter = painterResource(id = R.drawable.baseline_cloud_upload_24),
            title = stringResource(R.string.save_to_cloud),
            onClickListener = {
                coroutineScope.launch(Dispatchers.IO) {
                    saveInDrive(
                        noteViewModel = noteViewModel,
                        myGoogleDriveHelper = myGoogleDriveHelper,
                        intent = { launcherDrive.launch(it) }
                    )
                }
            }
        )
        MyDivider()
        MyOutLineButton(
            painter = painterResource(id = R.drawable.baseline_cloud_download_24),
            title = stringResource(R.string.download_from_cloud),
            onClickListener = {
                coroutineScope.launch(Dispatchers.IO) {
                    downloadFromDrive(
                        noteViewModel = noteViewModel,
                        alarmSchedulerImpl = alarmSchedulerImpl,
                        myGoogleDriveHelper = myGoogleDriveHelper,
                        intent = { launcherDrive.launch(it) }
                    )

                }
            }
        )
        MyDivider()
    }
}

private fun downloadFromDrive(
    noteViewModel: NoteViewModel,
    alarmSchedulerImpl: AlarmSchedulerImpl,
    myGoogleDriveHelper: MyGoogleDriveHelper,
    intent: (Intent) -> Unit = {}
) {
    myGoogleDriveHelper.downloadFileFromDrive(
        listEntity = { listEntity ->
            noteViewModel.insertList(list = listEntity)
            val currentTime = System.currentTimeMillis()
            val listNotification = listEntity.filter {
                it.timeNotification != null
                        && it.timeNotification > currentTime
                        && !it.isDelete
            }
            listNotification.forEach {
                if (it.id != null && it.timeNotification != null) {
                    val modelAlarmItem = ModelAlarmItem(
                        id = it.id,
                        time = it.timeNotification,
                        message = it.labelNote
                    )
                    alarmSchedulerImpl.scheduler(item = modelAlarmItem)
                }
            }
        },
        intent = intent
    )
}

private fun saveInDrive(
    noteViewModel: NoteViewModel,
    myGoogleDriveHelper: MyGoogleDriveHelper,
    intent: (Intent) -> Unit = {}
) {
    val list = noteViewModel.noteList.value
    list?.let { listEntity ->
        myGoogleDriveHelper.saveList(list = listEntity, intent = intent)
    }
}

