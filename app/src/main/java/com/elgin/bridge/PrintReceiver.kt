package com.elgin.bridge

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PrintReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val raw = intent.getStringExtra("text") ?: return
        val params = raw.split("&").mapNotNull {
            val parts = it.split("=")
            if (parts.size >= 2) parts[0] to parts.subList(1, parts.size).joinToString("=") else null
        }.toMap()
        val text = params["text"] ?: raw
        val mac = params["mac"]

        CoroutineScope(Dispatchers.IO).launch {
            try {
                PrinterManager.printText(context, text, mac)
            } catch (e: Exception) {
                Log.e("PrintReceiver", "Erro em impressão", e)
            }
        }
    }
}
