package com.clinicodiagnostic.data.repository.login

import com.clinicodiagnostic.data.model.LoginOtpRequest
import com.clinicodiagnostic.data.model.LoginOtpResponse
import com.clinicodiagnostic.utils.NetworkResult

interface LoginRepository {

    fun getLoginOtp(loginOtpRequest: LoginOtpRequest, callback: (NetworkResult<LoginOtpResponse>) -> Unit)

}