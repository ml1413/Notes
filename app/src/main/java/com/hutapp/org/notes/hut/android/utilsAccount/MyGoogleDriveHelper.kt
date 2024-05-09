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
import com.hutapp.org.notes.hut.android.R
import java.io.File
import java.io.FileOutputStream

class MyGoogleDriveHelper(private val context: Context) {
    private var drive: Drive? = null
    private var parentFolderidDrive: com.google.api.services.drive.model.File? = null

    init {
        drive = GoogleSignIn.getLastSignedInAccount(context)?.let { account ->
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

    fun deleteFile(fileName: String) {
        //deleteFile
        drive?.let { drive ->
            val fileId = getFileIdFromDrive(
                drive = drive,
                fileName = fileName
            )
            fileId?.let { fileFromDrive ->
                drive.Files().delete(fileFromDrive.id).execute()
            }

        }

    }

    fun createFile(pathName: String, type: String) {
        drive?.let { drive ->
            parentFolderidDrive?.let { parentFolder ->
                //pathName "/storage/emulated/0/Download/2.pdf"
                val myFileFromPhone = File(pathName)

                val gFile = com.google.api.services.drive.model.File()
                gFile.name = myFileFromPhone.name
                gFile.parents = listOf(parentFolder.id)
                //type  "application/pdf"
                val fileContent = FileContent(type, myFileFromPhone)
                drive.Files().update("id", gFile, fileContent).execute()
            }
        }
    }

    fun addFolderInDrive(folderName: String) {
        drive?.let { drive ->
            // find folder id
            parentFolderidDrive = getFileIdFromDrive(drive, folderName)

            //Create Folder
            val gFolder = com.google.api.services.drive.model.File()
            // Set file name
            gFolder.name = folderName
            //Set type  MIME
            gFolder.mimeType = "application/vnd.google-apps.folder"

            if (parentFolderidDrive == null) {
                // if folderId null    create folder
                drive.Files().create(gFolder).setFields("id").execute()
            }
        }
    }

    fun downloadFile(filePath: String) {
        // filePath example "/storage/emulated/0/Download/3.pdf"
        drive?.let { drive ->
            val outFile = File("/storage/emulated/0/Download/3.pdf")
            val outputStream = FileOutputStream(outFile)

            val fileId = getFileIdFromDrive(
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

    /**____OTHER FUN__________________________________________*/
    private fun getFileIdFromDrive(
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
}