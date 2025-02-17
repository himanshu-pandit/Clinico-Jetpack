package com.clinicodiagnostic.data.model

import com.google.gson.annotations.SerializedName

data class LoginOtpRequest(
    @SerializedName("objSp")
    val loginOtpRequestBody: LoginOtpRequestBody
)
data class LoginOtpRequestBody(
    @SerializedName("Task")
    val task: Int,
    @SerializedName("UserName")
    val userName: String,
    @SerializedName("EmailID")
    val emailId: String,
    @SerializedName("Password")
    val password: String,
    @SerializedName("MobileDeviceID")
    val mobileDeviceId: String,
    @SerializedName("Name")
    val name: String,
    @SerializedName("Gender")
    val gender: Int,
    @SerializedName("AppID")
    val appId: String,
    @SerializedName("DOB")
    val birthDate: String,
    @SerializedName("MobileNo")
    val mobileNo: String,
    @SerializedName("OTP")
    val otp: String,
)
