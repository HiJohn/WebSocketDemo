package me.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.UriPermission
import android.net.Uri
import android.provider.DocumentsContract
import android.text.TextUtils

object DataDirUtil {


    /**
     * 检测是否有特定包名 app 的/android/data 路径读写权限
     */
    fun checkAndroidDataPermission(context: Context, pkgName: String): Boolean {
        val path = Uri.encode(pkgName)
        try {
            val iterator: Iterator<*> = context.contentResolver.persistedUriPermissions.iterator()
            while (iterator.hasNext()) {
                val uriPermission = iterator.next() as UriPermission
                val persistedPath = uriPermission.uri.toString()
                //pkg uri = content://com.android.externalstorage.documents/document/primary%3AAndroid%2Fdata%2Fcom.ylb.myzl
                //Persisted uri = content://com.android.externalstorage.documents/tree/primary%3AAndroid%2Fdata%2Fcom.ylb.myzl
                if (persistedPath.contains("%3A")) {
                    val persistedFolder = persistedPath.substring(persistedPath.indexOf("%3A") + 3)
                    if (TextUtils.equals(persistedFolder, path) &&
                        uriPermission.isReadPermission &&
                        uriPermission.isWritePermission
                    ) {
                        return true
                    }
                }
            }
        } catch (e: Exception) {
        }
        return false
    }

    const val req_code = 1
    /**
     * 打开 documentsUI 跳转到 app 指定的Android/data目录
     */
    fun goAndroidDataPermissionPage(context: Context?, pkgName: String) {
        val sb1 = StringBuilder()
        sb1.append("content://com.android.externalstorage.documents/document/")
        val sb = StringBuilder()
        sb.append("primary")
        sb.append(":")
        sb.append(pkgName)
        sb1.append(Uri.encode(sb.toString()))
        val uri = Uri.parse(sb1.toString())
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT_TREE)
        intent.flags = Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION or
                Intent.FLAG_GRANT_WRITE_URI_PERMISSION or Intent.FLAG_GRANT_READ_URI_PERMISSION
        intent.putExtra("android.intent.extra.LOCAL_ONLY", true)
        intent.putExtra("android.content.extra.SHOW_ADVANCED", true)
        intent.putExtra("android.provider.extra.SHOW_ADVANCED", true)
        intent.putExtra(DocumentsContract.EXTRA_INITIAL_URI, uri)
        if (context is Activity) {
            context.startActivityForResult(
                intent,
                req_code
            )
        }
    }

    fun onRequestAndroidDataResult(
        context: Context,
        resultCode: Int,
        data: Intent?
    ) {
        if (resultCode == Activity.RESULT_OK) {
            if (data != null && data.data != null) context.applicationContext.contentResolver.takePersistableUriPermission(
                data.data!!,
                Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_GRANT_WRITE_URI_PERMISSION
            )
        }
    }
}