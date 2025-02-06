package com.bluepearl.detectdiagnostics

import android.app.AlertDialog
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.bluepearl.detectdiagnostics.ui.theme.FCMNotificationTheme

class MainActivity : ComponentActivity() {


    // Declare the launcher at the top of your Activity/Fragment:
    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission(),
    ) { isGranted: Boolean ->
        if (isGranted) {
            // FCM SDK (and your app) can post notifications.
        } else {
            // TODO: Inform user that that your app will not show notifications.
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Ask for notification permission when the activity is created
        askNotificationPermission()

        setContent {
            FCMNotificationTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }

    @Composable
    @OptIn(ExperimentalMaterial3Api::class)
    private fun askNotificationPermission() {
        // This is only necessary for API level >= 33 (TIRAMISU)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.POST_NOTIFICATIONS) ==
                PackageManager.PERMISSION_GRANTED
            ) {
                // FCM SDK (and your app) can post notifications.
                Toast.makeText(this, "Notification permission granted!", Toast.LENGTH_SHORT).show()
            } else if (shouldShowRequestPermissionRationale(android.Manifest.permission.POST_NOTIFICATIONS)) {
                // TODO: display an educational UI explaining to the user the features that will be enabled
                //       "OK" and "No thanks" buttons. If the user selects "OK," directly request the permission.
                //       by them granting the POST_NOTIFICATION permission. This UI should provide the user
                //       If the user selects "No thanks," allow the user to continue without notifications.
                ShowAlert(
                    "Permission Alert",
                    "To receive important updates, please allow FCM Notification to send notifications",
                    dialogAlert = {

                    },
                    onConfirm = {

                    },
                    onDismiss = {

                    }
                )
            } else {
                // Directly ask for the permission
                requestPermissionLauncher.launch(android.Manifest.permission.POST_NOTIFICATIONS)
            }
        }
    }
}

@Composable
fun Greeting(modifier: Modifier = Modifier) {

    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(
        modifier = modifier.fillMaxSize().padding(16.dp),
    ) {
        Text(
            text = "Firebase Message",
            modifier = modifier.padding(16.dp)
        )

        OutlinedTextField(
            value = username,
            onValueChange = { username = it },
            label = {
                Text("Username")
            },
            modifier = Modifier.fillMaxWidth().padding(16.dp),
        )

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = {
                Text("Password")
            },
            modifier = Modifier.fillMaxWidth().padding(16.dp),
        )

        OutlinedButton(
            onClick = {
                checkLogin(username, password)
            },
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            shape = RoundedCornerShape(8.dp)
        ) {
            Text(
                text = "Login",
            )
        }
    }

}

fun checkLogin(username: String, password: String) {

}

@Composable
fun ShowAlert(
    title: String,
    message: String,
    onDismiss:() -> Unit,
    onConfirm: () -> Unit,
    dialogAlert: () -> Unit,
    ){
    AlertDialog(
        title = {
            Text(title)
        },
        text = {
            Text(message)
        },
        onDismissRequest = {
            dialogAlert()
        },
        confirmButton = {
            onConfirm()
        },
        dismissButton = {
            onDismiss()
        }
    )
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    FCMNotificationTheme {
        val modifier = Modifier.padding(16.dp)
        Greeting(modifier)
    }
}