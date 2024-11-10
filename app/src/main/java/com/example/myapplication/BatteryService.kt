// BatteryService.kt
package com.example.myapplication

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import android.os.BatteryManager

class BatteryService : Service() {
    private val binder = BatteryBinder()

    override fun onBind(intent: Intent?): IBinder {
        return binder
    }

    fun getBatteryLevel(): Int {
        val batteryManager = getSystemService(BATTERY_SERVICE) as BatteryManager
        return batteryManager.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY)
    }

    inner class BatteryBinder : Binder() {
        fun getService(): BatteryService = this@BatteryService
    }
}
