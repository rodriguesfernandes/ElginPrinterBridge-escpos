package com.elgin.bridge

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothSocket
import android.content.Context
import android.util.Log
import java.io.IOException
import java.io.OutputStream
import java.util.*

object PrinterManager {
    private val SPP_UUID: UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB")

    fun findDevice(mac: String?): BluetoothDevice? {
        val adapter = BluetoothAdapter.getDefaultAdapter() ?: return null
        if (!adapter.isEnabled) return null

        mac?.let {
            try {
                return adapter.getRemoteDevice(it)
            } catch (_: IllegalArgumentException) { }
        }

        val paired = adapter.bondedDevices
        for (device in paired) {
            if (device.name.contains("I9", ignoreCase = true) || device.name.contains("elgin", ignoreCase = true)) {
                return device
            }
        }
        if (paired.size == 1) return paired.first()
        return null
    }

    @Throws(IOException::class)
    fun printText(context: Context, text: String, mac: String? = null) {
        val device = findDevice(mac) ?: throw IOException("Impressora Bluetooth não encontrada. Pareie antes.")
        var socket: BluetoothSocket? = null
        try {
            socket = device.createRfcommSocketToServiceRecord(SPP_UUID)
            socket.connect()
            val out: OutputStream = socket.outputStream

            val escpos = buildEscPos(text)
            out.write(escpos)
            out.flush()

            try {
                out.write(byteArrayOf(0x1D, 0x56, 0x00))
                out.flush()
            } catch (_: Exception) { }

        } finally {
            try { socket?.close() } catch (_: Exception) {}
        }
    }

    private fun buildEscPos(text: String): ByteArray {
        val bos = mutableListOf<Byte>()
        bos.addAll(byteArrayOf(0x1B, 0x40).toList())
        bos.addAll(byteArrayOf(0x1B, 0x61, 0x01).toList())
        bos.addAll(byteArrayOf(0x1D, 0x21, 0x11).toList())
        bos.addAll(text.toByteArray(Charsets.UTF_8).toList())
        bos.addAll(byteArrayOf(0x0A).toList())
        bos.addAll(byteArrayOf(0x1D, 0x21, 0x00).toList())
        bos.addAll(byteArrayOf(0x0A, 0x0A, 0x0A).toList())
        return bos.toByteArray()
    }
}
