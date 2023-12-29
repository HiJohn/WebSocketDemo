package me.utils

import android.app.Activity
import android.app.AppOpsManager
import android.app.NotificationManager
import android.app.usage.StorageStatsManager
import android.app.usage.UsageStatsManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ComponentActivity
import androidx.fragment.app.Fragment


fun Context.getUsageService() :UsageStatsManager{
    return getSystemService(Context.USAGE_STATS_SERVICE) as UsageStatsManager
}


fun Context.getAppOpsService() :AppOpsManager{
    return getSystemService(Context.APP_OPS_SERVICE) as AppOpsManager
}

fun Context.getNotificationManager():NotificationManager{
    return getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
}

@RequiresApi(Build.VERSION_CODES.O)
fun Context.getStorageStats():StorageStatsManager{
    return getSystemService(Context.STORAGE_STATS_SERVICE) as StorageStatsManager
}


fun ActivityResultLauncher<String>.launchPermission(permission: String) {
    this.launch(permission)
}


fun androidx.activity.ComponentActivity.registerPermLauncher(callback: (result: Boolean) -> Unit): ActivityResultLauncher<String> {
    return this.registerForActivityResult(ActivityResultContracts.RequestPermission()) {
        callback(it)
    }
}
fun AppCompatActivity.registerPermLauncher(callback: (result: Boolean) -> Unit): ActivityResultLauncher<String> {
    return this.registerForActivityResult(ActivityResultContracts.RequestPermission()) {
        callback(it)
    }
}

fun Fragment.registerPermLauncher(callback: (result: Boolean) -> Unit): ActivityResultLauncher<String> {
    return this.registerForActivityResult(ActivityResultContracts.RequestPermission()) {
        callback(it)
    }
}

fun AppCompatActivity.registerPickVisualMedia(callback: (result: Uri?) -> Unit):ActivityResultLauncher<PickVisualMediaRequest>{
    return this.registerForActivityResult(ActivityResultContracts.PickVisualMedia()){
            callback(it)
    }
}

inline fun <reified T : Activity> Context.launchActivity() {
    val intent = Intent(this, T::class.java)
    startActivity(intent)
}


inline fun <reified T : Activity> Fragment.launchActivity() {
    val intent = Intent(this.requireContext(), T::class.java)
    startActivity(intent)
}