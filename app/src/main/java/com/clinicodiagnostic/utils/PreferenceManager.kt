package com.clinicodiagnostic.utils

import android.content.Context

class PreferenceManager(private val context: Context) {

    private val sharedPreferences = SecureSharedPrefs.getSecurePreference(context)

    fun storeData(context: Context, key: String, value: String){
        sharedPreferences.edit().putString(key, value).apply()
    }

    fun getData(context: Context, key: String, default: String = ""): String {
        return sharedPreferences.getString(key, default) ?: default
    }

    fun removeData(key: String){
        sharedPreferences.edit().remove(key).apply()
    }

    fun clearAll(){
        sharedPreferences.edit().clear().apply()
    }

}