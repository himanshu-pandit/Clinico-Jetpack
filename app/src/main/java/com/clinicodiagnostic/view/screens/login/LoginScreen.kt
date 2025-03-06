package com.clinicodiagnostic.view.screens.login

import android.app.Activity
import android.content.Intent
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.LinkAnnotation
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withLink
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.clinicodiagnostic.R
import com.clinicodiagnostic.navigation.Screen
import com.clinicodiagnostic.utils.AppVersion
import com.clinicodiagnostic.utils.Constant
import com.clinicodiagnostic.utils.NetworkResult
import com.clinicodiagnostic.view.component.AppLogo
import com.clinicodiagnostic.view.component.alert.Alert
import com.clinicodiagnostic.view.component.countrycode.CountryCodePicker
import com.google.android.gms.auth.api.identity.Identity

@Composable
fun LoginScreen(navHostController: NavHostController){

    val context = LocalContext.current
    val activity = context as? Activity
    val scrollState = rememberScrollState()
    val focusManager = LocalFocusManager.current
    val appVersion = remember { AppVersion.getAppVersion(context) }
    val loginViewModel: LoginViewModel = viewModel()
    val pager by loginViewModel.homePager.observeAsState(emptyList())
    val numberCode by loginViewModel.numberCode.observeAsState("")
    val permissionAlert by loginViewModel.permissionAlert.observeAsState(false)
    var isLoading by remember { mutableStateOf(false) }
    val pagerState = rememberPagerState(pageCount = { 4 })
    val focusRequester = remember { FocusRequester() }
    var validNumber by remember { mutableStateOf<String?>(null) }
    val loggingAgree = stringResource(R.string.logging_agree)
    val termsCondition = stringResource(R.string.terms_condition)
    val annotatedString: AnnotatedString = remember {
        buildAnnotatedString {

            val style = SpanStyle(
                fontSize = 14.sp
            )

            val styleCenter = SpanStyle(
                fontSize = 14.sp,
                color = Color.Red
            )

            withStyle(
                style = style
            ){
                append(loggingAgree)
            }

            withLink(LinkAnnotation.Url(url = Constant.TERMS_CONDITION)){
                withStyle(
                    style = styleCenter
                ){
                    append(termsCondition)
                }
            }
        }
    }

    val hintIntentResultLauncher = rememberLauncherForActivityResult(ActivityResultContracts.StartIntentSenderForResult()) { result ->
        try {
            val phoneNumber = Identity.getSignInClient(activity!!).getPhoneNumberFromIntent(result.data)
            Log.d("PhoneNumber Launcher", phoneNumber)
            loginViewModel.updateMobileNumber(phoneNumber.drop(3))
        } catch(e: Exception) {
            Log.e("PhoneNumber Launcher", "Phone Number Hint failed")
        }
    }

    if(permissionAlert){
        Alert(
            icon = ImageVector.vectorResource(R.drawable.notifications),
            title = stringResource(R.string.notification_permission_title),
            message = stringResource(R.string.notification_permission_message),
            confirmText = stringResource(R.string.notification_permission_allow),
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

    LaunchedEffect (Unit) {
        loginViewModel.requestPhoneHint(context, hintIntentResultLauncher)
    }

    RequestNotificationPermission(loginViewModel)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(scrollState),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        Column (
            modifier = Modifier
                .fillMaxSize()
                .weight(1f),
            verticalArrangement = Arrangement.Center
        ){
            Column (
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.widthIn(max = 600.dp)
            ){

                AppLogo()

                HorizontalPager(
                    state = pagerState,
                    modifier = Modifier.fillMaxWidth()
                ) { page ->
                    Image(
                        painter = painterResource(pager[page].image),
                        contentDescription = "clinico_login",
                        alignment = Alignment.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(4.dp)
                            .clip(RoundedCornerShape(4.dp))
                    )
                }

                Row(
                    Modifier
                        .wrapContentHeight()
                        .fillMaxWidth()
                        .padding(bottom = 36.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    repeat(pagerState.pageCount) { iteration ->
                        val color = if (pagerState.currentPage == iteration) Color.DarkGray else Color.LightGray
                        Box(
                            modifier = Modifier
                                .padding(2.dp)
                                .clip(CircleShape)
                                .background(color)
                                .size(16.dp)
                        )
                    }
                }
            }

            Column (
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.widthIn(max = 600.dp)
            ){
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp, 4.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {

                    Box(
                        modifier = Modifier
                            .height(54.dp)
                            .border(
                                BorderStroke(1.dp, Color.DarkGray),
                                shape = RoundedCornerShape(4.dp)
                            ),
                        contentAlignment = Alignment.Center
                    ){
                        CountryCodePicker("91") { selectedCode ->
                            loginViewModel.setNumberCode(selectedCode)
                        }
                    }

                    OutlinedTextField(
                        value = loginViewModel.mobileNumber,
                        onValueChange = {
                            loginViewModel.updateMobileNumber(it)
                        },
                        leadingIcon = {
                            Icon(Icons.Default.Call, contentDescription = "call")
                        },
                        placeholder = {
                            Text(stringResource(R.string.plc_mobile_number), maxLines = 1, overflow = TextOverflow.Ellipsis)
                        },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Phone,
                            imeAction = ImeAction.Done
                        ),
                        keyboardActions = KeyboardActions(
                            onDone = {
                                focusManager.clearFocus()
                                val mobile = numberCode+loginViewModel.mobileNumber.replace("+", " ")
                                loginViewModel.generateOTP(mobile){ response ->
                                    when(response){
                                        is NetworkResult.Loading ->{
                                            isLoading = true
                                        }

                                        is NetworkResult.Failure -> {
                                            isLoading = false
                                            Toast.makeText(context, response.message.toString(), Toast.LENGTH_SHORT).show()
                                        }

                                        is NetworkResult.Success -> {
                                            isLoading = false
                                            response.data.let {
                                                if (it?.loginOtpResponseBody?.result == "Success") {
                                                    val otpNumber = it.loginOtpResponseBody.userName
                                                    Log.d("AppNavigation2", otpNumber)
                                                    navHostController.navigate("${Screen.OTP.route}/$otpNumber")
                                                }else{
                                                    Toast.makeText(context,
                                                        context.getString(R.string.something_went_wrong_please_try_again_later), Toast.LENGTH_SHORT).show()
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        ),
                        singleLine = true,
                        isError = loginViewModel.mobileNumberHasErrors,        //validNumber != null,
                        supportingText = {
                            if (loginViewModel.mobileNumberHasErrors){
                                Text("Incorrect mobile number format.")
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 12.dp)
                            .focusRequester(focusRequester)
                            /*.onFocusChanged {
                                if (it.isFocused) {
                                    getPhoneHintIntent()
                                }
                            },*/
                    )
                }
                OutlinedButton(
                    onClick = {
                        val mobile = numberCode+loginViewModel.mobileNumber
                        loginViewModel.generateOTP(mobile){ response ->
                            when(response){
                                is NetworkResult.Loading ->{
                                    isLoading = true
                                }

                                is NetworkResult.Failure -> {
                                    isLoading = false
                                    Toast.makeText(context, response.message.toString(), Toast.LENGTH_SHORT).show()
                                }

                                is NetworkResult.Success -> {
                                    isLoading = false
                                    response.data.let {
                                        if (it?.loginOtpResponseBody?.result == "Success") {
                                            if (it.loginOtpResponseBody.userName != ""){
                                                val otpNumber = it.loginOtpResponseBody.userName
                                                Log.d("AppNavigation2", otpNumber)
                                                navHostController.navigate("otp/$otpNumber")
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp, 4.dp),
                    shape = RoundedCornerShape(4.dp)
                ) {
                    Row {
                        if(isLoading){
                            CircularProgressIndicator(
                                modifier = Modifier.size(48.dp),
                            )
                        }
                        Text(
                            text = stringResource(R.string.login),
                        )
                    }

                }

                Text(text = annotatedString, textAlign = TextAlign.Center, modifier = Modifier.padding(8.dp))
            }
        }

        Text(text = "Version$appVersion", textAlign = TextAlign.Center, modifier = Modifier.padding(8.dp), fontSize = 14.sp)
    }
}