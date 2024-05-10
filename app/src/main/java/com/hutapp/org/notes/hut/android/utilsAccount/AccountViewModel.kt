package com.hutapp.org.notes.hut.android.utilsAccount

import android.content.Intent
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.GoogleAuthProvider
import com.hutapp.org.notes.hut.android.activity.MainActivity
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AccountViewModel @Inject constructor(
    private val myGoogleSignIn: MyGoogleSignIn
) :
    ViewModel() {
    private val _account = MutableLiveData<GoogleSignInAccount?>()
    val account: LiveData<GoogleSignInAccount?> = _account

    init {
        _account.value = myGoogleSignIn.account
    }

    fun setAccount(account: GoogleSignInAccount) {
        _account.value = account
    }

    fun signOut() {
        myGoogleSignIn.signOut() {
            _account.value = null
        }
    }

    fun getIntentForSignIn(intent: (Intent) -> Unit) {
        myGoogleSignIn.signInIntent()?.let {
            intent(it)
        }
    }

}