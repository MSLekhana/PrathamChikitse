package com.example.prathamchikitse.data

import android.content.Context

object JsonUtils {

    fun loadJSONFromAssets(context: Context): String {
        val inputStream = context.assets.open("emergency_data.json")
        val size = inputStream.available()
        val buffer = ByteArray(size)
        inputStream.read(buffer)
        inputStream.close()
        return String(buffer, Charsets.UTF_8)
    }
}