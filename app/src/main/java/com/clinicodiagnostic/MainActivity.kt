package com.clinicodiagnostic

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.rememberNavController
import com.clinicodiagnostic.navigation.AppNavigation
import com.clinicodiagnostic.ui.theme.FCMNotificationTheme
import com.clinicodiagnostic.utils.ConnectionObserver
import com.clinicodiagnostic.utils.ConnectionObserverImpl
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach


class MainActivity : ComponentActivity() {

    private lateinit var connectionObserver: ConnectionObserver

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        connectionObserver = ConnectionObserverImpl(applicationContext)

        connectionObserver.isNetworkAvailable().onEach {
            println("Status : $it")
        }.launchIn(lifecycleScope)

        setContent {
            FCMNotificationTheme {

                val navController = rememberNavController()

                val status by connectionObserver.isNetworkAvailable().collectAsState(
                    initial = ConnectionObserver.Status.Unavailable
                )


                Scaffold(
                    topBar = {
                        TopAppBar(
                            title = {
                                Text("Connection status : $status")
                            },
                            colors = TopAppBarDefaults.topAppBarColors(MaterialTheme.colorScheme.primary)
                        )
                    },
                    modifier = Modifier.fillMaxSize()
                ) { innerPadding ->
                    AppNavigation(navController)
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    FCMNotificationTheme {
        val navController = rememberNavController()
        AppNavigation(navController)
    }
}