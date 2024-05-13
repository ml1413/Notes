package com.hutapp.org.notes.my_organization.android.utilsAccount

import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth

object MyFirebaseSignInOut {
    fun fireBaseSignIn(
        credential: AuthCredential,
        onComplete: (Boolean) -> Unit
    ) {
        val firebaseAuth = FirebaseAuth.getInstance()
        firebaseAuth.signInWithCredential(credential)
            .addOnCompleteListener { taskSuccess ->
                onComplete(taskSuccess.isSuccessful)
            }
    }
    fun signOut() {
        FirebaseAuth.getInstance().signOut()
    }

}