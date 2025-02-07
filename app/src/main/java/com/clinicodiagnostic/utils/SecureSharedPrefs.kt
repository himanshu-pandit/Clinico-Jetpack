package com.clinicodiagnostic.utils

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys


object SecureSharedPrefs {

    private var sharedPreferences: SharedPreferences? = null

    fun getSecurePreference(context: Context): SharedPreferences {

        return sharedPreferences ?: synchronized(this){

            val masterKey = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)

            val instance = EncryptedSharedPreferences.create(
                Constant.PREFERENCE_NAME,
                masterKey,
                context,
                EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
            )
            sharedPreferences = instance
            instance
        }

    }

}