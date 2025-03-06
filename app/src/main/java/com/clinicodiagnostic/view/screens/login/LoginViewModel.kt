package com.clinicodiagnostic.view.screens.login

import android.app.PendingIntent
import android.content.Context
import android.util.Log
import android.util.Patterns
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.IntentSenderRequest
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.clinicodiagnostic.R
import com.clinicodiagnostic.data.model.LoginOtpRequest
import com.clinicodiagnostic.data.model.LoginOtpRequestBody
import com.clinicodiagnostic.data.model.LoginOtpResponse
import com.clinicodiagnostic.data.repository.login.LoginRepositoryImpl
import com.clinicodiagnostic.utils.ClinicoPager
import com.clinicodiagnostic.utils.Constant
import com.clinicodiagnostic.utils.NetworkResult
import com.google.android.gms.auth.api.identity.GetPhoneNumberHintIntentRequest
import com.google.android.gms.auth.api.identity.Identity
import kotlinx.coroutines.launch

class LoginViewModel(): ViewModel() {

    private val loginRepository = LoginRepositoryImpl()

//    private val _loginOtpDataState = MutableStateFlow<NetworkResult<LoginOtpResponse>>()
//    val loginOtpDataState : StateFlow<NetworkResult<LoginOtpResponse>> get() = _loginOtpDataState

    private val _homePager = MutableLiveData(listOf(
        ClinicoPager(R.drawable.clinico_login),
        ClinicoPager(R.drawable.clinico_login),
        ClinicoPager(R.drawable.clinico_login),
        ClinicoPager(R.drawable.clinico_login),
    ))
    val homePager: LiveData<List<ClinicoPager>> get() = _homePager

    private val _numberCode = MutableLiveData<String>("")
    val numberCode: LiveData<String> get() = _numberCode

    var mobileNumber by mutableStateOf("")
        private set

    val mobileNumberHasErrors by derivedStateOf {
        if (mobileNumber.isNotEmpty()){
            !Patterns.PHONE.matcher(mobileNumber).matches() || mobileNumber.length != 10
        }else{
            false
        }
    }

    private val _permissionAlert = MutableLiveData<Boolean>(false)
    val permissionAlert: LiveData<Boolean> get() = _permissionAlert

    fun setNumberCode(code: String) {
        _numberCode.postValue(code)
    }

    fun updateMobileNumber(input: String){
        println("updateMobileNumber $input")
        mobileNumber = input
    }

    fun setPermissionAlert(alert: Boolean){
        _permissionAlert.postValue(alert)
    }

    fun generateOTP(
        number: String,
        callback: (NetworkResult<LoginOtpResponse>) -> Unit
    ) {
        Log.d("generateOTP", number)
        val body = LoginOtpRequestBody(
            7,
            number,
            "",
            number,
            "",
            Constant.LAB_NAME,
            0,
            Constant.LAB_ID,
            "",
            number,
            ""
        )

        val loginBody = LoginOtpRequest(body)

        viewModelScope.launch {
            loginRepository.getLoginOtp(loginBody){ result ->
                callback(result)
            }
        }
    }

    fun requestPhoneHint(context: Context, hintIntentResultLauncher: ActivityResultLauncher<IntentSenderRequest>){
        val request: GetPhoneNumberHintIntentRequest = GetPhoneNumberHintIntentRequest.builder().build()

        Identity.getSignInClient(context)
            .getPhoneNumberHintIntent(request)
            .addOnSuccessListener { result: PendingIntent ->
                try {
                    hintIntentResultLauncher.launch(
                        IntentSenderRequest.Builder(result).build()
                    )
                } catch (e: Exception) {
                    Log.e("PhoneHintIntent", "Launching the PendingIntent failed $e")
                }
            }
            .addOnFailureListener {
                Log.e("PhoneHintIntent", "${it.message}")
            }
    }

    fun validateNumber(number: String): String? {
        println("validateMobileNumber $number")
        if(number.isBlank()) return "Mobile number cannot be empty"
        if(!number.all { it.isDigit() }) return "Only number allowed"
        if (number.length != 10) return "Number must be 10 digits"
        return null
    }

}