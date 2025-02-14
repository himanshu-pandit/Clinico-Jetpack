package com.clinicodiagnostic.utils

object ValidationUtils {

    fun validateMobileNumber(number: String): String?{
        if(number.isBlank()) return "Mobile number cannot be empty"
        if(!number.all { it.isDigit() }) return "Only number allowed"
        if (number.length != 10) return "Number must be 10 digits"
        return null
    }

}