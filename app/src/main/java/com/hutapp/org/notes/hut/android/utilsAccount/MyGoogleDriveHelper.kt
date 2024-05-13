package com.hutapp.org.notes.hut.android.utilsAccount

import android.content.Context
import android.util.Log
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.api.client.extensions.android.http.AndroidHttp
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential
import com.google.api.client.http.FileContent
import com.google.api.client.json.jackson2.JacksonFactory
import com.google.api.services.drive.Drive
import com.google.api.services.drive.DriveScopes
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.hutapp.org.notes.hut.android.R
import com.hutapp.org.notes.hut.android.db.NoteEntity
import java.io.BufferedWriter
import java.io.File
import java.io.FileOutputStream
import java.io.FileWriter

private const val FOLDER_ID = "Note back up folder"
private const val TYPE_JSON = "application/json"
private const val NAME_JSON_FILE = "JsonBackup.json"
private const val TYPE_FOLDER = "application/vnd.google-apps.folder"

class MyGoogleDriveHelper(private val context: Context) {
    private var drive: Drive? = null
    private var parentFolderIdDrive: com.google.api.services.drive.model.File? = null
    private val json: Gson by lazy { Gson() }

    init {
        initializationDrive()
    }

    fun initializationDrive() {
        drive = GoogleSignIn.getLastSignedInAccount(context)?.let { account ->
            Log.d("TAG1", "initializationDrive: ")
            // credential
            val credential =
                GoogleAccountCredential.usingOAuth2(
                    context,
                    listOf(
                        DriveScopes.DRIVE,
                        DriveScopes.DRIVE_FILE
                    )
                ).also {
                    it.selectedAccount = account.account
                }
            //get drive
            Drive.Builder(
                AndroidHttp.newCompatibleTransport(),
                JacksonFactory.getDefaultInstance(),
                credential
            )
                .setApplicationName(context.getString(R.string.app_name))
                .build()
        }
    }

    fun saveList(list: List<NoteEntity>, isDone: () -> Unit = {}) {
        Log.d("TAG1", "saveList: drive $drive")
        // delete folder if exist
        deleteFile(fileName = FOLDER_ID)
        // add new empty folder
        addFolderInDrive()
        // add file in folder
        addListAsJsonInGoogleDrive(list = list)
    }

    fun downloadFileFromDrive(listEntity: (List<NoteEntity>) -> Unit) {
        downloadFile(listEntity = listEntity)
    }


    /**____OTHER FUN__________________________________________*/
    private fun getFolderIdFromDrive(
        drive: Drive,
        fileName: String
    ): com.google.api.services.drive.model.File? {
        // get all folder
        val folders = drive.files().list()
            .setQ("mimeType='application/vnd.google-apps.folder'").execute()
        // find folder
        return folders.files.firstOrNull {
            it.name == fileName
        }
    }

    private fun getFileId(
        drive: Drive,
        fileName: String
    ): com.google.api.services.drive.model.File? {
        val allFiles = drive.files().list()
            .setFields("nextPageToken, files(id, name)")
            .execute()

        return allFiles.files.firstOrNull {
            it.name == fileName
        }

    }

    private fun deleteFile(fileName: String) {
        //deleteFile
        drive?.let { drive ->
            val fileId = getFolderIdFromDrive(
                drive = drive,
                fileName = fileName
            )
            fileId?.let { fileFromDrive ->
                drive.Files().delete(fileFromDrive.id).execute()
            }
            Log.d("TAG1", "deleteFile: ")
        }

    }

    private fun addListAsJsonInGoogleDrive(
        list: List<NoteEntity>
    ) {
        drive?.let { drive ->
            parentFolderIdDrive = getFolderIdFromDrive(drive, FOLDER_ID)
            parentFolderIdDrive?.let { parentFolder ->
                //convert to json
                val listJson = json.toJson(list)
                // file
                val tempFile = createTempFile(NAME_JSON_FILE)
                BufferedWriter(FileWriter(tempFile)).use { out ->
                    out.write(listJson)
                }
                val gFile = com.google.api.services.drive.model.File()
                gFile.name = NAME_JSON_FILE
                gFile.parents = listOf(parentFolder.id)
                //type  "application/pdf"
                val fileContent = FileContent(TYPE_JSON, tempFile)
                drive.Files().create(gFile, fileContent).setFields("id").execute()
                Log.d("TAG1", "addListAsJsonInGoogleDrive: ")
            }
        }
    }


    private fun addFolderInDrive() {
        drive?.let { drive ->
            // find folder id
            parentFolderIdDrive = getFolderIdFromDrive(drive, FOLDER_ID)

            if (parentFolderIdDrive == null) {
                // if folderId null    create folder
                //Create Folder
                val gFolder = com.google.api.services.drive.model.File()
                // Set file name
                gFolder.name = FOLDER_ID
                //Set type  MIME
                gFolder.mimeType = TYPE_FOLDER
                drive.Files().create(gFolder).setFields("id").execute()
                Log.d("TAG1", "addFolderInDrive: ")
            }
        }
    }
    //    fun createFile(pathName: String, type: String) {
//        drive?.let { drive ->
//            parentFolderIdDrive?.let { parentFolder ->
//                //pathName "/storage/emulated/0/Download/2.pdf"
//                val myFileFromPhone = File(pathName)
//
//                val gFile = com.google.api.services.drive.model.File()
//                gFile.name = myFileFromPhone.name
//                gFile.parents = listOf(parentFolder.id)
//                //type  "application/pdf"
//                val fileContent = FileContent(type, myFileFromPhone)
//                drive.Files().update("id", gFile, fileContent).execute()
//            }
//        }
//    }


    private fun downloadFile(filePath: String) {
        // filePath example "/storage/emulated/0/Download/3.pdf"
        drive?.let { drive ->
            val outFile = File("/storage/emulated/0/Download/3.pdf")
            val outputStream = FileOutputStream(outFile)

            val fileId = getFolderIdFromDrive(
                drive = drive,
                fileName = outFile.name
            )

            fileId?.let { fileFromDrive ->

                val inputStream =
                    drive.files().get(fileFromDrive.id).executeMediaAsInputStream()
                inputStream.use { input ->
                    outputStream.use { out ->
                        input.copyTo(out)
                    }
                }
            }
        }
    }

    private fun downloadFile(listEntity: (List<NoteEntity>) -> Unit) {
        drive?.let { drive ->
            //get file from diver
            val fileId = getFileId(drive = drive, fileName = NAME_JSON_FILE)

            fileId?.let { fileFromDrive ->
                // create temp file
                val fileTemp = createTempFile(NAME_JSON_FILE)
                val file = File(fileTemp.path)
                // download file
                FileOutputStream(file).use { outPutStream ->
                    drive.files().get(fileFromDrive.id).executeMediaAndDownloadTo(outPutStream)
                }
                // get json from File
                val fileContent = file.readText()

                val type = object : TypeToken<List<NoteEntity>>() {}.type
                val list = json.fromJson(fileContent, type) as List<NoteEntity>
                listEntity(list)
            }
        }
    }
}