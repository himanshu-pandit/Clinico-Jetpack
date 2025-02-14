package com.clinicodiagnostic.view.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.clinicodiagnostic.R

@Composable
fun AppLogo(){
    Image(
        painter = painterResource(R.drawable.clinico_logo),
        contentDescription = "clinico_logo",
        alignment = Alignment.Center,
        modifier = Modifier
            .size(180.dp)
            .fillMaxWidth()
            .wrapContentHeight()
    )
}