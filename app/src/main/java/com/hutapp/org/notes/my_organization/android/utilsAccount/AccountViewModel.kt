package com.hutapp.org.notes.my_organization.android.utilsAccount

import android.content.Intent
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
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