package com.clinicodiagnostic.view.screens.login

import android.app.Activity
import android.content.pm.PackageManager
import android.os.Build
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import com.clinicodiagnostic.R

@Composable
fun RequestNotificationPermission(loginViewModel: LoginViewModel){

    val context = LocalContext.current
    val activity = context as? Activity

    val requestPermissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission(),
    ) { isGranted: Boolean ->
        if (isGranted) {
            Toast.makeText(context, R.string.notification_permission_granted, Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context, R.string.notification_permission_denied, Toast.LENGTH_SHORT).show()
        }
    }

    LaunchedEffect (Unit){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(context, android.Manifest.permission.POST_NOTIFICATIONS) ==
                PackageManager.PERMISSION_GRANTED
            ) {
                // FCM SDK can post notifications.
                Toast.makeText(context, R.string.notification_permission_granted, Toast.LENGTH_SHORT).show()
            } else if (activity?.shouldShowRequestPermissionRationale(android.Manifest.permission.POST_NOTIFICATIONS) == true) {
                loginViewModel.setPermissionAlert(true)
            } else {
                // Directly ask for the permission
                requestPermissionLauncher.launch(android.Manifest.permission.POST_NOTIFICATIONS)
            }
        }
    }
}