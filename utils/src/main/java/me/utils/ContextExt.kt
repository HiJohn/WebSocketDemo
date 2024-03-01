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
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import java.io.File
import java.io.FileOutputStream


fun Context.getUsageService(): UsageStatsManager {
    return getSystemService(Context.USAGE_STATS_SERVICE) as UsageStatsManager
}


fun Context.getAppOpsService(): AppOpsManager {
    return getSystemService(Context.APP_OPS_SERVICE) as AppOpsManager
}

fun Context.getNotificationManager(): NotificationManager {
    return getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
}

@RequiresApi(Build.VERSION_CODES.O)
fun Context.getStorageStats(): StorageStatsManager {
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

fun AppCompatActivity.registerPickVisualMedia(callback: (result: Uri?) -> Unit): ActivityResultLauncher<PickVisualMediaRequest> {
    return this.registerForActivityResult(ActivityResultContracts.PickVisualMedia()) {
        callback(it)
    }
}

fun Fragment.toast(text: String) {
    Toast.makeText(this.context, text, Toast.LENGTH_SHORT).show()
}

fun Activity.toast(text: String) {
    Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
}

/**
 * use on android 13
 */
fun Fragment.registerPickVisualMedia(callback: (result: Uri?) -> Unit): ActivityResultLauncher<PickVisualMediaRequest> {
    return this.registerForActivityResult(ActivityResultContracts.PickVisualMedia()) {
        callback(it)
    }
}


fun Context.saveBase64Txt(content: String, fileName: String) {
    val file = File(this.filesDir, fileName)
    try {
        FileOutputStream(file).use { stream ->
            stream.write(content.toByteArray())
        }
    } catch (e: Exception) {
        println(e.message)
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