package com.clinicodiagnostic.view.screens.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class LoginViewModel: ViewModel() {

    private val _username = MutableLiveData<String>()
    val username: LiveData<String> get() = _username

    private val _password = MutableLiveData<String>()
    val password: LiveData<String> get() = _password

    private val _permissionAlert = MutableLiveData<Boolean>()
    val permissionAlert: LiveData<Boolean> get() = _permissionAlert

    fun setUserName(user: String){
        _username.postValue(user)
    }

    fun setPassword(pass: String){
        _password.postValue(pass)
    }

    fun setPermissionAlert(alert: Boolean){
        _permissionAlert.postValue(alert)
    }

    fun checkLogin(username: String, password: String) {

    }

}