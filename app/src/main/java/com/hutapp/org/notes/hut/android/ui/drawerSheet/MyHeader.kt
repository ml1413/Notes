package com.hutapp.org.notes.hut.android.ui.drawerSheet

import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.Button
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.ImageLoader
import coil.compose.AsyncImage
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.GoogleAuthProvider
import com.hutapp.org.notes.hut.android.R
import com.hutapp.org.notes.hut.android.utilsAccount.AccountViewModel
import com.hutapp.org.notes.hut.android.utilsAccount.MyGoogleDriveHelper

@Composable
fun MyHeader(
    modifier: Modifier = Modifier,
    context: Context = LocalContext.current,
    accountVewModel: AccountViewModel
) {

    val accountViewModelState = accountVewModel.account.observeAsState()
    val coroutineScope = rememberCoroutineScope()
    val myDrive = remember { MyGoogleDriveHelper(context) }


    val isShowProgress = remember { mutableStateOf(false) }

    val launchSignIn =
        launchGoogleSignin(onCredential = {
            GoogleSignIn.getLastSignedInAccount(context)?.let { account ->
                accountVewModel.setAccount(account)
            }
            isShowProgress.value = false
            Toast.makeText(context, "$it", Toast.LENGTH_SHORT).show()
        })

    Column(
        modifier = modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = modifier
                .height(8.dp),
            contentAlignment = Alignment.TopCenter
        ) {
            if (isShowProgress.value) {
                LinearProgressIndicator(modifier = modifier.fillMaxWidth())
            }
        }
        val image = accountVewModel.account.value?.photoUrl
        Box(
            modifier = modifier.padding(16.dp)
        ) {
            Image(
                modifier = modifier.size(124.dp).alpha(0.5f),
                imageVector = Icons.Default.AccountCircle,
                colorFilter = ColorFilter.tint(Color.LightGray),
                contentDescription = null,
            )
            AsyncImage(
                modifier = modifier
                    .size(124.dp)
                    .clip(shape = CircleShape),
                model = image,
                contentDescription = null,
                imageLoader = ImageLoader(context),
            )
        }
        if (accountViewModelState.value != null) {
            Button(
                modifier = modifier.fillMaxWidth().padding(horizontal = 16.dp),
                onClick = {
                accountVewModel.signOut()
            }) {
                Text(
                    fontWeight = FontWeight.Bold,
                    text = accountVewModel.account.value?.displayName.toString()
                )
                Text(text = stringResource(R.string.signout))
            }
            /***/
//            Button(onClick = {
//                coroutineScope.launch(Dispatchers.IO) {
//                    val folderName = "test name folder"
//                    myDrive.deleteFile(fileName = folderName)
//                }
//                GoogleSignIn.getLastSignedInAccount(context)?.let { account ->
//                    Log.d("TAG1", "MyHeader: account $account")
//                    /**_________________________________________________________________________*/
//                    val credential =
//                        GoogleAccountCredential.usingOAuth2(
//                            context,
//                            listOf(
//                                DriveScopes.DRIVE,
//                                DriveScopes.DRIVE_FILE
//                            )
//                        )
//                    credential.selectedAccount = account.account
//                    Log.d("TAG1", "MyHeader: credential $credential")
//
//                    /**_________________________________________________________________________*/
//                    //get drive
//                    val drive = Drive.Builder(
//                        AndroidHttp.newCompatibleTransport(),
//                        JacksonFactory.getDefaultInstance(),
//                        credential
//                    )
//                        .setApplicationName(context.getString(R.string.app_name))
//                        .build()
//                    Log.d("TAG1", "MyHeader: drive $drive")
//
//                    /**_________________________________________________________________________*/
//                    coroutineScope.launch(Dispatchers.IO) {
////                        // Define a Folder
////                        val gFolder = com.google.api.services.drive.model.File()
////                        // Set file name and MIME
////                        gFolder.name = "My Cool Folder Name"
////                        gFolder.mimeType = "application/vnd.google-apps.folder"
////                        // get all folder
////                        val folders = drive.files().list()
////                            .setQ("mimeType='application/vnd.google-apps.folder'").execute()
////                        // find folder
////                        val parentFolderId = folders.files.firstOrNull {
////                            it.name == "My Cool Folder Name"
////                        }
////                        // put in folder
////                        parentFolderId?.let {
////                            gFolder.parents = listOf(it.id)
////                        }
////
////                        drive.Files().create(gFolder).setFields("id").execute()
//
//                        /**_________________________________________________________________________*/
//                        //createFile
//                        val gFile = com.google.api.services.drive.model.File()
//                        val myFileFromPhone = File("/storage/emulated/0/Download/2.pdf")
//
//                        val fileContent = FileContent("application/pdf", myFileFromPhone)
//                        gFile.name = myFileFromPhone.name
//                        // get all Folder
//                        val allFolders = drive.files().list()
//                            .setQ("mimeType='application/vnd.google-apps.folder'").execute()
//                        val idParentFolder = allFolders.files.firstOrNull {
//                            it.name == "My Cool Folder Name"
//                        }
//                        idParentFolder?.let {
//                            gFile.parents = listOf(it.id)
////                            val isExist =
////                                drive.files().list().execute().files.firstOrNull() { file ->
////                                    file.name == myFileFromPhone.name
////                                }
////                            if (isExist != null) {
//////                                gFile.id = isExist.id
////                                drive.Files().update("id", gFile, fileContent).execute()
////
////                            } else {
////
////                            }
//                            val isExist =
//                                drive.files().list().execute().files.firstOrNull() { file ->
//                                    file.name == myFileFromPhone.name
//                                }
//                            if (isExist == null) {
//                                Log.d("TAG1", "MyHeader:if isExist $isExist")
//                                drive.Files().create(gFile, fileContent).setFields("id").execute()
//                            } else {
//                                Log.d("TAG1", "MyHeader:else isExist $isExist")
////                                //deleteFile
////                                drive.Files().delete(isExist.id).execute()
//                                //_________________________________________________________
//                                //get url file
//
//                                val outFile = File("/storage/emulated/0/Download/3.pdf")
//                                val outputStream = FileOutputStream(outFile)
//                                val inputStream =
//                                    drive.files().get(isExist.id).executeMediaAsInputStream()
//                                inputStream.use { input ->
//                                    outputStream.use { out ->
//                                        input.copyTo(out)
//                                    }
//
//                                }
//                            }
//
//                        }
//
//                    }
//                }

//            }) {
//                Text(text = "save")
//            }
            /***/

        } else {
            Button(
                modifier = modifier.fillMaxWidth().padding(horizontal = 16.dp),
                onClick = {
                accountVewModel.getIntentForSignIn { intent ->
                    launchSignIn.launch(intent)
                }
                isShowProgress.value = true
            }) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        modifier = modifier.size(24.dp),
                        painter = painterResource(id = R.drawable._0230822192910_google__g__logo),
                        contentDescription = null
                    )
                    Text(
                        modifier = modifier.padding(horizontal = 12.dp),
                        text = stringResource(R.string.googlesignin)
                    )
                }
            }
        }

