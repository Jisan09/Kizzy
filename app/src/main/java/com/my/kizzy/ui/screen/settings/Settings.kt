package com.my.kizzy.ui.screen.settings

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.PowerManager
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.EnergySavingsLeaf
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Palette
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.my.kizzy.ui.common.BackButton
import com.my.kizzy.ui.common.PreferencesHint
import com.my.kizzy.ui.common.SettingItem

@SuppressLint("BatteryLife")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Settings(
    navigateToProfile: () -> Unit,
    navigateToStyleAndAppeareance: () -> Unit,
    navigateToAbout: () -> Unit,
    onBackPressed: () -> Unit
) {
    val context = LocalContext.current
    val pm = context.getSystemService(Context.POWER_SERVICE) as PowerManager
    var showBatteryHint by remember { mutableStateOf(!pm.isIgnoringBatteryOptimizations(context.packageName)) }
    Surface(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
        ) {
            SmallTopAppBar(
                title = {},
                navigationIcon = { BackButton { onBackPressed() } },
                modifier = Modifier.padding(start = 8.dp)
            )
            Text(
                modifier = Modifier.padding(start = 24.dp, top = 48.dp),
                text = "Settings",
                style = MaterialTheme.typography.headlineLarge
            )
            LazyColumn(modifier = Modifier.padding(top = 48.dp)) {
                item {
                    AnimatedVisibility(visible = showBatteryHint) {
                        PreferencesHint(
                            title = "Battery Optimisation",
                            icon = Icons.Default.EnergySavingsLeaf,
                            description = "Turn off battery optimisation for better stability of rpc"
                        ) {
                            context.startActivity(Intent(android.provider.Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS).apply {
                                data = Uri.parse("package:${context.packageName}")
                            })
                            showBatteryHint =
                                !pm.isIgnoringBatteryOptimizations(context.packageName)
                        }
                    }
                }
                item {
                    SettingItem(
                        title = "Account",
                        description = "Account info",
                        icon = Icons.Outlined.Person
                    ) {
                        navigateToProfile()
                    }
                }
                item {
                    SettingItem(
                        title = "Display",
                        description = "Theme,Dynamic Colors,Languages",
                        icon = Icons.Outlined.Palette
                    ) {
                       navigateToStyleAndAppeareance()
                    }
                }
                item {
                    SettingItem(
                        title = "About",
                        description = "Version,releases",
                        icon = Icons.Outlined.Info
                    ) {
                        navigateToAbout()
                    }
                }
            }
        }
    }
}

