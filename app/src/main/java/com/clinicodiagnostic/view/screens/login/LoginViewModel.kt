package com.clinicodiagnostic.view.screens.login

import android.app.PendingIntent
import android.content.Context
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.IntentSenderRequest
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.clinicodiagnostic.R
import com.clinicodiagnostic.utils.ClinicoPager
import com.google.android.gms.auth.api.identity.GetPhoneNumberHintIntentRequest
import com.google.android.gms.auth.api.identity.Identity

class LoginViewModel: ViewModel() {

    private val _homePager = MutableLiveData(listOf(
        ClinicoPager(R.drawable.clinico_login),
        ClinicoPager(R.drawable.clinico_login),
        ClinicoPager(R.drawable.clinico_login),
        ClinicoPager(R.drawable.clinico_login),
    ))
    val homePager: LiveData<List<ClinicoPager>> get() = _homePager

    private val _numberCode = MutableLiveData<String>("")
    val numberCode: LiveData<String> get() = _numberCode

    private val _number = MutableLiveData<String>("")
    val number: LiveData<String> get() = _number

    private val _permissionAlert = MutableLiveData<Boolean>(false)
    val permissionAlert: LiveData<Boolean> get() = _permissionAlert

    fun setNumberCode(code: String){
        _numberCode.postValue(code)
    }

    fun setNumber(number: String) {
        _number.postValue(number)
    }

    fun setPermissionAlert(alert: Boolean){
        _permissionAlert.postValue(alert)
    }

    fun generateOTP(number: String) {

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

}