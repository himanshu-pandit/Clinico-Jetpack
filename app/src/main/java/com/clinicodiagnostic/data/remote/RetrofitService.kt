package com.clinicodiagnostic.data.remote

import com.clinicodiagnostic.data.model.LoginOtpRequest
import com.clinicodiagnostic.data.model.LoginOtpResponse
import com.clinicodiagnostic.utils.NetworkResult
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface RetrofitService {

    @POST("GlobalUserService.svc/UserRegistration")
    fun generateOtp(@Body loginOtpRequest: LoginOtpRequest): Call<LoginOtpResponse>

}