//        if (isSignIn.value && myGoogleSignIn.account != null)
//        Box(
//            modifier = modifier
//                .fillMaxWidth()
//                .clickable { },
//            contentAlignment = Alignment.Center
//        ) {
//            Row(
//                modifier = modifier.padding(16.dp),
//                verticalAlignment = Alignment.CenterVertically
//            ) {
//                Image(
//                    painterResource(id = R.drawable.baseline_cloud_24),
//                    contentDescription = null,
//                    modifier = modifier.padding(horizontal = 16.dp)
//                )
//                Text(text = stringResource(R.string.synchronization_and_backup))
//            }
//        }
    }
}

@Composable
fun launchGoogleSignin(onCredential: (AuthCredential?) -> Unit)
        : ManagedActivityResultLauncher<Intent, ActivityResult> {
    val launcher =
        rememberLauncherForActivityResult(
            contract = ActivityResultContracts.StartActivityForResult(),
            onResult = { activityResult ->
                if (activityResult.resultCode == ComponentActivity.RESULT_OK) {
                    val intentData = activityResult.data
                    intentData?.let {
                        val task = GoogleSignIn.getSignedInAccountFromIntent(it)

                        val credential = try {
                            val result = task.getResult(ApiException::class.java)
                            GoogleAuthProvider.getCredential(result.idToken, null)
                        } catch (e: Exception) {
                            Log.e("TAG1", "googleSignIn: ", e)
                            null
                        }
                        onCredential(credential)
                    }
                }


            }
        )
    return launcher
}


