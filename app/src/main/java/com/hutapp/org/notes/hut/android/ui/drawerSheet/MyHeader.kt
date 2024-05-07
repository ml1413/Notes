package com.hutapp.org.notes.hut.android.ui.drawerSheet

import android.content.Intent
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.ImageLoader
import coil.compose.AsyncImage
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.tasks.Task
import com.hutapp.org.notes.hut.android.R
import com.hutapp.org.notes.hut.android.utilsAccount.MyFirebaseSignInOut
import com.hutapp.org.notes.hut.android.utilsAccount.MyGoogleSignIn

@Composable
@Preview(showSystemUi = true, showBackground = true)
fun MyHeader(
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current
    val myGoogleSignIn = MyGoogleSignIn(context)
    val isSignIn = remember { mutableStateOf(myGoogleSignIn.account != null) }
    val isShowProgress = remember { mutableStateOf(false) }
    val launcher = launchGoogleSignIn() { task ->
        val credential = myGoogleSignIn.googleSignIn(task)
        credential?.let { authCredential ->
            MyFirebaseSignInOut.fireBaseSignIn(
                credential = authCredential,
                onComplete = { isComplete ->
                    isSignIn.value = isComplete
                    isShowProgress.value = false
                })
        }
    }
    Column(
        modifier = modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val image = myGoogleSignIn.account?.photoUrl
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
        if (isSignIn.value && myGoogleSignIn.account != null) {
            Button(onClick = {
                myGoogleSignIn.signOut {
                    MyFirebaseSignInOut.signOut()
                    isSignIn.value = false
                }
            }) {
                Text(
                    fontWeight = FontWeight.Bold,
                    text = myGoogleSignIn.account?.displayName.toString()
                )
                Text(text = stringResource(R.string.signout))
            }

        } else {
            Button(onClick = {
                myGoogleSignIn.signInIntent()?.let { signInIntent ->
                    launcher.launch(signInIntent)
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

@Composable
private fun launchGoogleSignIn(
    onTask: (Task<GoogleSignInAccount>) -> Unit
): ManagedActivityResultLauncher<Intent, ActivityResult> {
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult(),
        onResult = { activityResult ->
            if (activityResult.resultCode == ComponentActivity.RESULT_OK) {
                val intent = activityResult.data
                intent?.let {
                    val task = GoogleSignIn.getSignedInAccountFromIntent(it)
                    onTask(task)
                }
            }

        }
    )
    return launcher
}


