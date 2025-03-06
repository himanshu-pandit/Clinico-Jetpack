package com.clinicodiagnostic.service

import android.content.Context
import com.clinicodiagnostic.utils.Constant
import com.clinicodiagnostic.utils.SecureSharedPrefs
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class FCMService : FirebaseMessagingService() {

    override fun onNewToken(token: String) {
        super.onNewToken(token)

        saveFcmToken(this, token)

    }

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)

    }

    private fun saveFcmToken(context: Context, token: String){
        val securePreference = SecureSharedPrefs.getSecurePreference(context)
        securePreference.edit().putString(Constant.FCM_TOKEN, token).apply()
    }
}