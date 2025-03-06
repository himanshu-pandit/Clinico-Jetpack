package com.clinicodiagnostic.utils


import kotlinx.coroutines.flow.Flow

interface ConnectionObserver {

    fun isNetworkAvailable(): Flow<Status>

    enum class Status {
        Available, Losing, Lost, Unavailable
    }

}