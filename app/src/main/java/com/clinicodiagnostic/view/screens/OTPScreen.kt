package com.clinicodiagnostic.view.screens

import android.content.Context
import android.content.IntentFilter
import android.os.Build
import android.util.Log
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.clinicodiagnostic.R
import com.clinicodiagnostic.navigation.Screen
import com.clinicodiagnostic.view.component.AppLogo

@Composable
fun OTPScreen(navController: NavController, number: String?){

    val context  = LocalContext.current
    val scrollState = rememberScrollState()
    var otpValue by remember { mutableStateOf("") }

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
            "An 4 digit code has been sent to $number",
            modifier = Modifier.padding(16.dp),
            fontWeight = FontWeight.Bold,
        )

        OtpTextField(
            modifier = Modifier.widthIn(min = 176.dp),
            otpText = otpValue,
            onOtpTextChange = { value, otpInput ->
                otpValue = value
            }
        )

        OutlinedButton(
            modifier = Modifier.widthIn(min = 320.dp).padding(16.dp),
            shape = RoundedCornerShape(4.dp),
            colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.primary),
            onClick = {
                navController.navigate(Screen.Home.route)
            }
        ) {
            Text(
                text = "Verify"
            )
        }

        Row (
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ){

            Text("OTP valid for 120 seconds")

            TextButton(
                onClick = {

                }
            ) {
                Text("Resend OTP", color = MaterialTheme.colorScheme.primary)
            }
        }

    }

}

@Composable
fun OtpTextField(
    modifier: Modifier = Modifier,
    otpText: String,
    otpCount: Int = 4,
    onOtpTextChange: (String, Boolean) -> Unit
) {

    BasicTextField(
        modifier = modifier,
        value = TextFieldValue(otpText, selection = TextRange(otpText.length)),
        onValueChange = {
            if (it.text.length <= otpCount) {
                onOtpTextChange.invoke(it.text, it.text.length == otpCount)
            }
        },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
        decorationBox = {
            Row(horizontalArrangement = Arrangement.Center) {
                repeat(otpCount) { index ->
                    CharView(
                        index = index,
                        text = otpText
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                }
            }
        }
    )
}

@Composable
private fun CharView(
    index: Int,
    text: String
) {
    val isFocused = text.length == index
    val char = when {
        index == text.length -> ""
        index > text.length -> ""
        else -> text[index].toString()
    }

    Box(
        modifier = Modifier
            .width(44.dp)
            .height(44.dp)
            .border(
                1.dp, when {
                    isFocused -> Color.DarkGray
                    else -> Color.LightGray
                }, RoundedCornerShape(4.dp)
            )
            .padding(2.dp),
        contentAlignment = Alignment.Center
    ){
        Text(
            text = char,
            style = MaterialTheme.typography.titleLarge,
            color = if (isFocused) {
                Color.LightGray
            } else {
                Color.DarkGray
            },
            textAlign = TextAlign.Center
        )
    }
}