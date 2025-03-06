package com.clinicodiagnostic.view.component.countrycode

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.viewinterop.AndroidView
import com.clinicodiagnostic.R
import com.hbb20.CountryCodePicker

@Composable
fun CountryCodePicker(defaultCode: String, onCountryChange: (String) -> Unit) {
    Row (
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly
    ){
        AndroidView(
            factory = { context ->
                CountryCodePicker(context).apply {
                    setAutoDetectedCountry(true)
                    showFullName(false)
                    setShowPhoneCode(false)
                    showNameCode(false)
                    showFlag(true)
                    setDefaultCountryUsingNameCode(defaultCode)
                    setNumberAutoFormattingEnabled(true)
                    setArrowSize(16)
                    // Send the default country code when initialized
                    post{
                        onCountryChange(selectedCountryCode)
                    }
                    setOnCountryChangeListener {
                        onCountryChange(selectedCountryCode)
                    }
                }
            },
            modifier = Modifier.wrapContentSize()
        )
        Icon(painter = painterResource(R.drawable.arrow_down), contentDescription = "arrow_down")
    }
}

