package com.clinicodiagnostic.view.screens.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.clinicodiagnostic.R
import com.clinicodiagnostic.utils.ClinicoPager

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

}