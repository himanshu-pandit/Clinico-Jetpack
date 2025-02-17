package com.clinicodiagnostic.data.model

import com.google.gson.annotations.SerializedName

data class LoginOtpResponse(
    @SerializedName("d")
    val loginOtpResponseBody: LoginOtpResponseBody
)

data class LoginOtpResponseBody(
    @SerializedName("Age")
    val age: Int,
    @SerializedName("CollectionBoyID")
    val collectionBoyId: Int,
    @SerializedName("DOB")
    val birthDate: String,
    @SerializedName("EmailID")
    val emailId: String,
    @SerializedName("EntityID")
    val entityId: Int,
    @SerializedName("Gender")
    val gender: Int,
    @SerializedName("IsActive")
    val isActive: Boolean,
    @SerializedName("IsHomeCollectionAvailable")
    val isHomeCollectionAvailable: Boolean,
    @SerializedName("MobileNumber")
    val mobileNumber: String,
    @SerializedName("PreferredLabID")
    val preferredLabId: String,
    @SerializedName("PreferredLabName")
    val preferredLabName: String,
    @SerializedName("PrefferedLabsID")
    val prefferedLabsId: String,
    @SerializedName("Result")
    val result: String,
    @SerializedName("Role")
    val role: String,
    @SerializedName("ShortName")
    val shortName: String,
    @SerializedName("UserFID")
    val userFID: String,
    @SerializedName("UserName")
    val userName: String,
    @SerializedName("UserProfile")
    val userProfile: String
)