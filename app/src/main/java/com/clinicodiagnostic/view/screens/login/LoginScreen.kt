package com.clinicodiagnostic.view.screens.login

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.provider.Settings
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.clinicodiagnostic.R
import com.clinicodiagnostic.view.component.Alert.Alert

@Composable
fun LoginScreen(navHostController: NavHostController){

    val context = LocalContext.current
    val activity = context as? Activity
    val loginViewModel: LoginViewModel = viewModel()
    val username by loginViewModel.username.observeAsState("")
    val password by loginViewModel.password.observeAsState("")
    val permissionAlert by loginViewModel.permissionAlert.observeAsState(false)

    val requestPermissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission(),
    ) { isGranted: Boolean ->
        if (isGranted) {
            Toast.makeText(context, "Permission Granted", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context, "Permission Denied", Toast.LENGTH_SHORT).show()
        }
    }

    LaunchedEffect (Unit){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(context, android.Manifest.permission.POST_NOTIFICATIONS) ==
                PackageManager.PERMISSION_GRANTED
            ) {
                // FCM SDK can post notifications.
                Toast.makeText(context, "Notification permission granted!", Toast.LENGTH_SHORT).show()
            } else if (activity?.shouldShowRequestPermissionRationale(android.Manifest.permission.POST_NOTIFICATIONS) == true) {
                loginViewModel.setPermissionAlert(true)
            } else {
                // Directly ask for the permission
                requestPermissionLauncher.launch(android.Manifest.permission.POST_NOTIFICATIONS)
            }
        }
    }

    if(permissionAlert){
        Alert(
            icon = ImageVector.vectorResource(R.drawable.notifications),
            title = "Push Notifications",
            message = "Stay informed with order updates, promotional offers, and platform communication.",
            confirmText = "Allow Notification",
            onConfirm = {
                loginViewModel.setPermissionAlert(false)
                val intent = Intent(Settings.ACTION_APP_NOTIFICATION_SETTINGS).apply {
                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    putExtra(Settings.EXTRA_APP_PACKAGE, context.packageName)
                }
                context.startActivity(intent)
            },
            onDismiss = {
                loginViewModel.setPermissionAlert(false)
            },
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
    ) {

        OutlinedTextField(
            value = username,
            onValueChange = {
                loginViewModel.setUserName(it)
            },
            label = {
                Text("Username")
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .height(40.dp),
        )

        OutlinedTextField(
            value = password,
            onValueChange = {
                loginViewModel.setPassword(it)
            },
            label = {
                Text("Password")
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .height(40.dp),
        )

        OutlinedButton(
            onClick = {
                loginViewModel.checkLogin(username, password)
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            shape = RoundedCornerShape(8.dp)
        ) {
            Text(
                text = "Login",
            )
        }
    }
}