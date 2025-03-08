package com.clinicodiagnostic.view.screens


import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.graphics.Bitmap.Config
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.clinicodiagnostic.R
import com.google.firebase.encoders.config.Configurator
import kotlinx.coroutines.launch
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeSceeen(navHostController: NavHostController){

    val context = LocalContext.current
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val isPhlebotomist = remember { mutableStateOf(true) }
    val selectedMenuItem = remember { mutableStateOf("Lab Selection") }

    val patientMenu = listOf(
        NavigationItem("Lab Selection", false, Icons.Default.Done, "20"),
        NavigationItem("Book Test", false, Icons.Default.ShoppingCart, "21"),
        NavigationItem("View Report", false, Icons.Default.DateRange, "22"),
        NavigationItem("Update Profile", false, Icons.Default.Person, "23"),
        NavigationItem("Settings", false, Icons.Default.Settings, "24")
    )

    ModalNavigationDrawer(
        drawerContent = {
            ModalDrawerSheet {
                Column(
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .verticalScroll(rememberScrollState())
                ) {
                    Spacer(Modifier.height(12.dp))
                    Text("Drawer Title", modifier = Modifier.padding(16.dp), style = MaterialTheme.typography.titleLarge)
                    HorizontalDivider()

                    Text("Section 1", modifier = Modifier.padding(16.dp), style = MaterialTheme.typography.titleMedium)


                    patientMenu.map {
                        NavigationDrawerItem(
                            modifier = Modifier.padding(bottom = 8.dp),
                            label = {
                                Text(it.label)
                            },
                            selected = selectedMenuItem.value == it.label,
                            icon = {
                                Icon(imageVector = it.icon, contentDescription = null)
                            },
                            badge = {
                                Text(it.badge)
                            },
                            onClick = {
                                selectedMenuItem.value = it.label
                                scope.launch { drawerState.close() }
                            }
                        )
                    }

                    HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))

                    Text("Section 2", modifier = Modifier.padding(16.dp), style = MaterialTheme.typography.titleMedium)
                    NavigationDrawerItem(
                        label = { Text("Settings") },
                        selected = false,
                        icon = { Icon(Icons.Outlined.Settings, contentDescription = null) },
                        badge = { Text("20") }, // Placeholder
                        onClick = {
                            scope.launch {
                                if (drawerState.isClosed) {
                                    drawerState.open()
                                } else {
                                    drawerState.close()
                                }
                            }
                        }
                    )
                    NavigationDrawerItem(
                        label = { Text("Help and feedback") },
                        selected = false,
                        icon = { Icon(Icons.Default.Warning, contentDescription = null) },
                        onClick = {
                            scope.launch {
                                if (drawerState.isClosed) {
                                    drawerState.open()
                                } else {
                                    drawerState.close()
                                }
                            }
                        },
                    )
                    Spacer(Modifier.height(12.dp))
                }
            }
        },
        drawerState = drawerState
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Navigation Drawer Example") },
                    navigationIcon = {
                        IconButton(onClick = {
                            scope.launch {
                                if (drawerState.isClosed) {
                                    drawerState.open()
                                } else {
                                    drawerState.close()
                                }
                            }
                        }) {
                            Icon(Icons.Default.Menu, contentDescription = "Menu")
                        }
                    }
                )
            }
        ) { innerPadding ->
            Column(modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp)) {
                when (selectedMenuItem.value) {
                    "Lab Selection" -> Text("Lab Selection Content")
                    "Book Test" -> Text("Book Test Content")
                    "View Report" -> Text("View Report Content")
                    "Update Profile" -> Text("Update Profile Content")
                    "Settings" ->{
                        Text(stringResource(R.string.settings_content))
                        Button(
                            onClick = {
                                // Apply saved language
                                setLanguage(context, "hi")
                            }
                        ) {
                            Text("Change to Hindi")
                        }
                    }
                    else -> Text("Select an option from the drawer")
                }
            }
        }
    }
}

data class NavigationItem(
    val label : String,
    val selected : Boolean,
    val icon : ImageVector,
    val badge : String,
)

fun setLanguage(context: Context, languageCode: String){
    val local = Locale(languageCode)
    Locale.setDefault(local)

    val config = Configuration()
    config.setLocale(local)

    context.resources.updateConfiguration(config, context.resources.displayMetrics)

    // Save the language in SharedPreferences
    val sharedPref = context.getSharedPreferences("Settings", Context.MODE_PRIVATE)
    val editor = sharedPref.edit()
    editor.putString("App_Lang", languageCode)
    editor.apply()

    // Restart the activity to apply changes
    val intent = context.packageManager.getLaunchIntentForPackage(context.packageName)
    intent?.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
    context.startActivity(intent)
}