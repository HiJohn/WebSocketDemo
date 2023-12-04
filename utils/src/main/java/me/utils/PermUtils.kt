package me.utils

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.UriPermission
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.text.TextUtils
import androidx.activity.result.ActivityResultLauncher
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat


fun ActivityResultLauncher<String>.launchSd() {
    this.launch(PermUtils.SD_PERM)
}

fun ActivityResultLauncher<String>.launchLoc() {
    this.launch(PermUtils.LOC_PERM)
}




object PermUtils {

    /**
     * 当权限未申请时
     * shouldShowRequestPermissionRationale 判断返回 false
     * 当权限申请第一次被拒时
     * shouldShowRequestPermissionRationale 判断返回 true
     * 当权限申请第二次被拒时
     * shouldShowRequestPermissionRationale 判断返回 false
     * 当申请第三次时，系统不弹提示框，直接回调结果 false
     *
     *
     */

    const val REQ_LOC_CODE = 0x11




    /**
     * 如果为true，表示已经申请并且被拒一次，需向用户解释为何使用此权限
     * 如果为false，要么是还未申请过此权限，要么是已经被拒两次以及以上
     */
    fun checkPermissionRationale(activity: Activity, permission: String): Boolean {
        return ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)
    }

    fun checkSdRationale(activity: Activity): Boolean {
        return checkPermissionRationale(activity, SD_PERM)
    }

    fun checkLocRationale(activity: Activity): Boolean {
        return checkPermissionRationale(activity, LOC_PERM)
    }

    const val LOC_PERM = Manifest.permission.ACCESS_FINE_LOCATION
    const val SD_PERM = Manifest.permission.WRITE_EXTERNAL_STORAGE

    fun hasLocation(context: Context): Boolean {
        return ContextCompat.checkSelfPermission(
            context,
            LOC_PERM
        ) == PackageManager.PERMISSION_GRANTED

    }

    fun hasSd(context: Context): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R)
            Environment.isExternalStorageManager()
        else ContextCompat.checkSelfPermission(
            context,
            SD_PERM
        ) == PackageManager.PERMISSION_GRANTED
    }

    fun requestLoc(activity: Activity) {
        ActivityCompat.requestPermissions(
            activity, arrayOf(LOC_PERM),
            REQ_LOC_CODE
        )
    }

}