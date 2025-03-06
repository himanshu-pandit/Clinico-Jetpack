package com.clinicodiagnostic.view.screens

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.clinicodiagnostic.view.component.AppLogo

@Composable
fun OTPScreen(navController: NavController, number: String?){
    val context = LocalContext.current
    val scrollState = rememberScrollState()

    LaunchedEffect(Unit){
        Log.d("OTPScreen", "OTPScreen called $number")
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(scrollState),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ){

        AppLogo()

        Text(
            "Enter OTP sent to $number",
            modifier = Modifier.padding(16.dp),
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
        )

    }

}