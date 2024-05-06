package com.hutapp.org.notes.hut.android.ui.drawerSheet

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.ImageLoader
import coil.compose.AsyncImage
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.hutapp.org.notes.hut.android.R

@Composable
@Preview(showSystemUi = true, showBackground = true)
fun MyHeader(
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current
    val signIsOptions = getGoogleSignInOptions()
    val signInClient = GoogleSignIn.getClient(context, signIsOptions)
    val account = GoogleSignIn.getLastSignedInAccount(context)
    val isSignIn = remember { mutableStateOf(account != null) }
    val isShowProgress = remember { mutableStateOf(false) }
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult(),
        onResult = { activityResult ->
            if (activityResult.resultCode == ComponentActivity.RESULT_OK) {
                val intent = activityResult.data
                intent?.let {
                    val task = GoogleSignIn.getSignedInAccountFromIntent(it)
                    val credential = googleSignIn(task, context)
                    credential?.let { authCredential ->
                        fireBaseSignIn(
                            credential = authCredential,
                            onComplete = { isComplete ->
                                isSignIn.value = isComplete
                                isShowProgress.value = false
                            })
                    }
                }

            }

        }
    )
    Column(
        modifier = modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val image = account?.photoUrl
        Box(
            modifier = modifier.padding(top = 16.dp)
        ) {
            Image(
                modifier = modifier.size(124.dp),
                imageVector = Icons.Default.AccountCircle,
                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary),
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
        if (isSignIn.value && account != null) {
            Button(onClick = {
                signInClient.signOut().addOnCompleteListener {
                    if (it.isSuccessful) {
                        FirebaseAuth.getInstance().signOut()
                        isSignIn.value = false
                    }
                }
            }) {
                Text(
                    fontWeight = FontWeight.Bold,
                    text = account.displayName.toString()
                )
                Text(text = "  SignOut")
            }

        } else {
            Button(onClick = {
                launcher.launch(signInClient.signInIntent)
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
                        text = "GoogleSignIn"
                    )
                }
            }
        }
        Box(modifier = modifier.padding(vertical = 16.dp)) {
            Divider(
                modifier = modifier
                    .fillMaxWidth(),
                thickness = 1.dp
            )
            if (isShowProgress.value)
                LinearProgressIndicator(modifier = modifier.fillMaxWidth())
        }
    }

}

private fun getGoogleSignInOptions(): GoogleSignInOptions {
    return GoogleSignInOptions
        .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        .requestIdToken("971316400360-7glk9k4amrttfvs1c42iircqkn2lri50.apps.googleusercontent.com")
        .requestEmail()
        .build()
}

private fun googleSignIn(
    task: Task<GoogleSignInAccount>,
    context: Context
): AuthCredential? {
    var credential: AuthCredential? = null
    try {
        val result = task.getResult(ApiException::class.java)
        credential = GoogleAuthProvider.getCredential(result.idToken, null)
    } catch (e: Exception) {
        Log.e("TAG1", "googleSignIn: ", e)
        Toast.makeText(
            context,
            e.message,
            Toast.LENGTH_SHORT
        )
            .show()
    }
    return credential
}

private fun fireBaseSignIn(
    credential: AuthCredential,
    onComplete: (Boolean) -> Unit
) {
    val firebaseAuth = FirebaseAuth.getInstance()
    firebaseAuth.signInWithCredential(credential)
        .addOnCompleteListener { taskSuccess ->
            onComplete(taskSuccess.isSuccessful)
        }
}
