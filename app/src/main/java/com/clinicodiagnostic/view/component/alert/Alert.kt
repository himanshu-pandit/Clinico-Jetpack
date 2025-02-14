package com.clinicodiagnostic.view.component.alert

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import com.clinicodiagnostic.R

@Composable
fun Alert(
    title: String = "",
    message: String = "",
    icon: ImageVector = ImageVector.vectorResource(R.drawable.warning),
    iconDescription: String = "icon",
    iconColor: Color = if(isSystemInDarkTheme()){ Color.White } else { Color.Black },
    titleColor: Color = if(isSystemInDarkTheme()){ Color.White } else { Color.Black },
    textColor: Color = if(isSystemInDarkTheme()){ Color.White } else { Color.Black },
    containerColor: Color = if(isSystemInDarkTheme()){ Color.Black } else { Color.White },
    dismissText: String = "Cancel",
    confirmText: String = "Okay",
    modifier: Modifier = Modifier,
    onDismiss:() -> Unit,
    onConfirm: () -> Unit,
){
    AlertDialog(
        icon = {
            Icon(imageVector = icon, contentDescription = iconDescription)
        },
        title = {
            Text(title)
        },
        text = {
            Text(message)
        },
        containerColor = containerColor,
        iconContentColor = iconColor,
        titleContentColor = titleColor,
        textContentColor = textColor,
        modifier = modifier,
        onDismissRequest = {
            onDismiss()
        },
        confirmButton = {
            OutlinedButton(onClick = {
                onConfirm()
            }) {
                Text(confirmText)
            }
        },
        dismissButton = {
            OutlinedButton(onClick = {
                onDismiss()
            }) {
                Text(dismissText)
            }
        }
    )
}