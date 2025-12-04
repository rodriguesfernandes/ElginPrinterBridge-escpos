package com.elgin.bridge

import android.content.Context
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat

object BluetoothPermissionHelper {
    val REQUIRED_PERMISSIONS = arrayOf(
        "android.permission.BLUETOOTH",
        "android.permission.BLUETOOTH_ADMIN",
        "android.permission.BLUETOOTH_CONNECT",
        "android.permission.BLUETOOTH_SCAN",
        "android.permission.ACCESS_FINE_LOCATION"
    )

    fun hasPermissions(context: Context): Boolean {
        return REQUIRED_PERMISSIONS.all {
            ContextCompat.checkSelfPermission(context, it) == PackageManager.PERMISSION_GRANTED
        }
    }
}
