package com.hutapp.org.notes.hut.android.utilsAccount

import android.content.Context
import android.util.Log
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.GoogleAuthProvider

class MyGoogleSignIn(context: Context) {
    private var signInClient: GoogleSignInClient? = null
    var account: GoogleSignInAccount? = null

    init {
        signInClient = GoogleSignIn.getClient(context, getGoogleSignInOptions())
        account = GoogleSignIn.getLastSignedInAccount(context)
    }

    fun signOut(onCompleted: () -> Unit) {
        signInClient?.signOut()?.addOnCompleteListener {
            if (it.isSuccessful) {
                onCompleted()
            }
        }
    }

    fun signInIntent() = signInClient?.signInIntent

    private fun getGoogleSignInOptions(): GoogleSignInOptions {
        return GoogleSignInOptions
            .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("971316400360-7glk9k4amrttfvs1c42iircqkn2lri50.apps.googleusercontent.com")
            .requestEmail()
            .build()
    }

    fun googleSignIn(task: Task<GoogleSignInAccount>): AuthCredential? {
        var credential: AuthCredential? = null
        try {
            val result = task.getResult(ApiException::class.java)
            credential = GoogleAuthProvider.getCredential(result.idToken, null)
        } catch (e: Exception) {
            Log.e("TAG1", "googleSignIn: ", e)
        }
        return credential
    }
}