package com.clinicodiagnostic.utils

import android.content.Context
import android.util.Log

object AppVersion {
    fun getAppVersion(context: Context): String {
        return try {
            context.packageManager.getPackageInfo(context.packageName, 0).versionName ?: ""
        }catch (e: Exception){
            Log.e("AppVersion", e.printStackTrace().toString())
            ""
        }
    }
}